package com.example.medi.mediproject;


import java.util.Date;

public class ReportItem {
    Date date;
    String name;
    Integer stool_cnt;
    Double urine_amt, liquid_amt, consume_amt;

    public ReportItem(Date date, String name, Integer stool, Double urine, Double liquid, Double consume){
        this.date=date;
        this.name= name;
        this.stool_cnt=stool;
        this.urine_amt=urine;
        this.liquid_amt=liquid;
        this.consume_amt=consume;
    }


}
