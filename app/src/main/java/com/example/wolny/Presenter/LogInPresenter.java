package com.example.wolny.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;

import com.example.wolny.IMain;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInPresenter {
    IMain.ILogIn iLogIn;
    private ProgressDialog progressDialog;
    public LogInPresenter(IMain.ILogIn iLogIn) {
        this.iLogIn = iLogIn;
    }

    public void login(FirebaseAuth auth, String email, String password, Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Logging in");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        iLogIn.onSuccessful();

                    } else {
                        progressDialog.dismiss();
                        iLogIn.onFailure("Cannot sign in, please check the form and try again ");
                    }
                }
            });
        }else {
            progressDialog.dismiss();
            iLogIn.onFailure("Please enter the email & password");
        }
    }


}
