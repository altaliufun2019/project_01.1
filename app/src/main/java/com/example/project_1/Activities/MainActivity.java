package com.example.project_1.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project_1.Constants.Constants;
import com.example.project_1.Managers.NotificationCenter;
import com.example.project_1.Managers.StorageManager;
import com.example.project_1.UIComponents.DataAdapter.DataNumber;
import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter;
import com.example.project_1.Managers.MessageController;
import com.example.project_1.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationTarget {
    private Button clear;
    private Button get;
    private Button refresh;
    private ConnectionMonitor monitor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StorageManager.Companion.getInstance().setContext(getApplicationContext());

        NotificationCenter.getInstance().register(this, Constants.Tasks.FETCH_DATA);
        NotificationCenter.getInstance().register(this, Constants.Tasks.CONNECTION_CHANGE);

        // starting toolbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initializeRecyclerView();
        initializeButtons();

        monitor = new ConnectionMonitor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        monitor.disable();
        NotificationCenter.getInstance().unregister(this, Constants.Tasks.FETCH_DATA);
    }

    @Override
    public void notified(int taskID) {

        switch (taskID) {
            case Constants.Tasks.FETCH_DATA:
                MessageController.INSTANCE.changeData();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    // initializing and creating recycler view
    private void initializeRecyclerView(){
        RecyclerView rvData = findViewById(R.id.numList);
        ArrayList<DataNumber> dataNumbers = (ArrayList<DataNumber>) MessageController.INSTANCE.getMData();
        DataNumberAdapter adapter = new DataNumberAdapter(dataNumbers);
        rvData.setAdapter(adapter);
        rvData.setLayoutManager(new LinearLayoutManager(this));
        MessageController.INSTANCE.setMAdapter(adapter);
    }

    // importing and initializing buttons from layout
    private void initializeButtons(){
        clear = findViewById(R.id.clear);
        get = findViewById(R.id.get);
        refresh = findViewById(R.id.refresh);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageController.INSTANCE.clear();
            }
        });

        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(monitor.getConnection()) {
                    MessageController.INSTANCE.fetch(false);
                }
                else {
                    MessageController.INSTANCE.fetch(true);
                }
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageController.INSTANCE.fetch(true);
            }
        });
    }

    /**
     * a monitor class for network connectivity
     */
    private class ConnectionMonitor extends ConnectivityManager.NetworkCallback{
        final private NetworkRequest networkRequest;
        private Context context;
        private Boolean isConnected = false;

        ConnectionMonitor() {
            networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .build();
            enable(MainActivity.this);
        }

        Boolean getConnection() {
            return isConnected;
        }

        void enable(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.registerNetworkCallback(networkRequest, this);
            this.context = context;
            NetworkInfo network = cm.getActiveNetworkInfo();
            if (network == null || !network.isConnected())
                Glide.with(context).load(R.drawable.loading).into((ImageView) findViewById(R.id.ivtest));
        }

        void disable() {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            cm.unregisterNetworkCallback(this);
            this.context = null;
        }

        @Override
        public void onAvailable(Network network) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = true;
                    ((ImageView) findViewById(R.id.ivtest)).setImageResource(0);
                    ((TextView) findViewById(R.id.connecting)).setText("");
                }
            });
        }

        @Override
        public void onLost(Network network) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    isConnected = false;
                    Glide.with(context).load(R.drawable.loading).into((ImageView) findViewById(R.id.ivtest));
                    ((TextView) findViewById(R.id.connecting)).setText(" connecting...");
                }
            });
        }
    }

    // a self defined way to check connectivity
//    private void hasConnection(){
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    Context context = getApplicationContext();
//                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                    NetworkInfo info = cm.getActiveNetworkInfo();
//                    if (isConnected != (info != null && info.isConnected())) {
//                        isConnected = !isConnected;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                NotificationCenter.getInstance().conn_changed();
//                            }
//                        });
//                    }
//                }
//            }
//        });
//        t.start();
//    }

//    private void connectionChanged() {
//        if (!isConnected) {
//            Glide.with(this).load(R.drawable.loading).into((ImageView) findViewById(R.id.ivtest));
//            ((TextView) findViewById(R.id.connecting)).setText(" connecting...");
//        }
//        else {
//            ((ImageView) findViewById(R.id.ivtest)).setImageResource(0);
//            ((TextView) findViewById(R.id.connecting)).setText("");
//        }
//    }
}
