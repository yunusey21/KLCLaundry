package com.example.klclaundry.LOGSCREEN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.klclaundry.MainPages.MainActivity;
import com.example.klclaundry.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginScreen extends AppCompatActivity {
    private Button logButton,signUp;
    private ProgressBar progressBar;
    private TextView upPageText, downPageText;
    private EditText userNameText, passwordText;
    private FirebaseAuth mAuth;
    private SharedPreferences sp;

    String Email,Passwd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        definitions();
        initialize();
        events();

    }

    protected void RegTheMemo() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email",Email);
        editor.putString("password",Passwd);
        editor.commit();
    }

    protected void  Login() {
        mAuth.signInWithEmailAndPassword(Email, Passwd) //dokumantasyon
                .addOnSuccessListener(this ,new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        RegTheMemo();
                        RegTheId();
                        startActivity(new Intent(loginScreen.this, MainActivity.class));
                    }
                }) .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(loginScreen.this, e.getMessage().toString(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }
    protected void events() {
        downPageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"saklı dedik ya !",Toast.LENGTH_SHORT).show();
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = userNameText.getText().toString();
                Passwd = passwordText.getText().toString();

                if (Email.isEmpty() || Passwd.isEmpty() ) {
                    Toast.makeText(loginScreen.this,"boş bırakılamaz", Toast.LENGTH_SHORT).show();
                }

                else {
                    progressBar.setVisibility(View.VISIBLE);
                    Login();
                }

            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginScreen.this, SignUpPage.class));
            }
        });
    }

    protected  void initialize() {
        progressBar.setVisibility(View.INVISIBLE);
        logButton.setText("LOG IN");
        signUp.setText("SIGN UP");
        upPageText.setText("KLCLAUNDRY");
        downPageText.setText("tüm hakları saklıdır");
        downPageText.setClickable(true);
        userNameText.setText(sp.getString("email",""));
        passwordText.setText(sp.getString("password",""));
    }

    protected void definitions() {
        progressBar = findViewById(R.id.progressBar);
        logButton = findViewById(R.id.logScreenLoginButton);
        upPageText = findViewById(R.id.logScreenText);
        downPageText = findViewById(R.id.logScreenUnderText);
        userNameText = findViewById(R.id.logScreenUsername);
        passwordText = findViewById(R.id.logScreenPassword);
        signUp = findViewById(R.id.logScreenSignUp);
        sp = getSharedPreferences("user",MODE_PRIVATE);

        mAuth = FirebaseAuth.getInstance();
    }

    protected void RegTheId() {

        char[] chars = Email.toCharArray();
        String userName="";
        for(int i=0;i<Email.length();i++) {
            if(chars[i] == '@' && (i+1)<Email.length()) {
                userName = Email.split("@")[0];
            }
        }
        //giriş yapan son kişinin kullanıcı adı
        SharedPreferences sp2 = getSharedPreferences("USERNAME",MODE_PRIVATE); //USERNAME -> id
        SharedPreferences.Editor edt = sp2.edit();
        edt.putString("id",userName);
        edt.apply();

    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }

}