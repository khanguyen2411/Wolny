package com.example.wolny.Fragment.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.wolny.Activity.OnBoardingActivity;
import com.example.wolny.Activity.ProfileActivity;
import com.example.wolny.IMain;
import com.example.wolny.Presenter.SetQuotePresenter;
import com.example.wolny.R;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;
import java.util.concurrent.Executors;

public class UserFragment extends Fragment implements IMain.ISetQuote {

    View mView;
    TextView tvQuote, tvAuthor, tvUsername, tvLogOut;
    LinearLayout llProfile;
    String username;
    ImageView ivProfile;
    ProgressBar progressBar;

    public UserFragment() {
        // Required empty public constructor
    }

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference database;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_user, container, false);

        mapping();

        database = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users").child(currentUser.getUid());

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = Objects.requireNonNull(snapshot.child("username").getValue()).toString();
                tvUsername.setText(username);

                String imageUrl = Objects.requireNonNull(snapshot.child("profileImage").getValue()).toString();

                if (!imageUrl.equals("default")) {
                    Glide.with(getActivity()).load(imageUrl).into(ivProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                ivProfile.setVisibility(View.VISIBLE);
                tvUsername.setVisibility(View.VISIBLE);
            }
        }, 1000);

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireActivity(), OnBoardingActivity.class));
                requireActivity().finish();
            }
        });

        llProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), ProfileActivity.class);
                String uid = currentUser.getUid();
                intent.putExtra("uid", uid);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        SetQuotePresenter presenter = new SetQuotePresenter(this);

        presenter.getQuote(getContext());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setQuote(String author, String content) {
        tvQuote.setText(content);
        tvAuthor.setText("-" + author);
    }

    void mapping() {
        tvQuote = mView.findViewById(R.id.tvQuotation);
        tvAuthor = mView.findViewById(R.id.tvAuthor);
        tvUsername = mView.findViewById(R.id.tvUsername);
        tvLogOut = mView.findViewById(R.id.tvLogOut);
        llProfile = mView.findViewById(R.id.llProfile);
        ivProfile = mView.findViewById(R.id.ivProfile);
        progressBar = mView.findViewById(R.id.progress_circular);
    }


}