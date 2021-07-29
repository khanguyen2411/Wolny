package com.example.wolny.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wolny.IMain;
import com.example.wolny.Presenter.ForgotPasswordPresenter;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements IMain.IForgotPassword {
    ImageView ivBack;
    Button btnResetPassword;
    EditText etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        ForgotPasswordPresenter presenter = new ForgotPasswordPresenter(this);

        Context context = this;

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.resetPassword(FirebaseAuth.getInstance(), etEmail.getText().toString().trim(), context);
            }
        });
    }

    void init(){
        ivBack = findViewById(R.id.ivBack);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        etEmail = findViewById(R.id.etEmail);
    }

    @Override
    public void onSuccessful(Fragment fragment) {

        etEmail.setVisibility(View.INVISIBLE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.successFragment, fragment)
                .commit();


        btnResetPassword.setText("Back");

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
    }

    @Override
    public void onFailure(String err) {

        Toast.makeText(getApplicationContext(), err, Toast.LENGTH_SHORT).show();
    }
}