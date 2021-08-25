package com.example.wolny.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Adapter.AddSkill.CurrentSkillAdapter;
import com.example.wolny.Adapter.AddSkill.SkillAdapter;

import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillActivity extends AppCompatActivity{

    ImageView ivBack;
    SearchView searchView;
    RecyclerView recyclerView, rcvCurrentSkill;
    public SkillAdapter skillAdapter;
    CurrentSkillAdapter currentSkillAdapter;
    Button btCancel, btApply;
    List<String> currentSkills;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        mapping();

        String s = getIntent().getExtras().getString("skills").trim();
        if(s.isEmpty()){
            currentSkills = new ArrayList<>();
        } else {
            currentSkills = new ArrayList<>(Arrays.asList(s.split(", ")));
        }

        setupAdapter();
        setupSearchView();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        btApply.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                currentSkills = currentSkillAdapter.getList();
                String newSkill = String.join(", ", currentSkills);
                DatabaseReference mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
                String uid = getIntent().getExtras().getString("uid");

                mDatabase.child("Users").child(uid).child("skills").setValue(newSkill.trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getBaseContext(), "Successfully!!", Toast.LENGTH_SHORT).show();
                            onBack();
                        } else {
                            Toast.makeText(getBaseContext(), "Error, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void onBack(){
        onBackPressed();
        finish();
    }
    private List<String> initList() {
        List<String> list = new ArrayList<>();

        list.add("Node.js");
        list.add("React.js");
        list.add("Vue.js");
        list.add("Android");
        list.add("2D Animation");
        list.add("Flutter");
        list.add("React Native");

        return list;
    }

    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        searchView = findViewById(R.id.search_bar);
        recyclerView = findViewById(R.id.rcvSkill);
        rcvCurrentSkill = findViewById(R.id.rcvCurrentSkill);
        btCancel = findViewById(R.id.btCancel);
        btApply = findViewById(R.id.btApply);
    }

    public void filter(String text, List<String> skills) {
        // creating a new array list to filter our data.
        List<String> filtered = new ArrayList<>();

        // running a for loop to compare elements.
        for (String item : skills) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filtered.add(item);
            }
        }

        skillAdapter.setList(filtered);
    }


    public void setupAdapter(){
        //current skill adapter
        currentSkillAdapter = new CurrentSkillAdapter(this);
        currentSkillAdapter.setList(currentSkills);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvCurrentSkill.setHasFixedSize(false);
        rcvCurrentSkill.setLayoutManager(linearLayoutManager1);
        rcvCurrentSkill.setAdapter(currentSkillAdapter);

        //list skill adapter
        skillAdapter = new SkillAdapter(this, currentSkillAdapter, rcvCurrentSkill);
        skillAdapter.setList(initList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(skillAdapter);



    }

    void setupSearchView(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, initList());
                return false;
            }
        });

    }

}