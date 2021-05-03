package com.example.pickanddropsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Drivers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
    }





    public void registerdriver(View view) {

        Intent log = new Intent(Drivers.this, regdriver.class);
        startActivity(log);
    }
    public void showdrivers(View view) {

        Intent log = new Intent(Drivers.this, showdrivers.class);
        startActivity(log);
    }

    public void dash(View view) {

        Intent log = new Intent(Drivers.this, Dashboard.class);
        startActivity(log);
    }
}

