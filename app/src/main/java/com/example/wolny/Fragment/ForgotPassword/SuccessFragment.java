package com.example.wolny.Fragment.ForgotPassword;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.wolny.R;


public class SuccessFragment extends Fragment {
    public SuccessFragment() {
        // Required empty public constructor
    }

    public static SuccessFragment newInstance() {
        Bundle args = new Bundle();

        SuccessFragment fragment = new SuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_success, container, false);

        return mView;
    }
}