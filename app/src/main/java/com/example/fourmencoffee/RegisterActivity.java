package com.example.fourmencoffee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private TextView login_now;
    private Button create_account;
    private FirebaseAuth mAuth;
    private EditText etRegEmail, etRegPassword, etRegName;
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUi();
        initListener();
    }
    private void initUi() {
        create_account= findViewById(R.id.button_create_account);
        login_now =findViewById(R.id.login_now);
        etRegName = findViewById(R.id.tv_register_name);
        etRegEmail = findViewById(R.id.edit_register_email);
        etRegPassword = findViewById(R.id.edit_register_password);
    }
    private void initListener(){
        login_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  createUser();
//                Intent intent =new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        create_account.setOnClickListener(view ->{
            createUser();

            String fullname_db = etRegName.getText().toString().trim();
            String email_db = etRegEmail.getText().toString().trim();
            String password_db = etRegPassword.getText().toString().trim();

            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("Users");
            UserHelpClass helpClass = new UserHelpClass(fullname_db, email_db, password_db);
            reference.child(fullname_db).setValue(helpClass);
        });
    }


    private void createUser()
    {
        mAuth = FirebaseAuth.getInstance();
        String fullname = etRegName.getText().toString().trim();
        String email = etRegEmail.getText().toString().trim();
        String password = etRegPassword.getText().toString().trim();
        if (TextUtils.isEmpty(fullname)){
            etRegName.setError("Tên không được để trống!");
            etRegName.requestFocus();
        }else
        {
            if (TextUtils.isEmpty(email)) {
                etRegEmail.setError("Email không được để trống!");
                etRegEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                etRegPassword.setError("Password không được để trống!");
                etRegPassword.requestFocus();
            } else {
                {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information Signed up successfully/Authentication failed.
                                        Toast.makeText(RegisterActivity.this, "Đăng nhập thành công.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                                        finishAffinity();
                                    } else {

                                        Toast.makeText(RegisterActivity.this, "Đăng nhập thất bại.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        }
    }




}