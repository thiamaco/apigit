package com.example.retrofitfragment;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



       carregarFragmentoInicial();
    }

    private void carregarFragmentoInicial() {
        // Criar uma instância do fragmento que você deseja abrir
        PesquisarFragment meuFragmento = new PesquisarFragment();

        // Iniciar uma transação de fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Substituir a MainActivity pelo fragmento
        transaction.replace(android.R.id.content, meuFragmento);

        // Adicionar a transação à pilha de retrocesso (back stack)
        transaction.addToBackStack(null);

        // Confirmar a transação
        transaction.commit();
    }
}