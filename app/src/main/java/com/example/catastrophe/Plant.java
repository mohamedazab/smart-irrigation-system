package com.example.catastrophe;

import android.graphics.drawable.Icon;

/**
 * Created by gehad on 6/22/19.
 */

public class Plant {

    private Icon icon;
    private String name;
    private int size;
    private int moistureLevel;
    private String timeToWater;
    private String info;

    public Plant(Icon icon, String name, int size, int moistureLevel, String timeToWater, String info){
        this.icon = icon;
        this.name = name;
        this.size = size;
        this.moistureLevel = moistureLevel;
        this.timeToWater = timeToWater;
        this.info = info;
    }

    public Plant(String name, int size, int moistureLevel, String timeToWater, String info){
        this.icon = null;
        this.name = name;
        this.size = size;
        this.moistureLevel = moistureLevel;
        this.timeToWater = timeToWater;
        this.info = info;
    }

    public Icon getIcon(){
        return icon;
    }

    public String getName(){
        return name;
    }

    public int getSize(){
        return size;
    }

    public int getMoistureLevel(){
        return moistureLevel;
    }

    public String getTimeToWater(){
        return timeToWater;
    }

    public String getInfo(){
        return info;
    }
}
