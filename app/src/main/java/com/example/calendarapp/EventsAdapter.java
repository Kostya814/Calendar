package com.example.calendarapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

public class EventsAdapter extends ArrayAdapter<Events> {
    private LayoutInflater inflater;
    private int layout;
    private List<Events> events;

    public EventsAdapter(Context context, int resource, List<Events> events) {
        super(context, resource, events);
        this.events = events;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);


        TextView nameView = view.findViewById(R.id.name);
        TextView capitalView = view.findViewById(R.id.date);

        Events state = events.get(position);

        nameView.setText(state.getName());
        String date = state.getBithday().get(Calendar.YEAR)+"/"+ state.getBithday().get(Calendar.MONTH)+"/" + state.getBithday().get(Calendar.DATE);
        capitalView.setText(date);

        return view;
    }
}
