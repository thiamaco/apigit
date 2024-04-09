package com.example.retrofitfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MeuFragmento extends Fragment {
    List<Results> listaDeInformacoes;
    private RecyclerView rvDetalhes;
    private static final String ARG_REPOSITORIO_ID = "repositorioID";
    private static final String ARG_REPOSITORIO_NAME = "repositorioName";
    private String id;
    private String repos;
    public MeuFragmento() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtenha os argumentos passados
        Bundle args = getArguments();
        if (args != null) {
            id = args.getString(ARG_REPOSITORIO_ID);
            repos = args.getString(ARG_REPOSITORIO_NAME);
            Toast.makeText(getContext(), ""+id+repos, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meu_fragmento, container, false);
       rvDetalhes = rootView.findViewById(R.id.rvDetalhes);
        obterDadosDaApi(id, repos);
        return rootView;
    }



    private void obterDadosDaApi(String username, String repos) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiService = retrofit.create(Api.class);
        Call<List<Results>> call = apiService.getRepositorioInfo(username, repos);
        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                if (response.isSuccessful()) {
                    listaDeInformacoes = response.body();
                    if (listaDeInformacoes.size()>0){

                        MeuAdapterInfo adaptador = new MeuAdapterInfo(listaDeInformacoes);
                        RecyclerView.LayoutManager layout =
                                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        rvDetalhes.setLayoutManager(layout);
                        rvDetalhes.setAdapter(adaptador);

                    } else{
                        Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}