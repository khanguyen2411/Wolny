package com.example.wolny.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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
    DatabaseReference databaseReference;
    String uid;
    List<Job> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        mapping();
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        String category = getIntent().getExtras().getString("category");

        databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        databaseReference.child("Jobs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Job job = dataSnapshot.getValue(Job.class);
                    assert job != null;
                    if(!job.getEmployerID().equals(uid) && job.getCategory().equals(category)){
                        list.add(job);
                    }
                }
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

        JobAdapter jobAdapter = new JobAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvListJob.setLayoutManager(linearLayoutManager);
        jobAdapter.setList(list);
        rvListJob.setAdapter(jobAdapter);
    }

    void mapping(){
        ivBack = findViewById(R.id.ivBack);
        tvCategory = findViewById(R.id.tvCategory);
        rvListJob = findViewById(R.id.rvListJob);
    }
}