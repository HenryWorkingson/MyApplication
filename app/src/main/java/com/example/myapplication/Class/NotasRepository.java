package com.example.myapplication.Class;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.myapplication.NotasViewModel;

import java.util.List;

public class NotasRepository {
    private LiveData<List<Notas>> allNotasLive;
    //private LiveData<List<Notas>> findnotasWithPatten;
    private NotasDao notasDao;

    public NotasRepository(Context context) {
        NotasDatabase notasDatabase= NotasDatabase.getDatabase(context.getApplicationContext());
        notasDao= notasDatabase.getNotasDao();
        allNotasLive=notasDao.getAllNotas();
    }

    public LiveData<List<Notas>> getAllNotasLive() {
        return allNotasLive;
    }
    public void insertNotas(Notas...notas){
        new InsertAsyncTask(notasDao).execute(notas);
    }
    public void deleteAllNotas(Notas...notas){
        new DeleteAllAsyncTask(notasDao).execute();
    }
    public void deleteNotas(Notas...notas){
        new DeleteAsyncTask(notasDao).execute(notas);
    }
    public void updateNotas(Notas...notas){ new UpdateAsyncTask(notasDao).execute(notas);}

    static class InsertAsyncTask extends AsyncTask<Notas,Void,Void> {
        private NotasDao notasDao;
        public InsertAsyncTask(NotasDao notasDao){
            this.notasDao=notasDao;
        }

        @Override
        protected Void doInBackground(Notas... notas) {
            notasDao.insertNotas(notas);
            return null;
        }
    }
    public LiveData<List<Notas>> findnotasWithPatten(String patten){
        return notasDao.findNotasWithPatten("%"+patten+"%");
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private NotasDao notasDao;
        public DeleteAllAsyncTask(NotasDao notasDao){
            this.notasDao=notasDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notasDao.deleteAllNotas();
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<Notas,Void,Void>{
        private NotasDao notasDao;
        public DeleteAsyncTask(NotasDao notasDao){
            this.notasDao=notasDao;
        }

        @Override
        protected Void doInBackground(Notas... notas) {
            notasDao.deleteNotas(notas);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<Notas,Void,Void>{
        private NotasDao notasDao;
        public UpdateAsyncTask(NotasDao notasDao){
            this.notasDao=notasDao;
        }

        @Override
        protected Void doInBackground(Notas... notas) {
            notasDao.updateNotas(notas);
            return null;
        }
    }
}
