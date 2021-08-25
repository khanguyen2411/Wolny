package com.example.wolny.Fragment.LogInSignUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.wolny.Activity.LogInSignUpActivity;
import com.example.wolny.Activity.MainActivity;
import com.example.wolny.IMain;
import com.example.wolny.Presenter.SignUpPresenter;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements IMain.ISignUp {

    FirebaseAuth mAuth;
    View mView;
    EditText etEmail, etPassword, etUsername;
    TextView tvShowPassword;
    Button btnJoin;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        init();
        mAuth = FirebaseAuth.getInstance();
        SignUpPresenter presenter = new SignUpPresenter(this, getActivity());

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.signUp(mAuth, etUsername, etEmail, etPassword, getContext());

            }
        });

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
        return mView;
    }

    void init() {
        etEmail = mView.findViewById(R.id.etEmail);
        etPassword = mView.findViewById(R.id.etPassword);
        tvShowPassword = mView.findViewById(R.id.tvShowPassword);
        btnJoin = mView.findViewById(R.id.btnJoin);
        etUsername = mView.findViewById(R.id.etUsername);
    }

    @Override
    public void onSuccessful() {
        startActivity(new Intent(getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(), "Cannot sign up, please check the form and try again ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkPassword(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkEmail(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkUsername(String err) {
        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
    }

}