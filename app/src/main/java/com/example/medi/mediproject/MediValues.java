package com.example.medi.mediproject;

import java.util.Map;

public class MediValues {
    public static final String USERNAME = "service";
    public static final String PASSWORD = "medi@service";
    public static String ACCESS_TOKEN = "";

    public static Map<String, Map<String, String>> patientData;
    public static String mediDate;
    public static String mediTime;
    public static Map<String, String> patientRecord[];

    static final int INPUT = 1;
    static final int OUTPUT = 2;

    static final int STOOL = 10;
    static final int URINE = 6;

    static final int CONSUME = 1;
}
