package com.example.wolny.Fragment.Main.MyJob;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Adapter.Main.MyJobs.MyJobAdapter;
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

public class ListFragment extends Fragment {

    View mView;
    String as, status, uid;
    RecyclerView rvListJob;
    DatabaseReference mDatabase;
    List<Job> list;

    public ListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_list, container, false);

        rvListJob = mView.findViewById(R.id.rvListJob);
        getArgs();

        MyJobAdapter adapter = new MyJobAdapter(getContext());
        adapter.setList(list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListJob.setLayoutManager(linearLayoutManager);
        rvListJob.setAdapter(adapter);

        mDatabase.child("Jobs").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot jobSnapShot : snapshot.getChildren()) {
                    // TODO: handle the post
                    Job job = jobSnapShot.getValue(Job.class);
                    list.add(job);
                }

                adapter.setList(processList());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mView;
    }

    void getArgs() {
        as = requireArguments().getString("as").toLowerCase();
        status = requireArguments().getString("status").toLowerCase();
        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    List<Job> processList() {
        List<Job> processedList = new ArrayList<>();

        if (as.equals("employer")) {
            for (Job item : list) {
                if (item.getEmployerID().equals(uid)) {
                    processedList.add(item);
                }
            }

            if(!status.equals("all")){
                processedList.removeIf(job -> !job.getStatus().equals(status));
            }
        } else {
            for (Job item : list) {
                if (item.getFreelancerID().equals(uid)) {
                    processedList.add(item);
                }
            }

            if(!status.equals("all")){
                processedList.removeIf(job -> !job.getStatus().equals(status));
            }
        }

        return processedList;
    }
}