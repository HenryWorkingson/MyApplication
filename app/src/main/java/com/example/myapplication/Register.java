package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class Register extends AppCompatActivity {
        private EditText email,password;
        private TextView LogIn;
        private Button register;
        private FirebaseAuth mAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            mAuth = FirebaseAuth.getInstance();
            email=findViewById(R.id.username_register);
            password=findViewById(R.id.password_register);
            LogIn=findViewById(R.id.Login_register);
            register=findViewById(R.id.Register_btn);
        }

        @Override
        protected void onStart() {
            super.onStart();

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailRegister = email.getText().toString().trim();
                    String pass = password.getText().toString().trim();
                    if(emailRegister.isEmpty() ||  pass.isEmpty()){
                        Toast.makeText(Register.this, "Ingresar los datos correctos", Toast.LENGTH_SHORT).show();
                    }else{
                        if (!checkEmail(emailRegister)){
                            Toast.makeText(Register.this,"Correo incorrecto",Toast.LENGTH_SHORT).show();
                        } else if(password.length()<6){
                            Toast.makeText(Register.this,"PassWord cant be less than 6 length",Toast.LENGTH_SHORT).show();
                        }else{
                            createAccount(emailRegister,pass);
                        }
                    }
                }
            });
            LogIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                    Intent i=new Intent(Register.this,MainActivity.class);
                    startActivity(i);
                }
            });
        }

        private void createAccount(String email, String password) {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "REGISTER SUCCESSFUL",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(Register.this,MainActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Register.this, "REGISTER FAIL CAMBIA EL CORREO",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END create_user_with_email]
        }

        private boolean checkEmail(String email){
            Pattern pattern = Pattern
                    .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(email);
            return mather.find();
        }
    }