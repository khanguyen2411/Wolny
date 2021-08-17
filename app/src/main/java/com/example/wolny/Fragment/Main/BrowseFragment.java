package com.example.wolny.Fragment.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Adapter.Main.Browse.CategoryAdapter;
import com.example.wolny.Adapter.Main.Browse.PopularJobAdapter;
import com.example.wolny.Model.Job;
import com.example.wolny.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BrowseFragment extends Fragment {

    View mView;
    RecyclerView rvPopularJob, rvCategory;
    DatabaseReference mDatabase;
    int size, start, end;
    String uid;
    List<Job> list;

    public BrowseFragment() {
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
        mView = inflater.inflate(R.layout.fragment_browse, container, false);
        mapping();
        setupContentRecyclerView();
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        setupPopularJobRecyclerView();

        return mView;
    }

    private void setupContentRecyclerView() {
        List<String> titles = new ArrayList<>();
        List<Integer> images = new ArrayList<>();

        titles.add("Website & IT");
        titles.add("Mobile");
        titles.add("Writing");
        titles.add("Art & Design");
        titles.add("Data Entry");
        titles.add("Software Development");
        titles.add("Sales");
        titles.add("Business");
        titles.add("Local Job");
        titles.add("Other");

        images.add(R.drawable.category_website);
        images.add(R.drawable.category_mobile);
        images.add(R.drawable.category_writing);
        images.add(R.drawable.category_design);
        images.add(R.drawable.category_data);
        images.add(R.drawable.category_software_development);
        images.add(R.drawable.category_sale);
        images.add(R.drawable.category_business);
        images.add(R.drawable.category_local);
        images.add(R.drawable.category_other);

        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvCategory.setLayoutManager(gridLayoutManager);
        rvCategory.setNestedScrollingEnabled(true);
        rvCategory.setAdapter(categoryAdapter);
    }

    private void setupPopularJobRecyclerView() {
        PopularJobAdapter adapter = new PopularJobAdapter(requireActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvPopularJob.setLayoutManager(linearLayoutManager);
        rvPopularJob.setNestedScrollingEnabled(true);
        rvPopularJob.setAdapter(adapter);


        Query query = mDatabase.child("Jobs").limitToFirst(3);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Job job = dataSnapshot.getValue(Job.class);
                    if (job != null && !job.getEmployerID().equals(uid)) {
                        list.add(job);
                    }
                    if(list.size() == 3){
                        break;
                    }
                }
                adapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void mapping() {
        rvPopularJob = mView.findViewById(R.id.rvPopularJob);
        rvCategory = mView.findViewById(R.id.rvCategory);
    }



}