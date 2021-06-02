package com.slalom.example.domain.rigistration;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root = db.getReference().child("Users");

    private MyAdapter adapter;
    private List<User> list, showList;

    private Button favouritsBTN, profileBTN, inviteBtn;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        favouritsBTN = findViewById(R.id.favouritsBTN);
        favouritsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserList.this, favoritesUsers.class));
            }
        });



        profileBTN = findViewById(R.id.profileBTN);
        profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserList.this, ProfileActivity.class));
            }
        });



        recyclerView = findViewById(R.id.reciclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        showList = new ArrayList<>();

        adapter = new MyAdapter(this, showList);

        recyclerView.setAdapter(adapter);

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User model = dataSnapshot.getValue(User.class);
                    list.add(model);

                    if(model != null  && model.goalkeeper != null && model.goalkeeper.equals("goalkeeper")  ){
                        showList.add(model);
                    }

                }
                //showList = showList.stream().filter(x->x.goalkeeper.equals("goalkeeper")).collect(Collectors.toList());
                //Log.v("this", showList.stream().collect(Collectors.toList()).toString());

                //Log.v("this", showList.get(0).name);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);
        switch (item.getItemId())
        {
            case 101:
                Snackbar.make(findViewById(R.id.rootId), "Добавленно в избранное", Snackbar.LENGTH_LONG).show();
                return true;
            case 102:
                Snackbar.make(findViewById(R.id.rootId), "Удалено", Snackbar.LENGTH_LONG).show();
                return true;
        }

        return false;
    }
}