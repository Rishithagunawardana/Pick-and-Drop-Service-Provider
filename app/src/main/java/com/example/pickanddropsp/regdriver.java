package com.example.pickanddropsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class regdriver extends AppCompatActivity {
    EditText Name,address,phone, email, password,nic;
    Button regbtn;
    FirebaseFirestore fstore;

    FirebaseAuth fauth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regdriver);
        database = FirebaseDatabase.getInstance();
        Name = findViewById(R.id.driname);
        address = findViewById(R.id.adress);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        nic = findViewById(R.id.nic);
        regbtn = findViewById(R.id.reg);
        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = email.getText().toString().trim();
                String mailpass = password.getText().toString().trim();

                if (TextUtils.isEmpty(mail)) {
                    email.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(mailpass)) {
                    password.setError("Password Required");
                    return;
                }

                if (mailpass.length() < 6) {
                    password.setError("Password must be 6 char above");
                    return;
                }


                fauth.createUserWithEmailAndPassword(mail, mailpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            Toast.makeText(regdriver.this, "Driver Initialized!..", Toast.LENGTH_LONG).show();
                            String user_id = fauth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Service Provider").child("Registered Drivers").child(user_id);


                            String nme  = Name.getText().toString();
                            String add  = address.getText().toString();
                            String pho  = phone.getText().toString();
                            String mal = email.getText().toString();
                            String malpass = password.getText().toString();
                            String ni = nic.getText().toString();

                            Map newPost = new HashMap();
                            newPost.put("Name",nme);
                            newPost.put("Address",add);
                            newPost.put("Phone",pho);
                            newPost.put("Email",mal);
                            newPost.put("Password",malpass);
                            newPost.put("NIC NO",ni);


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
                            final Intent start = new Intent(regdriver.this,Dashboard.class);
                            startActivity(start);

                        }

                        else {
                            Toast.makeText(regdriver.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }




            private void notification() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    NotificationChannel channel = new NotificationChannel("n","n", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);

                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(regdriver.this,"n")
                        .setContentText("Pick & Drop")
                        .setSmallIcon(R.drawable.logocir)
                        .setAutoCancel(true)
                        .setContentText("New Driver Registered!");
                NotificationManagerCompat managerCompat =   NotificationManagerCompat.from(regdriver.this);
                managerCompat.notify(999,builder.build());
            }

        });



    }



    public void todash(View view) {

        Intent log = new Intent(regdriver.this,Drivers.class);
        startActivity(log);
    }

}