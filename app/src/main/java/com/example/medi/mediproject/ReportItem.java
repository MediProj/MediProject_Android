package com.example.medi.mediproject;


import java.util.Date;

public class ReportItem {
    String date;
    String name;
    Integer stool_cnt;
    float urine_amt, liquid_amt, consume_amt;

    public ReportItem(String date, String name, Integer stool,float urine, float liquid, float consume){
        this.date=date;
        this.name= name;
        this.stool_cnt=stool;
        this.urine_amt=urine;
        this.liquid_amt=liquid;
        this.consume_amt=consume;
    }


}
