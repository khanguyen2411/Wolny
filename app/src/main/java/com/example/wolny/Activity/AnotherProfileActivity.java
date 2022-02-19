package com.example.wolny.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wolny.Adapter.Profile.SkillAdapter;
import com.example.wolny.Model.Bid;
import com.example.wolny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class AnotherProfileActivity extends AppCompatActivity {

    ImageView ivBack, ivProfile;
    TextView tvUsername, tvSmallUsername, tvSummary;
    RecyclerView rcvSkill;
    DatabaseReference mDatabase;
    String summary, skill;
    List<String> listSkills;
    Button btHireFreelancer;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_profile);
        mapping();
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        Bid bid = (Bid) bundle.getSerializable("information");

        String uid = bid.getFreelancerID();

        SkillAdapter skillAdapter = new SkillAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvSkill.setLayoutManager(gridLayoutManager);
        rcvSkill.setNestedScrollingEnabled(true);
        rcvSkill.setAdapter(skillAdapter);

        mDatabase.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                summary = Objects.requireNonNull(snapshot.child("summary").getValue()).toString();
                skill = Objects.requireNonNull(snapshot.child("skills").getValue()).toString();
                convertToList();
                skillAdapter.setList(listSkills);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        String username = bid.getFreelancerName();
        String imgUrl = bid.getFreelancerAvatar();

        if(!imgUrl.equals("default")){
            Glide.with(this).load(imgUrl).into(ivProfile);
        }

        ivBack.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        tvUsername.setText(username);
        tvSmallUsername.setText("@" + username);

        String currency = bundle.getString("currency");
        String type = bundle.getString("type");

        String budget = (type.equals("Hourly")) ? (bid.getBudget() + currency + "/hour") : (bid.getBudget() + currency);

        String message = "Do you want to hire " + username + " for your job with " + budget + " and complete in " + bid.getTime();

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Confirm hiring")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    setFreelancerId(uid, bid.getJobId(), username);
                    Intent intent = new Intent(AnotherProfileActivity.this, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("freelancerID", uid);
                    bundle1.putString("imgUrl", imgUrl);
                    bundle1.putString("username", username);
                    intent.putExtra("bundle", bundle1);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        btHireFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
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
        btHireFreelancer = findViewById(R.id.btHireFreelancer);
    }

    void setFreelancerId(String freelancerId, String jobID, String username){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("freelancerID", freelancerId);
        hashMap.put("freelancerName", username);
        hashMap.put("status", "progress");
        databaseReference.child("Jobs").child(jobID).updateChildren(hashMap);
    }
}