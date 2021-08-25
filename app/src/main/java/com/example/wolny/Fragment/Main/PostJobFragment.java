package com.example.wolny.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wolny.Activity.NewJobActivity;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.internal.InternalTokenProvider;

import java.util.ArrayList;
import java.util.Objects;

public class PostJobFragment extends Fragment{

    View mView;
    CardView cvWebsite, cvMobile, cvWriting, cvArtDesign, cvDataEntry, cvSoftwareDevelopment, cvSales, cvBusiness, cvLocalJob, cvOther;
    public PostJobFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView =  inflater.inflate(R.layout.fragment_post_job, container, false);

        mapping();

        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Intent intent = new Intent(getActivity(), NewJobActivity.class);
        intent.putExtra("uid", uid);

        cardViewClick(intent);

        return mView;
    }

    private void cardViewClick(Intent intent) {
        cvWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Website & IT");
                startActivity(intent);
            }
        });

        cvMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Mobile");
                startActivity(intent);
            }
        });

        cvWriting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Writing");
                startActivity(intent);
            }
        });

        cvArtDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Art & Design");
                startActivity(intent);
            }
        });

        cvDataEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Data Entry");
                startActivity(intent);
            }
        });


        cvSoftwareDevelopment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Software Development");
                startActivity(intent);
            }
        });


        cvSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Sales");
                startActivity(intent);
            }
        });


        cvBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Business");
                startActivity(intent);
            }
        });

        cvLocalJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Local Job");
                startActivity(intent);
            }
        });

        cvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("category", "Others");
                startActivity(intent);
            }
        });
    }

    void mapping(){
        cvWebsite = mView.findViewById(R.id.cvWebsite);
        cvMobile = mView.findViewById(R.id.cvMobile);
        cvWriting = mView.findViewById(R.id.cvWriting);
        cvArtDesign = mView.findViewById(R.id.cvArtDesign);
        cvDataEntry = mView.findViewById(R.id.cvData);
        cvSoftwareDevelopment = mView.findViewById(R.id.cvSoftware);
        cvSales = mView.findViewById(R.id.cvSales);
        cvBusiness = mView.findViewById(R.id.cvBusiness);
        cvLocalJob = mView.findViewById(R.id.cvLocalJob);
        cvOther = mView.findViewById(R.id.cvOther);
    }


}