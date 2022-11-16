package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Class.Notas;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addNotas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addNotas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button buttonSubmit;
    private EditText editTitulo, editDescripcion;
    private NotasViewModel notasViewModel;

    public addNotas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addWord.
     */
    // TODO: Rename and change types and number of parameters
    public static addNotas newInstance(String param1, String param2) {
        addNotas fragment = new addNotas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_notas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        notasViewModel = new ViewModelProvider(this).get(NotasViewModel.class);

        buttonSubmit = getView().findViewById(R.id.buttonSubmit);
        editTitulo = getView().findViewById(R.id.add_titulo_nota);
        editDescripcion = getView().findViewById(R.id.add_descripcion_nota);
        buttonSubmit.setEnabled(false);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTitulo, 0);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String titulo = editTitulo.getText().toString().trim();
                String descripcion = editDescripcion.getText().toString().trim();
                buttonSubmit.setEnabled(!titulo.isEmpty() && !descripcion.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editTitulo.addTextChangedListener(textWatcher);
        editDescripcion.addTextChangedListener(textWatcher);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder builder=new NotificationCompat.Builder(getActivity(), "My Notification");
                builder.setContentTitle("Has creado una nota");
                builder.setContentText(editTitulo.getText().toString().trim()+ " : "+editDescripcion.getText().toString().trim());
                builder.setSmallIcon(R.drawable.ic_baseline_checklist_24);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat= NotificationManagerCompat.from(getActivity());
                managerCompat.notify(1,builder.build());


                String titulo = editTitulo.getText().toString().trim();
                String descripcion = editDescripcion.getText().toString().trim();
                Notas notas = new Notas(titulo,descripcion);
                notasViewModel.insertNotas(notas);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                NavController navController= Navigation.findNavController(view);
                navController.navigateUp();
            }
        });
    }

}