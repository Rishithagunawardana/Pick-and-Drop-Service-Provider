package com.example.pickanddropsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class showdrivers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdrivers);
    }

    public void todash(View view) {

        Intent log = new Intent(showdrivers.this, Drivers.class);
        startActivity(log);
    }





}