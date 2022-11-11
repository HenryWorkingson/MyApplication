package com.example.myapplication.Class;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notas_table")
public class Notas {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo(name = "notas_titulo")
    private String titulo;

    @ColumnInfo(name = "notas_descrip")
    private String descripcion;

    public Notas(String titulo, String descripcion) {
        this.titulo=titulo;
        this.descripcion=descripcion;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
