package com.example.pickanddropsp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import static com.google.firebase.database.FirebaseDatabase.getInstance;

public class profile extends AppCompatActivity {
    TextView nic,profemail,profname;
    Button update,delete;
    FirebaseUser user;
    String uid;
    FirebaseAuth mAuth;
    private static final int IMAGE_REQEST = 2 ;
    private Uri imageuri;
    String TAG;
    ImageView profilemg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        delete = (Button) findViewById(R.id.deletepic);
        update = (Button) findViewById(R.id.uploadpic);
        profemail = (TextView) findViewById(R.id.uemail);
        nic = (TextView) findViewById(R.id.nic);
        profname = (TextView) findViewById(R.id.username);
        profilemg = (ImageView) findViewById(R.id.profilepic);
        DatabaseReference databaseRef = getInstance().getReference();

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                String data = dataSnapshot.child("Service Provider").child("Users").child(uid).child("Email").getValue(String.class);
                String data2 = dataSnapshot.child("Service Provider").child("Users").child(uid).child("NIC").getValue(String.class);
                String data3 = dataSnapshot.child("Service Provider").child("Users").child(uid).child("Name").getValue(String.class);

                profemail.setText(data);
                nic.setText(data2);
                profname.setText(data3);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        DatabaseReference f = databaseRef.child("Users").child(uid).child("Image_url");
        DatabaseReference d = databaseRef.child("Users");
        f.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String link = dataSnapshot.getValue(String.class);
                Picasso.get().load(link).into(profilemg);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.setValue(null);

                Toast.makeText(profile.this,"User Deleted! Please SignOut!",Toast.LENGTH_LONG).show();
                final   Intent start = new Intent(profile.this,Dashboard.class);
                startActivity(start);



            }

        });
    }
    private void openImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQEST && resultCode == RESULT_OK){
            imageuri = data.getData();

            uploadImage();
        }


    }

    private String getFileExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }


    private void uploadImage() {
        final ProgressDialog pd  = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageuri != null){
            StorageReference fileref = FirebaseStorage.getInstance().getReference().child("Service_Provider").child("User_prof_photo").child(System.currentTimeMillis()+"."+getFileExtention(imageuri));
            fileref.putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            Log.d("Download url",url);
                            FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Image_url").setValue(uri.toString());
                            pd.dismiss();
                        }
                    });
                }
            });








        }
    }









    public void todash(View view) {

        Intent log = new Intent(profile.this, Dashboard.class);
        startActivity(log);
    }

}