package com.example.catastrophe;

import android.graphics.drawable.Icon;

/**
 * Created by gehad on 6/22/19.
 */

//A class the holds the properties of the plants

public class Plant {

    public Icon icon;
    public String id;
    public String name;
    public double moisture_threshold;
    public double recommended_ph;
    public double recommended_temperature;

    public Plant(Icon icon,String id,String name,double moisture_threshold,double recommended_ph,double recommended_temperature){
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.moisture_threshold = moisture_threshold;
        this.recommended_ph = recommended_ph;
        this.recommended_temperature = recommended_temperature;
    }

    public Plant(String name,double moisture_threshold,double recommended_ph,double recommended_temperature){
        this.icon = null;
        this.id = null;
        this.name = name;
        this.moisture_threshold = moisture_threshold;
        this.recommended_ph = recommended_ph;
        this.recommended_temperature = recommended_temperature;
    }
}
