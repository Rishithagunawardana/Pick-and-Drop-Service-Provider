package com.example.pickanddropsp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        Context context;
        ArrayList<User>list;


    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v  = LayoutInflater.from(context).inflate(R.layout.activity_item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Approved Driver Tours");
        User user = list.get(position);
        holder.name.setText(user.getName());
        holder.date.setText(user.getDate());
        holder.destination.setText(user.getDestination());
        holder.passengers.setText(user.getPassengers());
        holder.telephone.setText(user.getTelno());
        holder.actype.setText(user.getActype());





        holder.accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    Intent start = new Intent(context,approved.class);
                    context.startActivity(start);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,date,destination,passengers,telephone,actype;
        Button accept;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
            destination = itemView.findViewById(R.id.destination);
            passengers = itemView.findViewById(R.id.passengers);
            telephone = itemView.findViewById(R.id.telephone);
            actype= itemView.findViewById(R.id.actype);
            accept = itemView.findViewById(R.id.accept);
        }
    }

}
