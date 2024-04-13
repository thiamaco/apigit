package com.example.retrofitfragment;


import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.security.AccessController;
import java.util.List;


public class MeuAdapterInfo extends RecyclerView.Adapter<MeuAdapterInfo.ViewHolder> {
    Results materias;
    private FragmentManager fragmentManager;
    public MeuAdapterInfo(Results materias) {
        this.materias = materias;
        this.fragmentManager = fragmentManager;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView txtRepositorio;
        final TextView txtAcessar;
        final ImageView imgRepositorio;
        final TextView txtNota;

        public ViewHolder(View view) {
            super(view);


            txtAcessar = (TextView) view.findViewById(R.id.txtAcessar);
            txtRepositorio = (TextView) view.findViewById(R.id.txtRepositorio);
            txtNota = (TextView)view.findViewById(R.id.txtFullName);
            imgRepositorio =(ImageView) view.findViewById(R.id.imgRepositorio);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itens_repositorio_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Results dados = materias;
        holder.txtRepositorio.setText(dados.getName());
        holder.txtNota.setText(dados.getFullName());
        Glide.with(holder.imgRepositorio.getContext())
                .load(dados.getOwner().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.imgRepositorio);
        holder.txtAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               // Bundle args = new Bundle();
                //args.putString("repositorioID", dados.getFullName());
               // MeuFragmento meuFragmento = new MeuFragmento();
                //meuFragmento.setArguments(args);
                //FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                //FragmentTransaction transaction = fragmentManager.beginTransaction();

                // Use replace() em vez de add() para substituir o fragmento atual pelo novo fragmento
                //transaction.replace(R.id.container, meuFragmento);

               // transaction.addToBackStack(null);
               // transaction.commit();
            }
        });

    }


    private void handleCheckBoxClick(int position, boolean check) {
        Log.d("RecyclerView", "CheckBox na posição " + position+ "status: "+check);
    }
    @Override
    public int getItemCount() {
        return materias.hashCode();
    }
}