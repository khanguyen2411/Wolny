package com.example.wolny.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.example.wolny.Activity.ForgotPasswordActivity;
import com.example.wolny.Fragment.ForgotPassword.SuccessFragment;
import com.example.wolny.IMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordPresenter {
    IMain.IForgotPassword iForgotPassword;
    ProgressDialog progressDialog;

    public ForgotPasswordPresenter(IMain.IForgotPassword iForgotPassword) {
        this.iForgotPassword = iForgotPassword;
    }

    public void resetPassword(FirebaseAuth auth, String email, Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Reset Password");
        progressDialog.setMessage("Please wait while we reset your password!");
        progressDialog.setCanceledOnTouchOutside(false);
        if(!TextUtils.isEmpty(email)){
            progressDialog.show();
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        iForgotPassword.onSuccessful(SuccessFragment.newInstance());
                    } else {
                        progressDialog.dismiss();
                        iForgotPassword.onFailure("Cannot reset password, please check the form and try again ");
                    }
                }
            });
        } else {
            progressDialog.dismiss();
            iForgotPassword.onFailure("Please enter the email to reset password");
        }
    }
}
