package com.example.project_1.Managers;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.example.project_1.Constants.Constants;

import java.util.ArrayList;

public class NotificationCenter {
    private static final NotificationCenter INSTANCE = new NotificationCenter();
    public static final int DATA_LOADING = Constants.Tasks.FETCH_DATA;
    public static final int CONNECTION_CHANGE = Constants.Tasks.CONNECTION_CHANGE;
    private SparseArray<ArrayList<Object>> observers = new SparseArray<>();
    private ArrayList<Integer> allowedTasks = new ArrayList<>();

    private NotificationCenter(){
        allowedTasks.add(Constants.Tasks.FETCH_DATA);
        allowedTasks.add(Constants.Tasks.CONNECTION_CHANGE);
    }

    public static NotificationCenter getInstance() {
        return INSTANCE;
    }

    public interface NotificationTarget{
        public void notified(int taskID);
    }

    public Boolean addTask(int taskID) {
        allowedTasks.add(taskID);
        System.out.println("success");
        return false;
    }

    public void conn_changed() {
        int taskID = NotificationCenter.CONNECTION_CHANGE;
        for(Object observer: observers.get(taskID)) {
            ((NotificationTarget) observer).notified(taskID);
        }
    }

    public void data_loaded() {
        int taskID = NotificationCenter.DATA_LOADING;
        for(Object observer: observers.get(taskID)) {
            ((NotificationTarget) observer).notified(taskID);
        }
    }

    public Boolean register(@NonNull Object observer, int taskID) {
        if(!allowedTasks.contains(taskID))
            return false;
        ArrayList<Object> taskObservers = observers.get(taskID);
        if (taskObservers == null)
            observers.put(taskID, (taskObservers = new ArrayList<Object>()));
        if (taskObservers.contains(observer))
            return false;
        taskObservers.add(observer);
        return true;
    }

    public Boolean unregister(@NonNull Object observer, int taskID) {
        if (!allowedTasks.contains(taskID))
            return false;
        ArrayList<Object> taskObservers = observers.get(taskID);
        if (taskObservers == null)
            return false;
        if (!taskObservers.contains(observer))
            return false;
        taskObservers.remove(observer);
        return true;
    }
}
