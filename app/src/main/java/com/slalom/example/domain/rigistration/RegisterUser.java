package com.slalom.example.domain.rigistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.slalom.example.domain.rigistration.Notifications.CHANNEL_1_ID;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView banner, registerUser;
    private EditText editTextName, editTextSurname, editTextEmail, editTextPassword, editTextTg;
    private ProgressBar progressBar;
    private Switch switchK;
    private String floatingAction;
    private String word;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

//        banner = (TextView) findViewById(R.id.banner);
//        banner.setOnClickListener(this);

        switchK = (Switch) findViewById(R.id.choose_goalkip);
        switchK.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.name);
        editTextSurname = (EditText) findViewById(R.id.surname);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextTg = (EditText) findViewById(R.id.tg);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        switchK.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    word = "вратарь";
                }else{
                    word = "играк";
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.banner:
//                startActivity(new Intent(this, MainActivity.class));
//                break;
            case R.id.registerUser:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String goalkeeper = word;
        String telegram = editTextTg.getText().toString().trim();
        String age = editTextSurname.getText().toString().trim();
        String time = editTextSurname.getText().toString().trim();
        String playce = editTextSurname.getText().toString().trim();



        if(name.isEmpty()){
            editTextName.setError("Пустое поле!");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Введите почту!");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Пожалуйста, введите верную почту!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Недопустимый пароль!");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Минимальный размер пароля 6 символов!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(name, surname, email, goalkeeper, age, time, playce, floatingAction, telegram);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Пользователь успешно зарегистрирован!", Toast.LENGTH_LONG).show();
                                        Intent notificationIntent = new Intent(RegisterUser.this, RegisterUser.class);
                                        PendingIntent contentIntent = PendingIntent.getActivity(RegisterUser.this,
                                                0, notificationIntent,
                                                PendingIntent.FLAG_CANCEL_CURRENT);

                                        NotificationCompat.Builder builder =
                                                new NotificationCompat.Builder(RegisterUser.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.ic_baseline_sports_hockey_24)
                                                        .setContentTitle("HockeyApp")
                                                        .setContentText("Пользователь успешно зарегистрирован!")
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                        .setContentIntent(contentIntent);

                                        NotificationManagerCompat notificationManager =
                                                NotificationManagerCompat.from(RegisterUser.this);
                                        notificationManager.notify(4, builder.build());
                                        progressBar.setVisibility((View.GONE));
                                    }else{
                                        Toast.makeText(RegisterUser.this, "Ошибка регистрации! Попробуйте снова!", Toast.LENGTH_LONG).show();
                                        Intent notificationIntent = new Intent(RegisterUser.this, RegisterUser.class);
                                        PendingIntent contentIntent = PendingIntent.getActivity(RegisterUser.this,
                                                0, notificationIntent,
                                                PendingIntent.FLAG_CANCEL_CURRENT);

                                        NotificationCompat.Builder builder =
                                                new NotificationCompat.Builder(RegisterUser.this, CHANNEL_1_ID)
                                                        .setSmallIcon(R.drawable.ic_baseline_sports_hockey_24)
                                                        .setContentTitle("HockeyApp")
                                                        .setContentText("Ошибка регистрации! Попробуйте снова!")
                                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                        .setContentIntent(contentIntent);

                                        NotificationManagerCompat notificationManager =
                                                NotificationManagerCompat.from(RegisterUser.this);
                                        notificationManager.notify(5, builder.build());
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            });


                        }else{
                            Toast.makeText(RegisterUser.this, "Ошибка регистрации!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });


    }
}