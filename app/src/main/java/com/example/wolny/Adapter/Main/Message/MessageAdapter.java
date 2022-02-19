package com.example.wolny.Adapter.Main.Message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Activity.ChatActivity;
import com.example.wolny.IMain;
import com.example.wolny.Model.LastMessage;
import com.example.wolny.Model.Message;
import com.example.wolny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    Context mContext;
    String uid;
    List<LastMessage> lastMessages;
    DatabaseReference databaseReference;

    public MessageAdapter(Context mContext, String uid, List<LastMessage> lastMessages, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.uid = uid;
        this.lastMessages = lastMessages;
        this.databaseReference = databaseReference;
    }

    public void setList(List<LastMessage> lastMessages){
        this.lastMessages = lastMessages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        LastMessage message = lastMessages.get(position);

        String otherID = message.getFreelancerID();

        databaseReference.child("Users").child(otherID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String status = Objects.requireNonNull(snapshot.child("status").getValue()).toString();
                if(status.equals("online")){
                    holder.ivStatus.setImageResource(R.drawable.green_dot);
                } else {
                    holder.ivStatus.setImageResource(R.drawable.red_dot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(message.getSender().equals(uid)){
            if(message.getType().equals("text")){
                if(message.getContent().length() > 30){
                    String content = message.getContent().substring(0, 15) + "...";
                    holder.tvLastMessage.setText("You: " + content);
                } else {
                    holder.tvLastMessage.setText("You: " + message.getContent());
                }
            } else {
                holder.tvLastMessage.setText("You has sent a photo");
            }
        } else {
            if(message.getType().equals("text")){
                if(message.getContent().length() > 30){
                    String content = message.getContent().substring(0, 15) + "...";
                    holder.tvLastMessage.setText(content);
                } else {
                    holder.tvLastMessage.setText(message.getContent());
                }
            } else {
                holder.tvLastMessage.setText(message.getUsername() + " has sent a photo");
            }
        }

        holder.tvUsername.setText(message.getUsername());

        String url = message.getAvatarUrl();
        if(!url.equals("default")){
            Glide.with(mContext).load(url).into(holder.ivAvatar);
        }

        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, ChatActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle1 = new Bundle();
                bundle1.putString("freelancerID", message.getFreelancerID());
                bundle1.putString("imgUrl", url);
                bundle1.putString("username", message.getUsername());
                intent.putExtra("bundle", bundle1);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (lastMessages == null ? 0 : lastMessages.size());
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivAvatar, ivStatus;
        TextView tvUsername, tvLastMessage;
        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvLastMessage = itemView.findViewById(R.id.tvLastMessage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getLayoutPosition(), false);
        }
    }
}
