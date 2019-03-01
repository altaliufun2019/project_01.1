package com.example.project_1.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.project_1.UIComponents.DataAdapter.DataNumber;
import com.example.project_1.UIComponents.DataAdapter.DataNumberAdapter;
import com.example.project_1.Managers.MessageController;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button clear;
    private Button get;
    private Button refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRecyclerView();
        initializeButtons();

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
                MessageController.INSTANCE.get();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageController.INSTANCE.refresh();
            }
        });
    }

}
