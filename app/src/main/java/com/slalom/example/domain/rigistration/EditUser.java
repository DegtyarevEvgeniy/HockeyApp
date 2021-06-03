package com.slalom.example.domain.rigistration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static com.slalom.example.domain.rigistration.Notifications.CHANNEL_1_ID;

public class EditUser extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText nameSave, surnameSave, emailSave, ageSave;
    private Button save;
    private StorageReference storageReference;
    private ImageView profilePic;
    private Uri imageUri;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    String DISPLAY_NAME = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);


        nameSave = (EditText) findViewById(R.id.nameSave);


        profilePic = findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        save = (Button) findViewById(R.id.savechanges);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onCreate: " + user.getDisplayName());
            if (user.getDisplayName() != null) {
                nameSave.setText(user.getDisplayName());
                nameSave.setSelection(user.getDisplayName().length());
            }
        }


//    save.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            updateProfile();
//        }
//
//    });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && requestCode == RESULT_OK && data != null && data.getData()!=null){
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }
    private void choosePicture() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }
    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image ...");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(R.id.content), "Картинка загружается", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Ошибка загрузки", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        pd.setMessage("Process: " + (int) progressPercent + "%");
                    }
                });
    }

    private void updateProfile() {

        DISPLAY_NAME = nameSave.getText().toString();

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setDisplayName(DISPLAY_NAME)
                .build();

        firebaseUser.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditUser.this, "Ваш профиль успешно обновлен", Toast.LENGTH_SHORT).show();
                        Intent notificationIntent = new Intent(EditUser.this, EditUser.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(EditUser.this,
                                0, notificationIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(EditUser.this, CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.ic_baseline_sports_hockey_24)
                                        .setContentTitle("HockeyApp")
                                        .setContentText("Ваш профиль успешно обновлен")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                        .setContentIntent(contentIntent);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(EditUser.this);
                        notificationManager.notify(5, builder.build());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditUser.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                });

//        UserProfileChangeRequest updateProfile = new UserProfileChangeRequest.Builder()
//                .setDisplayName(nameSave.getText().toString())
//                .setPhotoUri(Uri.parse(surnameSave.getText().toString()))
//                .build();
//        user.updateProfile(updateProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(edit_user.this, "Профиль обновлен", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(edit_user.this, ProfileActivity.class));
//                    finish();
//                }else{
//                    Toast.makeText(edit_user.this, "Ошибка", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
    }

}
