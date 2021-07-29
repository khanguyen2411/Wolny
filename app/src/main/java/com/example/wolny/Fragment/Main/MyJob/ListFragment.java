package com.example.wolny.Fragment.Main.MyJob;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wolny.R;

public class ListFragment extends Fragment {
    TextView textView;
    View mView;

    public ListFragment() {

    }

    public static ListFragment newInstance(String type) {

        Bundle args = new Bundle();

        args.putString("type", type);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_list, container, false);

        textView = mView.findViewById(R.id.textView);

        String string = requireArguments().getString("as") + "" + requireArguments().getString("type");
        textView.setText(string);
        return mView;
    }
}