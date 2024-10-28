package com.example.calendarapp;

import java.util.Calendar;
import java.util.Date;

public class Events {
    private int id;
    private String Name;
    private Calendar Bithday;

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Calendar getBithday() {
        return Bithday;
    }

    public void setBithday(Calendar bithday) {
        Bithday = bithday;
    }

    public void setId(int id) {
        this.id = id;
    }
}
