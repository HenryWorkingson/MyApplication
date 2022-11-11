package com.example.myapplication;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class api extends Fragment {

    private ApiViewModel mViewModel;

    public static api newInstance() {
        return new api();
    }
    private ArrayList<String> names;
    private ListView listView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_api, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        // TODO: Use the ViewModel
        listView = (ListView) getView().findViewById(R.id.listViewApi);
        names = new ArrayList<String>();
        names.add("Pixabay");
        names.add("Otro Api");
        //ListAdapter adapter = new ListAdapter(this.getContext(), R.layout.fragment_api,names);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, names);
        NavController navController = Navigation.findNavController(getView());


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getContext(), "Has pulsado: "+ names.get(position), Toast.LENGTH_LONG).show();
                if(names.get(position).equals("Pixabay"))
                    navController.navigate(R.id.action_fragment_api_to_gallery);
            }
        });


    }

}