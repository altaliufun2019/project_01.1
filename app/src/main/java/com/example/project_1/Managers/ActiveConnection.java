package com.example.project_1.Managers;

public class ActiveConnection extends Thread{
    private static ActiveConnection ac_con = new ActiveConnection();

    private ActiveConnection(){

    }

    public static ActiveConnection getInstance() {
        return ac_con;
    }


    @Override
    public void run() {
        while(true) {

        }
    }
}
