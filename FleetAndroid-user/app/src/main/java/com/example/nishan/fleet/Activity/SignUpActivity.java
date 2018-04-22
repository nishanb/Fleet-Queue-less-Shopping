package com.example.nishan.fleet.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nishan.fleet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignUpActivity extends AppCompatActivity {
    private EditText mEmail, mName, mPassword;
    private Button mLoginRedirect;
    private FancyButton mSignup;
    private ProgressBar mSignupIndicator;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);


        Auth = FirebaseAuth.getInstance();

        if(Auth.getCurrentUser()!=null){
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mName = findViewById(R.id.et_full_name);
        mSignup = findViewById(R.id.btn_signup);
        mSignup = findViewById(R.id.btn_signup);
        mLoginRedirect = findViewById(R.id.btn_login_redirect);
        mSignupIndicator=findViewById(R.id.pb_signup_indicator);


        mLoginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
            }
        });


        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                mSignupIndicator.setVisibility(View.VISIBLE);

                Auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                mSignupIndicator.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
