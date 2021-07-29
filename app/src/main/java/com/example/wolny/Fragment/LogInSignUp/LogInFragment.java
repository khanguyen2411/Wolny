package com.example.wolny.Fragment.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wolny.Activity.ForgotPasswordActivity;
import com.example.wolny.Activity.MainActivity;
import com.example.wolny.IMain;
import com.example.wolny.Presenter.LogInPresenter;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment implements IMain.ILogIn{
    View mView;
    EditText etEmail, etPassword;
    TextView tvShowPassword, tvForgotPassword;
    Button btnLogIn;
    FirebaseAuth auth;
    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_log_in, container, false);

        auth = FirebaseAuth.getInstance();
        LogInPresenter presenter = new LogInPresenter(this);
        init();
        tvShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvShowPassword.getText().toString().equals("Show")){
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    tvShowPassword.setText(getResources().getString(R.string.log_in_sign_up_hide));
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    tvShowPassword.setText(getResources().getString(R.string.log_in_sign_up_show));
                }
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.login(auth, etEmail.getText().toString().trim(), etPassword.getText().toString().trim(), getContext());
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ForgotPasswordActivity.class));
            }
        });
        return mView;
    }

    void init(){
        etEmail = mView.findViewById(R.id.etEmail);
        etPassword = mView.findViewById(R.id.etPassword);
        tvShowPassword = mView.findViewById(R.id.tvShowPassword);
        btnLogIn = mView.findViewById(R.id.btnLogIn);
        tvForgotPassword = mView.findViewById(R.id.tvForgotPassword);
    }

    @Override
    public void onSuccessful() {
        startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onFailure(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }
}