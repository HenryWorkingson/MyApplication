package com.example.myapplication;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;


public class login extends Fragment {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    private FirebaseAuth mAuth;
    private Button button;
    private EditText username,password;
    private TextView register;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        googleBtn = getView().findViewById(R.id.google_btn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this.getContext(),gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this.getContext());
        if(acct!=null){
            navigateToSecondActivity();
        }
        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        //Email Login
        button=getView().findViewById(R.id.Register_btn);
        username=getView().findViewById(R.id.username_register);
        password=getView().findViewById(R.id.password_register);
        register=getView().findViewById(R.id.Login_register);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            getActivity().finish();
            Intent i = new Intent(getContext(),AfterLogin.class);
            i.putExtra("email",currentUser.getEmail());
            i.putExtra("google","0");
            startActivity(i);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                if(email.isEmpty()|| pass.isEmpty()){
                    Toast.makeText(getContext(),"Ingresar los datos",Toast.LENGTH_SHORT).show();
                }else{
                    loginUser(email,pass);
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                Intent i=new Intent(getContext(),Register.class);
                startActivity(i);
            }
        });
    }

    private void loginUser(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    getActivity().finish();
                    Intent i = new Intent(getContext(),AfterLogin.class);
                    i.putExtra("email",email);
                    i.putExtra("google","0");
                    startActivity(new Intent(i));
                }else{
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"Error al inciar",Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println(mAuth.getCurrentUser());


    }


    void signIn(){

        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    void navigateToSecondActivity(){
        getActivity().finish();
        Intent intent = new Intent(getContext(),AfterLogin.class);
        intent.putExtra("google","1");
        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                getActivity().finish();
                Intent intent = new Intent(getActivity(),AfterLogin.class);
                intent.putExtra("google","1");
                startActivity(intent);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }
}

