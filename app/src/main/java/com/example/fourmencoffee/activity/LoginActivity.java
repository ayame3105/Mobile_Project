package com.example.fourmencoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity
{

    ImageView fb, gg;
    TextView em, hint_em, pw, hint_pw, notamember, orcontinue, hello, wel;
    TextView reset_pw,register_now;
    Button login_button;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_button = findViewById(R.id.login_button);
        hello =  findViewById(R.id.FirstPart1);
        wel = findViewById(R.id.FirstPart2);
        fb =  findViewById(R.id.fb_icon);
        gg =  findViewById(R.id.gg_icon);
        em =  findViewById(R.id.email);
        hint_em =  findViewById(R.id.hint_email);
        pw =  findViewById(R.id.password);
        hint_pw =  findViewById(R.id.hint_password);
        notamember =  findViewById(R.id.not_a_member);
        orcontinue =  findViewById(R.id.continuewith);

        register_now =  findViewById(R.id.registernow);
        reset_pw =  findViewById(R.id.reset_password);

        /*Animation*/
        hello.setTranslationX(300);
        wel.setTranslationX(300);
        gg.setTranslationX(300);
        fb.setTranslationX(300);
        login_button.setTranslationX(300);
        em.setTranslationX(300);
        hint_em.setTranslationX(300);
        pw.setTranslationX(300);
        hint_pw.setTranslationX(300);
        orcontinue.setTranslationX(300);
        notamember.setTranslationX(300);

        register_now.setTranslationX(300);
        reset_pw.setTranslationX(300);

        fb.animate().translationX(0).setDuration(700).setStartDelay(0);
        gg.animate().translationX(0).setDuration(700).setStartDelay(0);

        login_button.animate().translationX(0).setDuration(700).setStartDelay(0);
        hello.animate().translationX(0).setDuration(700).setStartDelay(0);
        wel.animate().translationX(0).setDuration(700).setStartDelay(0);
        em.animate().translationX(0).setDuration(700).setStartDelay(0);
        hint_em.animate().translationX(0).setDuration(700).setStartDelay(0);
        pw.animate().translationX(0).setDuration(700).setStartDelay(0);
        hint_pw.animate().translationX(0).setDuration(700).setStartDelay(0);
        orcontinue.animate().translationX(0).setDuration(700).setStartDelay(0);
        notamember.animate().translationX(0).setDuration(700).setStartDelay(0);
        register_now.animate().translationX(0).setDuration(700).setStartDelay(0);
        reset_pw.animate().translationX(0).setDuration(700).setStartDelay(0);
        /*Xử lý đăng nhập*/




        /*Xử lý sự kiện*/

        login_button.setOnClickListener(view ->{
            loginUser();
         });
        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });

        reset_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, Forgot_Password.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
    }

    private void loginUser(){
        String email = hint_em.getText().toString().trim();
        String password = hint_pw.getText().toString().trim();
        mAuth =FirebaseAuth.getInstance();
        if (TextUtils.isEmpty(email)){
            hint_em.setError("Email can't be empty!");
            hint_em.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            hint_pw.setError("Password can't be empty");
            hint_pw.requestFocus();
        }
        else
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                String emailFromDB = email;

                                Toast.makeText(LoginActivity.this, "Logged in successfully",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("email",emailFromDB);
                                startActivity(intent);
                                finishAffinity();
                                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

}