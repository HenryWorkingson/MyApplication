package com.example.myapplication.Class;
import android.app.Activity;
import android.content.Intent;
import android.location.GnssAntennaInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.NotasViewModel;
import com.example.myapplication.R;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private NotasViewModel notasViewModel;
    private List<Notas> allNotas= new ArrayList<>();
    public void setAllNotas(List<Notas> allNotas){
        this.allNotas=allNotas;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view= layoutInflater.inflate(R.layout.cell_card,parent,false);
        final MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        final Notas notas=allNotas.get(position);
        holder.textViewNumber.setText(String.valueOf(notas.getId()));
        holder.textViewTitulo.setText(notas.getTitulo());
        holder.itemView.setTag(notas);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Bundle bundle=new Bundle();
               bundle.putInt("id",notas.getId());
               bundle.putString("titulo",notas.getTitulo());
               bundle.putString("descripcion",notas.getDescripcion());
               NavController navController = Navigation.findNavController(holder.itemView);
               navController.navigate(R.id.action_fragment_notas_to_detail_notas,bundle);
           }
       });


    }





    @Override
    public int getItemCount() {
        return allNotas.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNumber,textViewTitulo;
        int position;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNumber=itemView.findViewById(R.id.textViewNumber);
            textViewTitulo=itemView.findViewById(R.id.textViewTitulo);

        }

        public int getPosition(int position){
            return this.position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getTextViewNumber() {
            return textViewNumber.toString();
        }
    }


}
