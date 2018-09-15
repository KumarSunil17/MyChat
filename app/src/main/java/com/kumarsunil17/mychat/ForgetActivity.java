package com.kumarsunil17.mychat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
    private EditText emailText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        emailText = findViewById(R.id.forgot_email);
        mAuth = FirebaseAuth.getInstance();
    }

    public void forgotPassword(View view) {
        final ProgressDialog p = new ProgressDialog(this);
        p.setTitle("Please wait");
        p.setMessage("Please wait while we are verifying");
        p.setCanceledOnTouchOutside(false);
        p.setCancelable(false);
        String email = emailText.getText().toString();
        if (TextUtils.isEmpty(email)){
            emailText.setError("Email Address can't be blank");
        }else{
            p.show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        p.dismiss();
                        Toast.makeText(ForgetActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(ForgetActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
