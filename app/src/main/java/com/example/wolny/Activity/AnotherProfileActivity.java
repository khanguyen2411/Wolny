package com.example.wolny.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wolny.Adapter.Profile.SkillAdapter;
import com.example.wolny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AnotherProfileActivity extends AppCompatActivity {

    ImageView ivBack, ivProfile;
    TextView tvUsername, tvSmallUsername, tvSummary;
    RecyclerView rcvSkill;
    DatabaseReference mDatabase;
    String summary, skill;
    List<String> listSkills;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_profile);
        mapping();
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Bundle bundle = getIntent().getBundleExtra("bundle");

        String uid = bundle.getString("freelancerID");

        SkillAdapter skillAdapter = new SkillAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvSkill.setLayoutManager(gridLayoutManager);
        rcvSkill.setNestedScrollingEnabled(true);
        rcvSkill.setAdapter(skillAdapter);

        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                summary = snapshot.child("summary").getValue().toString();
                skill = snapshot.child("skills").getValue().toString();
                convertToList();
                skillAdapter.setList(listSkills);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String username = bundle.getString("freelancerName");
        String imgUrl = bundle.getString("freelancerAvatar");

        if(!imgUrl.equals("default")){
            Glide.with(this).load(imgUrl).into(ivProfile);
        }

        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        tvUsername.setText(username);
        tvSmallUsername.setText("@" + username);
    }

    public void convertToList(){
        listSkills = new ArrayList<>(Arrays.asList(skill.split(", ")));
    }


    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        ivProfile = findViewById(R.id.ivProfile);
        tvUsername = findViewById(R.id.tvUsername);
        tvSmallUsername = findViewById(R.id.tvSmallUsername);
        tvSummary = findViewById(R.id.tvSummary);
        rcvSkill = findViewById(R.id.rcvSkill);
    }
}