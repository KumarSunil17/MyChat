package com.kumarsunil17.mychat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.ms_square.etsyblur.BlurConfig;
import com.ms_square.etsyblur.BlurDialogFragment;

public class LoginActivity extends AppCompatActivity{
    private EditText email,password;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        mAuth = FirebaseAuth.getInstance();
    }

    public void doLogin(View view) {
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Please wait while we are login you in");
        p.setTitle("Please wait");
        p.setCancelable(false);
        p.setCanceledOnTouchOutside(false);

        String emailText = email.getText().toString();
        String passText = password.getText().toString();
        if(TextUtils.isEmpty(emailText)){
            email.setError("Field cannot be empty");
        }else if(TextUtils.isEmpty(passText)){
            password.setError("Field cannot be empty");
        }else{
            p.show();
            mAuth.signInWithEmailAndPassword(emailText,passText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    p.dismiss();
                    if (task.isSuccessful()){
                        Toast t = new Toast(getApplicationContext());
                                t = Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT);
                                t.setGravity(Gravity.CENTER,0,0);
                                t.show();
                        startActivity(new Intent(LoginActivity.this,AllChatActivity.class));
                        finish();
                    }else{
                        Toast t = new Toast(getApplicationContext());
                                t = Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT);
                                t.setGravity(Gravity.CENTER,0,0);
                                t.show();
                    }
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) findViewById(R.id.custom_toast_root));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.FILL,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        //toast.show();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() ){
            if (mAuth.getCurrentUser() != null) {
                startActivity(new Intent(LoginActivity.this, AllChatActivity.class));
                finish();
            }
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Network connection failed!")
                    .setCancelable(false)
                    .setTitle("Oops!")
                    .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.show();
        }
    }


    public void doResetPassword(View view) {
        startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
    }

    public void doSignup(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}
