package com.example.catastrophe;

import android.graphics.drawable.Icon;

/**
 * Created by gehad on 6/22/19.
 */

public class Plant {

    private Icon icon;
    private String id;
    private String name;
    private double moisture_threshold;
    private double recommended_ph;
    private double recommended_temperature;

    public Plant(Icon icon,String id,String name,double moisture_threshold,double recommended_ph,double recommended_temperature){
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.moisture_threshold = moisture_threshold;
        this.recommended_ph = recommended_ph;
        this.recommended_temperature = recommended_temperature;
    }
}
