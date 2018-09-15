package com.kumarsunil17.mychat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameText,emailText,passText,confPassText;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.signup_name);
        emailText = findViewById(R.id.signup_email);
        passText = findViewById(R.id.signup_password);
        confPassText = findViewById(R.id.signup_conf_password);

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void signup(View view) {
        final String email = emailText.getText().toString();
        final String name = nameText.getText().toString();
        String password = passText.getText().toString();
        String confPassword = confPassText.getText().toString();
        final ProgressDialog pg = new ProgressDialog(this);
        pg.setMessage("Please wait while we are login you in");
        pg.setTitle("Please wait");
        pg.setCancelable(false);
        pg.setCanceledOnTouchOutside(false);

        if (TextUtils.isEmpty(email)){
            emailText.setError("");
        } else if (TextUtils.isEmpty(name)){
            nameText.setError("");
        } else if (TextUtils.isEmpty(password)){
            passText.setError("");
        } else if (TextUtils.isEmpty(confPassword)){
            confPassText.setError("");
        }else if (!(password.equals(confPassword))){
            confPassText.setError("Password must be same");
            passText.setError("");
        }else{
            pg.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pg.dismiss();
                    if (task.isSuccessful()){
                        String uid = task.getResult().getUser().getUid();
                        Map<String,Object> m = new HashMap<>();
                        m.put("Name",name);
                        m.put("Email",email);
                        m.put("dp","nothing");
                        userRef.child(uid).updateChildren(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void login(View view) {
        startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
    }
}
