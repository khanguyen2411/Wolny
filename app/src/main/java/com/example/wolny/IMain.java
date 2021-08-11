package com.example.wolny;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface IMain {
    interface ISignUp{
        void onSuccessful();
        void onFailure();
        void checkPassword(String err);
        void checkEmail(String err);
        void checkUsername(String err);
    }

    interface ILogIn{
        void onSuccessful();
        void onFailure(String err);
    }

    interface IForgotPassword{
        void onSuccessful(Fragment fragment);
        void onFailure(String err);
    }

    interface ISetQuote{
        void setQuote(String author, String content);
    }

    interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

}
