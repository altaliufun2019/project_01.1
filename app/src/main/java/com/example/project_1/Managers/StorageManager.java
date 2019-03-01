package com.example.project_1.Managers;

import android.app.Application;
import android.support.annotation.NonNull;

import com.example.project_1.Managers.MessageControllers.DispatchQueue;
import com.example.project_1.UIComponents.DataAdapter.DataNumber;

import java.util.ArrayList;

public class StorageManager {
    private static final StorageManager INSTANCE = new StorageManager();
    private final DispatchQueue mQueue = new DispatchQueue("storage");

    private StorageManager() {
    }

    public static StorageManager getInstance() {
        return INSTANCE;
    }

    public void save(@NonNull ArrayList<DataNumber> new_data) {
        //TODO
    }

    public ArrayList<DataNumber> load() {
        // TODO
        return new ArrayList<>();
    }
}
