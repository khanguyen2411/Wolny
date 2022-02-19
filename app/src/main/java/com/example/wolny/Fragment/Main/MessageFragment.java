package com.example.wolny.Fragment.Main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wolny.Adapter.Main.Message.MessageAdapter;
import com.example.wolny.Model.LastMessage;
import com.example.wolny.Model.Message;
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


public class MessageFragment extends Fragment {

    View mView;
    RecyclerView rvMessages;
    ImageView ivNoMessage;
    DatabaseReference databaseReference;
    String uid;
    List<LastMessage> lastMessageList;

    public MessageFragment() {
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
        mView = inflater.inflate(R.layout.fragment_message, container, false);
        rvMessages = mView.findViewById(R.id.rvMessage);
        ivNoMessage = mView.findViewById(R.id.ivNoMessage);

        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        databaseReference = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        lastMessageList = new ArrayList<>();

        MessageAdapter messageAdapter = new MessageAdapter(getActivity(), uid, lastMessageList, databaseReference);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvMessages.setLayoutManager(linearLayoutManager);
        rvMessages.setAdapter(messageAdapter);

        databaseReference.child("LastMessage").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lastMessageList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    LastMessage lastMessage = dataSnapshot.getValue(LastMessage.class);
                    if(!lastMessageList.contains(lastMessage)){
                        lastMessageList.add(0, lastMessage);
                    }
                }

                if(lastMessageList.isEmpty()){
                    ivNoMessage.setVisibility(View.VISIBLE);
                } else {
                    ivNoMessage.setVisibility(View.INVISIBLE);
                }

                messageAdapter.setList(lastMessageList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return mView;
    }

}