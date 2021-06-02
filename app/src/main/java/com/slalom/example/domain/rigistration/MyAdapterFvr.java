package com.slalom.example.domain.rigistration;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapterFvr extends RecyclerView.Adapter<com.slalom.example.domain.rigistration.MyAdapterFvr.MyViewHolder> {


    List<User> mList;
    Context context;

    ImageButton fvrt_btn;
    DatabaseReference favouriteref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyAdapterFvr(Context context, List<User> mList) {

        this.mList = mList;
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.mitem, parent, false);
        return new com.slalom.example.domain.rigistration.MyAdapterFvr.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        User model = mList.get(position);
//        holder.name.setText(model.getName());
//        holder.surname.setText(model.getSurname());
//        // holder.email.setText(model.getEmail());
//        holder.goalkeeper.setText(model.getGoalkeeper());
//        holder.telegramTAG.setText(model.getTg());

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, surname, email, goalkeeper, telegramTAG;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            name = itemView.findViewById(R.id.name_text);
//            surname = itemView.findViewById(R.id.surname_text);
//            //email = itemView.findViewById(R.id.email_text);
//            goalkeeper = itemView.findViewById(R.id.goalkeeper_text);
//            telegramTAG = itemView.findViewById(R.id.telegram_text);
//

        }
    }

}

