package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class Perfil extends Fragment {
    private TextView email,name;
    private Button button;
    FirebaseAuth mAuth;
    private String google;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        google=getActivity().getIntent().getStringExtra("google");
        email=getView().findViewById(R.id.email_perfil);
        name=getView().findViewById(R.id.name_perfil);

        if(google.equals("0")){
            mAuth = FirebaseAuth.getInstance();
            email.setText( getActivity().getIntent().getStringExtra("email"));
            name.setText("Firebase Account");
        }else{
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(getActivity(),gso);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());
            if(acct!=null){
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                name.setText(personName);
                email.setText(personEmail);
            }
        }

        button=getView().findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //no es sign de google
                if (google.equals("0")){
                    mAuth.signOut();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),MainActivity.class));
                }else{
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            getActivity().finish();
                            startActivity(new Intent(getActivity(),MainActivity.class));
                        }
                    });
                }
            }
        });

    }
}