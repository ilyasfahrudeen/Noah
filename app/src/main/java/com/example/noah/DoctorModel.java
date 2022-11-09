package com.example.noah;

public class DoctorModel {
    private String name;
    private int avg_time;

    public DoctorModel(String name, int avg_time){
        this.name = name;
        this.avg_time = avg_time;
    }

    public String getName() {
        return name;
    }

    public int getAvg_time() {
        return avg_time;
    }
}
