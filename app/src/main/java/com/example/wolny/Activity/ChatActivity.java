package com.example.wolny.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Adapter.Chat.ChatAdapter;
import com.example.wolny.Model.LastMessage;
import com.example.wolny.Model.Message;
import com.example.wolny.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    ImageView ivBack, ivAvatar, ivStatus, ivCamera, ivGallery, ivSend;
    TextView tvUsername;
    RecyclerView rvChat;
    EditText etMessage;
    String freelancerId, freelancerImgUrl, freelancerName;
    String currentUserId, currentUsername, currentUserUrl;
    List<Message> messageList = new ArrayList<>();
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mapping();

        currentUserId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        freelancerId = bundle.getString("freelancerID");
        freelancerImgUrl = bundle.getString("imgUrl");
        freelancerName = bundle.getString("username");

        mDatabase = FirebaseDatabase.getInstance("https://wolny-b8ffa-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();

        mDatabase.child("Users").child(freelancerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = Objects.requireNonNull(snapshot.child("status").getValue()).toString();
                if(status.equals("online")){
                    ivStatus.setImageResource(R.drawable.green_dot);
                } else {
                    ivStatus.setImageResource(R.drawable.red_dot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mDatabase.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUsername = Objects.requireNonNull(snapshot.child("username").getValue()).toString();
                currentUserUrl = Objects.requireNonNull(snapshot.child("profileImage").getValue()).toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tvUsername.setText(freelancerName);
        if (!freelancerImgUrl.equals("default")) {
            Glide.with(this).load(freelancerImgUrl).into(ivAvatar);
        }

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etMessage.getText().toString().isEmpty()) {
                    sendTextMessage(currentUserId, freelancerId, "text", etMessage.getText().toString());
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFromGallery();
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageFromCamera();
            }
        });

        ChatAdapter chatAdapter = new ChatAdapter(this, messageList, freelancerImgUrl, currentUserId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);

        rvChat.setLayoutManager(linearLayoutManager);
        rvChat.setHasFixedSize(false);
        rvChat.setAdapter(chatAdapter);
        getMessage(mDatabase, chatAdapter);
    }

    void sendTextMessage(String sender, String receiver, String type, String content) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("type", type);
        hashMap.put("content", content);
        mDatabase.child("Chats").push().setValue(hashMap);
        LastMessage lastMessage = new LastMessage(type, sender, receiver, content, freelancerName, freelancerImgUrl, freelancerId);
        mDatabase.child("LastMessage").child(currentUserId).child(freelancerId).setValue(lastMessage);

        LastMessage lastMessage1 = new LastMessage(type, sender, receiver, content, currentUsername, currentUserUrl, currentUserId);
        mDatabase.child("LastMessage").child(freelancerId).child(currentUserId).setValue(lastMessage1);

        etMessage.setText("");
    }

    void sendImageMessage(String sender, String receiver, String type, String content) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("type", type);
        hashMap.put("content", content);
        mDatabase.child("Chats").push().setValue(hashMap);

        LastMessage lastMessage = new LastMessage(type, sender, receiver, content, freelancerName, freelancerImgUrl, freelancerId);
        mDatabase.child("LastMessage").child(currentUserId).child(freelancerId).setValue(lastMessage);

        LastMessage lastMessage1 = new LastMessage(type, sender, receiver, content, currentUsername, currentUserUrl, currentUserId);
        mDatabase.child("LastMessage").child(freelancerId).child(currentUserId).setValue(lastMessage1);
    }

    void mapping() {
        ivBack = findViewById(R.id.ivBack);
        ivSend = findViewById(R.id.ivSend);
        ivAvatar = findViewById(R.id.ivAvatar);
        ivStatus = findViewById(R.id.ivStatus);
        ivCamera = findViewById(R.id.ivCamera);
        ivGallery = findViewById(R.id.ivGallery);
        tvUsername = findViewById(R.id.tvUsername);
        rvChat = findViewById(R.id.rvChat);
        etMessage = findViewById(R.id.etMessage);
    }

    void getMessage(DatabaseReference databaseReference, ChatAdapter chatAdapter) {
        databaseReference.child("Chats").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message message = snapshot.getValue(Message.class);
                assert message != null;
                if (message.getSender().equals(currentUserId) && message.getReceiver().equals(freelancerId) ||
                        message.getReceiver().equals(currentUserId) && message.getSender().equals(freelancerId)) {
                    messageList.add(message);
                }

                chatAdapter.setList(messageList);
                rvChat.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void imageFromGallery() {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .compress(128)
                .start();
    }

    private void imageFromCamera() {
        ImagePicker.Companion.with(this)
                .cameraOnly()
                .compress(128)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String fileName = UUID.randomUUID().toString().replace("-", "");

                StorageReference reference = FirebaseStorage.getInstance().getReference().child("/imageMessage").child(fileName + ".png");

                reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    sendImageMessage(currentUserId, freelancerId, "image", uri.toString());
                                }
                            });
                        } else {
                            Toast.makeText(ChatActivity.this, "Something got error!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
}