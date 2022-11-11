package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityAfterLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AfterLogin extends AppCompatActivity {

    private TextView email,name;
    private Button button;
    FirebaseAuth mAuth;
    private String google;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login);
        google=getIntent().getStringExtra("google");
        email=findViewById(R.id.email_perfil);
        name=findViewById(R.id.name_perfil);

        if(google.equals("0")){
            mAuth = FirebaseAuth.getInstance();
            email.setText( getIntent().getStringExtra("email"));
            name.setText("Firebase Account");
        }else{
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this,gso);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if(acct!=null){
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                name.setText(personName);
                email.setText(personEmail);
            }
        }

        button=findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //no es sign de google
                if (google.equals("0")){
                    mAuth.signOut();
                    finish();
                    startActivity(new Intent(AfterLogin.this,MainActivity.class));
                }else{
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            finish();
                            startActivity(new Intent(AfterLogin.this,MainActivity.class));
                        }
                    });
                }
            }
        });


    }

    /*
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_after_login);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

     */
}