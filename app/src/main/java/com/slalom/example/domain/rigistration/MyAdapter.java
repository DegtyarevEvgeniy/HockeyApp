package com.slalom.example.domain.rigistration;

import android.content.Context;
import android.os.Build;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<User> mList;
    Context context;


    FirebaseDatabase database = FirebaseDatabase.getInstance();




    @RequiresApi(api = Build.VERSION_CODES.N)
    public MyAdapter(Context context , List<User> mList){

        this.mList = mList;
        this.context = context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item , parent ,false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User model = mList.get(position);
        holder.name.setText(model.getName());
        holder.surname.setText(model.getSurname());
       // holder.email.setText(model.getEmail());
//        holder.goalkeeper.setText(model.getGoalkeeper());
   //     holder.telegramTAG.setText(model.getTg());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView name , surname , email, goalkeeper, telegramTAG;
        CardView cardView;
        LinearLayout linearLayout;
        Button inviteBtn;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_text);
            surname = itemView.findViewById(R.id.surname_text);
            //email = itemView.findViewById(R.id.email_text);
//            goalkeeper = itemView.findViewById(R.id.goalkeeper_text);
//            telegramTAG = itemView.findViewById(R.id.telegram_text);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnCreateContextMenuListener(this);




        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Выбери что-нибудь");
            menu.add(getAdapterPosition(), 101, 0, "Добавить в избранное");
        }


    }

}
