package com.example.project_1.Activities;

import android.content.Context;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.bumptech.glide.Glide;
import com.example.project_1.Constants.Constants;
import com.example.project_1.Managers.ActiveConnection;
import com.example.project_1.Managers.NotificationCenter;
import com.example.project_1.UIComponents.DataAdapter.DataNumber;
import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter;
import com.example.project_1.Managers.MessageController;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NotificationCenter.NotificationTarget {
    private Button clear;
    private Button get;
    private Button refresh;
    public static Boolean isConnected = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotificationCenter.getInstance().register(this, Constants.Tasks.FETCH_DATA);
        NotificationCenter.getInstance().register(this, Constants.Tasks.CONNECTION_CHANGE);

        // starting toolbar
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        initializeRecyclerView();
        initializeButtons();
        hasConnection();
        connectionChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationCenter.getInstance().unregister(this, Constants.Tasks.FETCH_DATA);
    }

    @Override
    public void notified(int taskID) {

        switch (taskID) {
            case Constants.Tasks.FETCH_DATA:
                MessageController.INSTANCE.changeData();
                break;
            case Constants.Tasks.CONNECTION_CHANGE:
                connectionChanged();

        }
    }

    private void hasConnection(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    Context context = getApplicationContext();
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = cm.getActiveNetworkInfo();
                    if (isConnected != (info != null && info.isConnected())) {
                        isConnected = !isConnected;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                NotificationCenter.getInstance().conn_changed();
                            }
                        });
                    }
                }
            }
        });
        t.start();
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
        ArrayList<DataNumber> dataNumbers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataNumbers.add(new DataNumber(i, new Date()));
        }
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
                if(isConnected) {
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

    private void connectionChanged() {
        if (!isConnected)
            Glide.with(this).load(R.drawable.loading).into((ImageView) findViewById(R.id.ivtest));
        else
            ((ImageView) findViewById(R.id.ivtest)).setImageResource(0);
    }
}
