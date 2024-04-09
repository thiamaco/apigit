package com.example.retrofitfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Repositorio extends Fragment {

    ListView superListView;
    List<Results> listaDeInformacoes;
    private RecyclerView rvBoletim;
    private static final String ARG_REPO = "repositorio";
    private String repositorio;
    @SuppressLint("MissingInflatedId")

    public Repositorio() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenha os argumentos passados
        Bundle args = getArguments();
        if (args != null) {
            repositorio = args.getString(ARG_REPO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_repositorio, container, false);
        rvBoletim = rootView.findViewById(R.id.rvBoletim);
        obterDadosDaApi(repositorio);
        return rootView;
    }






    private void obterDadosDaApi(String username) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiService = retrofit.create(Api.class);
        Call<List<Results>> call = apiService.getRepositorio(username);
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                if (response.isSuccessful()) {
                    listaDeInformacoes = response.body();
                    if (listaDeInformacoes.size()>0){

                        MeuAdapter adaptador = new MeuAdapter(listaDeInformacoes);
                        RecyclerView.LayoutManager layout =
                                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rvBoletim.setLayoutManager(layout);
                        rvBoletim.setAdapter(adaptador);

                    } else{
                        Toast.makeText(getContext(), "Não há registro nesse periodo", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), "Erro ao obter informações, atualize a sessao", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}