package com.example.catastrophe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//public class CustomAdapter extends BaseAdapter {
//    public List<Plant> plant_list;
//    Context context;
//    LayoutInflater layoutInflater;
//
//    public CustomAdapter(List<Plant> plant_list, Context context) {
//        this.plant_list = plant_list;
//        this.context = context;
//        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return plant_list.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return plant_list.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
//        view = layoutInflater.inflate(R.layout.plant_view,null);
//        if(plant_list.get(position).getIcon() != null) {
//            ImageView image = (ImageView) view.findViewById(R.id.PlantImage);
//            image.setImageIcon(plant_list.get(position).getIcon());
//        }
//        else{
//            ImageView image = (ImageView) view.findViewById(R.id.PlantImage);
//            image.setImageResource(R.mipmap.plant2);
//        }
//
//        TextView PlantName = (TextView) view.findViewById(R.id.PlantName);
//        PlantName.setText(plant_list.get(position).getName());
//
//        TextView PlantSize = (TextView) view.findViewById(R.id.PlantSize);
//        PlantSize.setText(plant_list.get(position).getSize()+"");
//
//        TextView PlantMoisture = (TextView) view.findViewById(R.id.PlantMoisture);
//        PlantMoisture.setText(plant_list.get(position).getMoistureLevel()+"");
//
//        TextView PlantTimeToWater = (TextView) view.findViewById(R.id.PlantTimeToWater);
//        PlantTimeToWater.setText(plant_list.get(position).getTimeToWater()+"");
//
//        TextView PlantInfo = (TextView) view.findViewById(R.id.PlantInfo);
//        PlantInfo.setText(plant_list.get(position).getInfo()+"");
//
////        if(position==plant_list.size()-1){
////            view.setAlpha(0);
////            view.animate();
////        }
//
//        return view;
//    }
//
//}

