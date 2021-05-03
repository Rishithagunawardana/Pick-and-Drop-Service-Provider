package com.example.pickanddropsp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText email,pass;
    TextView passreset;
    Button log;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.Email);
        pass  = findViewById(R.id.password);
        log = findViewById(R.id.Signin);
        fauth= FirebaseAuth.getInstance();
       passreset = findViewById(R.id.forgetpass);



        //-----------------password reset section
        passreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetmail =  new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog  =  new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ..");
                passwordResetDialog.setMessage("Enter Your Registered Email to Send the Reset Link .. ");
                passwordResetDialog.setView(resetmail);


                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetmail.getText().toString();
                        fauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Reset Mail Sent ",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Reset Mail Not Sent " + e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
        //------------ end of password reset section




        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m = email.getText().toString().trim();
                String p = pass.getText().toString().trim();

                if (TextUtils.isEmpty(m)){
                    email.setError("Email Required");
                    return;
                }

                if (TextUtils.isEmpty(p)){
                    pass.setError("Password Required");
                    return;
                }

                if (p.length()<6){
                    pass.setError("Password must be 6 char above");
                    return;
                }
                fauth.signInWithEmailAndPassword(m,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            FirebaseUser user = fauth.getCurrentUser();
                            if (user.isEmailVerified()){
                                Toast.makeText(MainActivity.this,"Logged in Successfully" ,Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),Dashboard.class));

                            }else {
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this,"Email Verification Sent ",Toast.LENGTH_LONG).show();

                                    }
                                });
                            }



                        }else{

                            Toast.makeText(MainActivity.this,"Error !!" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();





                        }


                    }
                });


            }
        });


    }







    public void tosignup(View view) {

        Intent log = new Intent(MainActivity.this, Register.class);
        startActivity(log);
    }
}