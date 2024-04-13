package com.example.retrofitfragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MeuFragmento extends Fragment {
    Results result;
    private RecyclerView rvDetalhes;
    private static final String ARG_REPOSITORIO_ID = "repositorioID";
    private static final String ARG_REPOSITORIO_NAME = "repositorioName";
    private String id;
    private String repos;
    private TextView nome, url,descricao,branch,linguagem, id2;
    private Button voltar;

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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meu_fragmento, container, false);
       //rvDetalhes = rootView.findViewById(R.id.rvDetalhes);

        nome =rootView.findViewById(R.id.txtName);
        id2 =rootView.findViewById(R.id.txtID);
        branch =rootView.findViewById(R.id.txtBranch);
        descricao =rootView.findViewById(R.id.txtDescription);
        url =rootView.findViewById(R.id.txtURL);
        linguagem =rootView.findViewById(R.id.txtLinguage);
        voltar =rootView.findViewById(R.id.btnVoltar);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putString("repositorio", id    );
                Repositorio meuFragmento = new Repositorio();
                meuFragmento.setArguments(args);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, meuFragmento);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        });
        obterDadosDaApi(id, repos);
        return rootView;
    }



    private void obterDadosDaApi(String username, String repos) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiService = retrofit.create(Api.class);
        Call<Results> call = apiService.getRepositorioInfo(username, repos);
        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    if (result!=null){

                        nome.setText(result.getName());
                        id2.setText(String.valueOf( result.getId()));
                        branch.setText(result.getDefault_branch());
                        descricao.setText(result.getDescription());
                        url.setText(result.getHtmlUrl());
                        linguagem.setText(result.getLanguage());

                    } else{
                        Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getContext(), ""+response.body(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(getContext(), "Falha na conexão", Toast.LENGTH_SHORT).show();
            }
        });
    }
}