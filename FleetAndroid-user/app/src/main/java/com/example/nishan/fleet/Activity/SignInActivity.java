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

public class SignInActivity extends AppCompatActivity {
    private EditText mEmail,mPassword;
    private Button mSignUpRedirect;
    private FancyButton mLogin;
    private ProgressBar mLoginIndicator;
    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_in);

        mEmail=findViewById(R.id.et_signin_email);
        mPassword=findViewById(R.id.et_signin_password);
        mLogin=findViewById(R.id.btn_login);
        mSignUpRedirect=findViewById(R.id.btn_signup_redirect);
        mLoginIndicator=findViewById(R.id.pb_signin_indicator);

        Auth=FirebaseAuth.getInstance();

        if(Auth.getCurrentUser()!=null){
            startActivity(new Intent(SignInActivity.this,MainActivity.class));
        }

        mSignUpRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLoginIndicator.setVisibility(View.VISIBLE);
                String email,password;

                email=mEmail.getText().toString();
                password=mPassword.getText().toString();


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

                Auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if(task.isSuccessful()){
                                    startActivity(new Intent(SignInActivity.this,MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(SignInActivity.this,"Invalid Login ",Toast.LENGTH_SHORT).show();
                                    mLoginIndicator.setVisibility(View.INVISIBLE);
                                }
                            }
                        });
            }
        });
    }
}
