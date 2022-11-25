package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.Class.NotasDao;
import com.example.myapplication.Class.NotasDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.inputmethod.InputMethodManager;
import android.widget.Toolbar;

import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

public class AfterLogin extends AppCompatActivity {

    NotasDatabase notasDatabase;
    NotasDao notasDao;
    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationViewLogin);
        NavHostFragment navHostFragment= (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.NavHostFragmentLogin);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav,navController);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(bottomNav.getMenu()).build();


        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration);
        notasDatabase= Room.databaseBuilder(this,NotasDatabase.class,"nota_database").allowMainThreadQueries().build();
        notasDao = notasDatabase.getNotasDao();


    }
    @Override
    public boolean onSupportNavigateUp(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow( findViewById(R.id.fragment_notas).getWindowToken(), 0);

        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

}