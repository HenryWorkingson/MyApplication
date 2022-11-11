package com.example.myapplication;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.Class.Notas;
import com.example.myapplication.Class.NotasDao;
import com.example.myapplication.Class.NotasDatabase;
import com.example.myapplication.Class.NotasRepository;

import java.util.List;

public class NotasViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private NotasRepository notasRepository;

    public NotasViewModel(@NonNull Application application){
        super(application);
        notasRepository=new NotasRepository(application);
    }

    public LiveData<List<Notas>> getAllNotasLive() {
        return notasRepository.getAllNotasLive();
    }
    void insertNotas(Notas...notas){
        notasRepository.insertNotas(notas);
    }

    LiveData<List<Notas>> findnotasWithPatten(String patten){
        return notasRepository.findnotasWithPatten(patten);
    }
    void deleteAllNotas(){
        notasRepository.deleteAllNotas();
    }
    void updateNota(Notas...notas){notasRepository.updateNotas(notas);}
    void deleteNotas(Notas...notas){
        notasRepository.deleteNotas(notas);
    }

}