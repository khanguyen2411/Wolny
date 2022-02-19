package com.example.wolny.Presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.wolny.IMain;
import com.example.wolny.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpPresenter {
    IMain.ISignUp iSignUp;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;
    Context mContext;

    public SignUpPresenter(IMain.ISignUp iSignUp, Context mContext) {
        this.iSignUp = iSignUp;
        this.mContext = mContext;
    }

    public void signUp(FirebaseAuth auth, EditText etUsername, EditText etEmail, EditText etPassword, Context context) {
        String username = etUsername.getText().toString().trim();
        if (username.equals("")) {
            iSignUp.checkUsername("Please enter username");
        } else {
            if (username.length() < 6) {
                iSignUp.checkUsername("Username minimum contain 6 character");
            }
        }

        String email = etEmail.getText().toString().trim();
        if (email.equals("")) {
            iSignUp.checkEmail("Please enter email!!");
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                iSignUp.checkEmail("Please enter valid email address");
            }
        }

        String password = etPassword.getText().toString().trim();
        if (password.equals("")) {
            iSignUp.checkPassword("Please enter password");
        } else {
            if (password.length() < 6) {
                iSignUp.checkPassword("Password minimum contain 6 character");
            }
        }

        progressDialog = new ProgressDialog(context);

        if (email.length() >= 6 && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length() >= 6) {

            progressDialog.setTitle("Registering Account");
            progressDialog.setMessage("Please wait while we are create your account!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = auth.getCurrentUser();
                        assert currentUser != null;
                        String uid = currentUser.getUid();
                        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

                        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                        String countryCodeValue = tm.getNetworkCountryIso();

                        User user = new User(uid, username, "default", "I'm a perfect freelancer", "", countryCodeValue, "offline");

                        mDatabase.child("Users").child(uid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    iSignUp.onSuccessful();
                                }
                            }
                        });
                    } else {
                        progressDialog.dismiss();
                        iSignUp.onFailure();
                    }
                }
            });

        }
    }

}
