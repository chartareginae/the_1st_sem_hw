package com.mipt.bayandinamariya.model;

public abstract class WorkingPerson {

    public abstract void work(int hours);

    public boolean goHome(String time, String currentTime) {
        return time != null && time.equals(currentTime);
    }
}