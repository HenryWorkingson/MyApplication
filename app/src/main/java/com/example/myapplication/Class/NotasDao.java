package com.example.myapplication.Class;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NotasDao  {
    @Insert
    Void insertNotas(Notas...notas);

    @Query("DELETE FROM notas_table")
    void deleteAllNotas();

    @Query("SELECT * FROM notas_table ORDER BY id")
    //androidx.paging.DataSource.Factory<Integer,Notas> getAllNotas();
    //List<Notas> getAllNotas();
    LiveData<List<Notas>>getAllNotas();

    @Update
    void updateNotas(Notas... notas);

    @Delete
    void deleteNotas(Notas... notas);

    @Query("SELECT * FROM notas_table WHERE notas_titulo LIKE :patten ORDER BY id DESC")
    LiveData<List<Notas>>findNotasWithPatten(String patten);
}
