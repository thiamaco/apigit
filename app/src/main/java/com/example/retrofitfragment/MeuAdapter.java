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


public class MeuAdapter extends RecyclerView.Adapter<MeuAdapter.ViewHolder> {
    List<Results> materias;
    Context context;
    public MeuAdapter(List<Results> materias) {
        this.materias = materias;

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
                .inflate(R.layout.itens_repositorio, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Results dados = materias.get(position);
        holder.txtRepositorio.setText(dados.getName());
        holder.txtNota.setText(dados.getFullName());
        Glide.with(holder.imgRepositorio.getContext())
                .load(dados.getOwner().getAvatarUrl())
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.imgRepositorio);
    holder.txtAcessar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            Bundle args = new Bundle();
            args.putString("repositorioID", dados.getOwner().getLogin());
            args.putString("repositorioName", dados.getName());
            MeuFragmento meuFragmento = new MeuFragmento();
            meuFragmento.setArguments(args);
            FragmentTransaction fragmentTransaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, meuFragmento);

            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.commit();
        }
    });
    }


    private void handleCheckBoxClick(int position, boolean check) {
        Log.d("RecyclerView", "CheckBox na posição " + position+ "status: "+check);
    }
    @Override
    public int getItemCount() {
        return materias.size();
    }
}