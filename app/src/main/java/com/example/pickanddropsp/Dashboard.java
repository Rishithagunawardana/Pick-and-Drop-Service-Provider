package com.example.pickanddropsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class Dashboard extends AppCompatActivity {
    Button sigout;
    TextView name;
    FirebaseAuth mauth;
    FirebaseUser user;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        mauth = FirebaseAuth.getInstance();
        sigout = findViewById(R.id.logout);
        name = findViewById(R.id.username);
        DatabaseReference databaseRef= FirebaseDatabase.getInstance().getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String data = dataSnapshot.child("Service Provider").child("Users").child(uid).child("Name").getValue(String.class);

                name.setText(data);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


        sigout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mauth.signOut();
                soutser();
            }

        });

    }


    private void soutser() {
        Intent log = new Intent(Dashboard.this, MainActivity.class);
        startActivity(log);
        finish();
    }



    public void tovehiclereq(View view) {

        Intent log = new Intent(Dashboard.this, vehiclereq.class);
        startActivity(log);
    }





    public void drivers(View view) {

        Intent log = new Intent(Dashboard.this, Drivers.class);
        startActivity(log);
    }



    public void profile(View view) {

        Intent log = new Intent(Dashboard.this, profile.class);
        startActivity(log);
    }



    public void allreq(View view) {

        Intent log = new Intent(Dashboard.this, allreq.class);
        startActivity(log);
    }
}