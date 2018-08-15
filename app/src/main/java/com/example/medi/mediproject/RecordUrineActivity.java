package com.example.medi.mediproject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.felhr.usbserial.UsbSerialDevice;
import com.felhr.usbserial.UsbSerialInterface;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RecordUrineActivity extends BaseActivity {
    public final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";
    Button startButton,stopButton;
    TextView textView, tmp;
    UsbManager usbManager;
    UsbDevice device;
    UsbSerialDevice serialPort;
    UsbDeviceConnection connection;
    String res=null;
    String pid,name, time;
    boolean first=false;

    UsbSerialInterface.UsbReadCallback mCallback = new UsbSerialInterface.UsbReadCallback() { //Defining a Callback which triggers whenever data is read.
        @Override
        public void onReceivedData(byte[] arg0) {
            String data = null;
            try {
                data = new String(arg0, "UTF-8");

                if( (res==null&& !first) && (data!=null && data !="")){
                    res=data;
                    textView.setText(res+".0");
                    first=true;
                }

                data.concat("/n");
                tvAppend(tmp, data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }
    };
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { //Broadcast Receiver to automatically start and stop the Serial connection.
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_USB_PERMISSION)) {
                boolean granted = intent.getExtras().getBoolean(UsbManager.EXTRA_PERMISSION_GRANTED);
                if (granted) {
                    connection = usbManager.openDevice(device);
                    serialPort = UsbSerialDevice.createUsbSerialDevice(device, connection);
                    if (serialPort != null) {

                        if (serialPort.open() ) { //Set Serial Connection Parameters.
                            setUiEnabled(true);
                            serialPort.setBaudRate(9600);
                            serialPort.setDataBits(UsbSerialInterface.DATA_BITS_8);
                            serialPort.setStopBits(UsbSerialInterface.STOP_BITS_1);
                            serialPort.setParity(UsbSerialInterface.PARITY_NONE);
                            serialPort.setFlowControl(UsbSerialInterface.FLOW_CONTROL_OFF);
                            serialPort.read(mCallback);

                        } else {
                            Log.d("SERIAL", "PORT NOT OPEN");
                        }
                    } else {
                        Log.d("SERIAL", "PORT IS NULL");
                    }
                } else {
                    Log.d("SERIAL", "PERM NOT GRANTED");
                }
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_ATTACHED)) {
                onClickStart(startButton);
            } else if (intent.getAction().equals(UsbManager.ACTION_USB_DEVICE_DETACHED)) {
                onClickStop(stopButton);
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_urine);
        usbManager = (UsbManager) getSystemService(this.USB_SERVICE);

        stopButton = (Button)findViewById(R.id.bt_stop);
        startButton = (Button) findViewById(R.id.askWeight);
        textView = (TextView) findViewById(R.id.weightPrint);
        tmp = findViewById(R.id.urineUnit);

        Intent intent =getIntent();
        pid=intent.getStringExtra("pid");
        name= MediValues.patientData.get(pid).get("name");
        time ="";

        buttonPrev = findViewById(R.id.Bnt_prev);
        buttonNext =findViewById(R.id.Bnt_next);

        setUiEnabled(false);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_USB_PERMISSION);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(broadcastReceiver, filter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                res=null;
                first=false;

                Toast.makeText(getApplicationContext(),"무게를 측정하였습니다",Toast.LENGTH_SHORT).show();
                HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();

                if (!usbDevices.isEmpty()) {
                    boolean keep = true;
                    for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                        device = entry.getValue();
                        int deviceVID = device.getVendorId();
                        if (deviceVID == 0x2341)//Arduino Vendor ID
                        {
                            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(ACTION_USB_PERMISSION), 0);
                            usbManager.requestPermission(device, pi);
                            keep = false;
                        } else {
                            connection = null;
                            device = null;
                        }
                        if (!keep)
                            break;
                    }
                }

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serialPort.close();
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                setUiEnabled(false);
            }
        });
    }


    public void onPrevClick(View view) {
        Intent intent = new Intent(RecordUrineActivity.this, ContainerSelectActivity.class);
        intent.putExtra("pid", pid);
        startActivity(intent);
    }

    public void onNextClick(View view) {

        if(!textView.getText().toString().equals("")) {
            float amount = Float.parseFloat(res);
            MediPostRequest postRequest = new MediPostRequest(view.getContext(), pid, name, MediValues.OUTPUT, MediValues.URINE, amount, time);
            Intent intent = new Intent(RecordUrineActivity.this, ReportActivity.class);
            intent.putExtra("pid", pid);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "무게를 측정해 주세요", Toast.LENGTH_LONG).show();
        }
    }
    public void setUiEnabled(boolean bool) {
        startButton.setEnabled(!bool);
        stopButton.setEnabled(bool);
        textView.setEnabled(bool);
    }

    public void onClickStart(View view) {
        Toast.makeText(getApplicationContext(),"측정 준비가 되었습니다",Toast.LENGTH_LONG).show();
        HashMap<String, UsbDevice> usbDevices = usbManager.getDeviceList();

        if (!usbDevices.isEmpty()) {
            boolean keep = true;
            for (Map.Entry<String, UsbDevice> entry : usbDevices.entrySet()) {
                device = entry.getValue();
                int deviceVID = device.getVendorId();
                if (deviceVID == 0x2341)//Arduino Vendor ID
                {
                    PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
                    usbManager.requestPermission(device, pi);
                    keep = false;
                } else {
                    connection = null;
                    device = null;
                }
                if (!keep)
                    break;
            }
        }


    }


    public void onClickStop(View view) {
        serialPort.close();
        Toast.makeText(getApplicationContext(), "저울과 연결이 필요합니다", Toast.LENGTH_LONG).show();
        setUiEnabled(false);
    }

    private void tvAppend(TextView tv, final CharSequence text) {
        final TextView ftv = tv;
        final CharSequence ftext = text;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //ftv.append(ftext);
            }

        });
    }

}
