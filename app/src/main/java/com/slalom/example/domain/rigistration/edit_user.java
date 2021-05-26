package com.slalom.example.domain.rigistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class edit_user extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText nameSave, surnameSave, emailSave, ageSave;
    private Button save;
    private ImageView userImg;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);



        nameSave = (EditText) findViewById(R.id.nameSave);
        surnameSave = (EditText) findViewById(R.id.surnameSave);
        emailSave = (EditText) findViewById(R.id.emailSave);
        ageSave = (EditText) findViewById(R.id.ageSave);




//    save.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            updateProfile();
//        }
//
//    });

    }

    private void updateProfile() {
        UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameSave.getText().toString())
                .setPhotoUri(Uri.parse(surnameSave.getText().toString()))
                .build();
        user.updateProfile(updateProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(edit_user.this, "Profile Updated", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(edit_user.this, ProfileActivity.class));
                    finish();
                }
            }
        });
    }


}