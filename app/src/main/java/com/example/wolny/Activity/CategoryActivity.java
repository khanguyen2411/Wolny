package com.example.wolny.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Adapter.Main.Browse.JobAdapter;
import com.example.wolny.Model.Job;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {

    TextView tvCategory;
    ImageView ivBack;
    RecyclerView rvListJob;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    String uid;
    List<Job> list = new ArrayList<>();
    LinearLayout llQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mapping();
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String category = getIntent().getExtras().getString("category");
        databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        JobAdapter jobAdapter = new JobAdapter(this, databaseReference, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvListJob.getContext(), linearLayoutManager.getOrientation());
        rvListJob.addItemDecoration(dividerItemDecoration);
        rvListJob.setLayoutManager(linearLayoutManager);
        rvListJob.setNestedScrollingEnabled(true);
        rvListJob.setAdapter(jobAdapter);

        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Job job = dataSnapshot.getValue(Job.class);
                    assert job != null;
                    if (!job.getEmployerID().equals(uid) && job.getCategory().equals(category) && job.getStatus().equals("open")) {
                        list.add(job);
                    }
                }
                jobAdapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvCategory.setText(category);

        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        Handler handler = new Handler(Looper.myLooper());
        handler.postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            if(list.isEmpty()){
                rvListJob.setVisibility(View.INVISIBLE);
                llQuote.setVisibility(View.VISIBLE);
            } else {
                rvListJob.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        tvCategory = findViewById(R.id.tvCategory);
        rvListJob = findViewById(R.id.rvListJob);
        progressBar = findViewById(R.id.progressBar);
        llQuote = findViewById(R.id.llQuote);
    }
}