package com.example.myapplication;

import static android.content.Intent.getIntent;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.Class.Notas;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link detail_notas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class detail_notas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText editTitulo, editDescripcion;
    private NotasViewModel notasViewModel;
    private int id;
    private List<Notas> allNotas= new ArrayList<>();
    private Button button;

    public detail_notas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment detail_notas.
     */
    // TODO: Rename and change types and number of parameters
    public static detail_notas newInstance(String param1, String param2) {
        detail_notas fragment = new detail_notas();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_notas, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        editTitulo=getView().findViewById(R.id.detail_nota_titulo);
        editDescripcion=getView().findViewById(R.id.detail_nota_descripcion);
        editTitulo.setText(getArguments().getString("titulo"), TextView.BufferType.EDITABLE);
        editDescripcion.setText(getArguments().getString("descripcion"),TextView.BufferType.EDITABLE);
        notasViewModel = new ViewModelProvider(this).get(NotasViewModel.class);

        button=getView().findViewById(R.id.Summit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = editTitulo.getText().toString().trim();
                String descripcion = editDescripcion.getText().toString().trim();
                int id=getArguments().getInt("id");
                Notas notas=new Notas(titulo,descripcion);
                notas.setTitulo(titulo);
                notas.setDescripcion(descripcion);
                notas.setId(id);
                notasViewModel.updateNota(notas);

            }
        });



    }
}