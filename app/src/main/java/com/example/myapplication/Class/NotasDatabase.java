package com.example.myapplication.Class;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

//singleton
@Database(entities = {Notas.class},version = 5,exportSchema = false)
public abstract class NotasDatabase extends RoomDatabase {
    private static NotasDatabase instance;
    public static synchronized NotasDatabase getDatabase(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context,NotasDatabase.class,"Nota_database")
                    .fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_3_4)
                    .build();
        }
        return instance;
    }
    public abstract NotasDao getNotasDao();
    static final Migration MIGRATION_2_3= new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE notas_table ADD COLUMN bar_data NOT NULL DEFAULT 1 ");
        }
    };
    static final Migration MIGRATION_3_4= new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE nota_table (id INTEGER PRIMARY KEY NOT NULL, titulo TEXT," +
                    "descripcion TEXT)");
            database.execSQL("INSERT INTO nota_table (id,titulo,descripcion)" +
                    "SELECT id,titulo,descripcion FROM notas");
            database.execSQL("DROP TABLE notas");
            database.execSQL("ALTER TABLE nota_table RENAME to notas");
        }
    };
}
