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

public class Register extends AppCompatActivity {

    EditText name, email, password,nic;
    Button regbtn;
    FirebaseFirestore fstore;

    FirebaseAuth fauth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        database = FirebaseDatabase.getInstance();
        name = findViewById(R.id.name);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        nic = findViewById(R.id.nic);
        regbtn = findViewById(R.id.Signup);
        fauth = FirebaseAuth.getInstance();
        fstore= FirebaseFirestore.getInstance();


        if (fauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
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
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            String user_id = fauth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Service Provider").child("Users").child(user_id);


                            String nme  = name.getText().toString();
                            String mal = email.getText().toString();
                            String malpass = password.getText().toString();
                            String ni = nic.getText().toString();


                            Map newPost = new HashMap();
                            newPost.put("Name",nme);
                            newPost.put("Email",mal);
                            newPost.put("Password",malpass);
                            newPost.put("NIC",ni);

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
                            final Intent start = new Intent(Register.this,MainActivity.class);
                            startActivity(start);


                        }

                        else {
                            Toast.makeText(Register.this, "Error !! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Register.this,"n")
                        .setContentText("Pick & Drop")
                        .setSmallIcon(R.drawable.logocir)
                        .setAutoCancel(true)
                        .setContentText("Thank you for Using Pick and Drop Service");
                NotificationManagerCompat managerCompat =   NotificationManagerCompat.from(Register.this);
                managerCompat.notify(999,builder.build());
            }
        });
    }











    public void tosignin(View view) {

        Intent log = new Intent(Register.this, MainActivity.class);
        startActivity(log);
    }

}