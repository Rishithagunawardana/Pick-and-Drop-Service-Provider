package com.example.pickanddropsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION.SDK_INT;

public class approved extends AppCompatActivity {
    EditText name ,status,type,fee,drname,drmobile;
    Button approve , back ;

    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Approved Tours");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved);
    // resources
        name = findViewById(R.id.pname);
        status = findViewById(R.id.status);
        type = findViewById(R.id.Vehicle);
        fee = findViewById(R.id.Fee);
        drname = findViewById(R.id.dname);
        drmobile = findViewById(R.id.dmobile);
        approve = findViewById(R.id.approve);
        back = findViewById(R.id.back);

        approve.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {


             String nme = name.getText().toString();
             String STATUS = status.getText().toString();
             String TYPE = type.getText().toString();
             String FEE = fee.getText().toString();
             String DRNAME = drname.getText().toString();
             String DRMOBILE = drmobile.getText().toString();



             Map newPost = new HashMap();
             newPost.put("Passenger_name", nme);
             newPost.put("Trip_Status", STATUS);
             newPost.put("Vehicle_Type", TYPE);
             newPost.put("Fee", FEE);
             newPost.put("Driver_Name", DRNAME);
             newPost.put("Driver_Mobile_No", DRMOBILE);

             current_user_db.setValue(newPost);
             current_user_db.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     notification();

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

             Toast.makeText(approved.this, "approvel Sent", Toast.LENGTH_SHORT).show();

             Intent start = new Intent(approved.this, Dashboard.class);
             startActivity(start);
             finish();
         }


     });


    }
    // notification part
    // add this part to initialize  -    notification();




    private void notification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"n")
                .setContentText("Pick & Drop")
                .setSmallIcon(R.drawable.logocir)
                .setAutoCancel(true)
                .setContentText(" the Approval Sent !");
        NotificationManagerCompat managerCompat =   NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}