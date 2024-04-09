package com.example.retrofitfragment;


import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class PesquisarFragment extends Fragment {
    Button btnPesquisar;
    EditText editRepositorio;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_pesquisar, container, false);
        btnPesquisar= rootView.findViewById(R.id.btnPesquisar);
        editRepositorio = rootView.findViewById(R.id.editRepositorio);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editRepositorio.getText().toString().isEmpty()) {
                    Bundle args = new Bundle();
                    args.putString("repositorio", editRepositorio.getText().toString());
                    Repositorio meuFragmento = new Repositorio();
                    meuFragmento.setArguments(args);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();


                    transaction.replace(getId(), meuFragmento);

                    transaction.addToBackStack(null);

                    transaction.commit();

                }else{
                    Toast.makeText(getContext(),"Preencha o campos!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rootView;
    }




}
