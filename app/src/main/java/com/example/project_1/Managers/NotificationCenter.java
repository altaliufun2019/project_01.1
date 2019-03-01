package com.example.project_1.Managers;

public class NotificationCenter {
    private static final NotificationCenter INSTANCE = new NotificationCenter();

    private NotificationCenter(){

    }

    public static NotificationCenter getInstance() {
        return INSTANCE;
    }


    public void data_loaded() {
        // TODO
    }
}
