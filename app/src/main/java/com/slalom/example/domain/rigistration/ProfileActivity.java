package com.slalom.example.domain.rigistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private String userID;

    private Button logout;
    private Button list;

    private Button edit;



    private Uri imageUri;

    boolean favCheck = false;
    private static final String TAG = "MyApp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        logout = (Button) findViewById(R.id.singOut);

        logout.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });




        list = (Button) findViewById(R.id.list);

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, UserList.class));
            }
        });

        edit = (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditUser.class));

                user = FirebaseAuth.getInstance().getCurrentUser();
                reference = FirebaseDatabase.getInstance().getReference("Users");
                userID = user.getUid();

                Log.d(TAG, user.getDisplayName()+user.getEmail());
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView greetingTextView = (TextView) findViewById(R.id.greeting);
        final TextView nameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView surnameTextView = (TextView) findViewById(R.id.surnameText);

        reference.child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = new User();
                Log.d(TAG, user.toString());

                User userProfile = snapshot.getValue(User.class);


                if (userProfile != null && userProfile.name!=null) {
                    String name = userProfile.name;
                    String email = userProfile.email;
                    String surname = userProfile.surname;

                    greetingTextView.setText("Добро пожаловать, " + name + "!");
                    nameTextView.setText(name);
                    emailTextView.setText(email);
                    surnameTextView.setText(surname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Что-то пошло не так!", Toast.LENGTH_LONG).show();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        int maxCount = 10000, currentCount = 0;
        while (user.getEmail() == null && currentCount < maxCount) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            currentCount++;
        }
        Log.d(TAG, user.getDisplayName() + user.getEmail() + "!!!!!!!!!!!");
        if (user != null && user.getDisplayName()!=null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            String surname = user.getPhoneNumber();

            greetingTextView.setText("Добро пожаловать, " + name + "!");
            nameTextView.setText(name);
            emailTextView.setText(email);
            surnameTextView.setText(surname);
        }

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }
}