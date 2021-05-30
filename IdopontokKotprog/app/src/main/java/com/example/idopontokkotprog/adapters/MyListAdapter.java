package com.example.idopontokkotprog.adapters;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.idopontokkotprog.R;
import com.example.idopontokkotprog.models.Appointment;

import java.util.ArrayList;

public class MyListAdapter extends ArrayAdapter<Appointment> {

    public MyListAdapter(Activity context, ArrayList<Appointment> appointments) {
        super(context, R.layout.list_element, appointments);

        this.context = context;
        this.appointments = appointments;
    }

    private final Activity context;
    private ArrayList<Appointment> appointments;

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_element, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        switch (appointments.get(position).status){
            case "Függőben" : imageView.setImageResource(R.drawable.send);
                break;
            case "Felvéve" : imageView.setImageResource(R.drawable.flag);
                break;
            case "Megérkezett" : imageView.setImageResource(R.drawable.login);
                break;
            case "Teljesítve" : imageView.setImageResource(R.drawable.done);
                break;
            case "Visszavonva" : imageView.setImageResource(R.drawable.delete_forever);
                break;
            default: imageView.setImageResource(R.drawable.send);
                break;
        }
        titleText.setText(appointments.get(position).description != "" ? appointments.get(position).description : "Névtelen");
        subtitleText.setText(appointments.get(position).start + " - " + appointments.get(position).end);

        return rowView;

    };
}