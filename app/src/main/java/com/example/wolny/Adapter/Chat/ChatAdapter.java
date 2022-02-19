package com.example.wolny.Adapter.Chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Model.Message;
import com.example.wolny.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public static final int MSG_SEND_TEXT = 0;
    public static final int MSG_RECEIVE_TEXT = 1;
    public static final int MSG_SEND_IMAGE = 2;
    public static final int MSG_RECEIVE_IMAGE = 3;

    Context mContext;
    List<Message> messages;
    String avatarUrl, uid;

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<Message> messages){
        this.messages = messages;
        notifyDataSetChanged();
    }

    public ChatAdapter(Context mContext, List<Message> messages, String avatarUrl, String uid) {
        this.mContext = mContext;
        this.messages = messages;
        this.avatarUrl = avatarUrl;
        this.uid = uid;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView;

        switch (viewType){
            case MSG_SEND_TEXT: {
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_send_message, parent, false);
                break;
            }
            case MSG_RECEIVE_TEXT: {
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text_receive_message, parent, false);
                break;
            }
            case MSG_SEND_IMAGE: {
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_send_message, parent, false);
                break;
            }
            default: {
                mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_receive_message, parent, false);
                break;
            }
        }

        return new ChatViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messages.get(position);

        if(holder.getItemViewType() == MSG_RECEIVE_IMAGE || holder.getItemViewType() == MSG_RECEIVE_TEXT){
            if(!avatarUrl.equals("default")){
                Glide.with(mContext).load(avatarUrl).into(holder.ivAvatar);
            }
        }

        String content = message.getContent();

        if(message.getType().equals("text")){
            holder.tvContent.setText(content);
        } else {
            Glide.with(mContext).load(content).into(holder.ivContent);
        }
    }

    @Override
    public int getItemCount() {
        return (messages == null ? 0 : messages.size());
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder{
        ImageView ivAvatar, ivContent;
        TextView tvContent;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            ivContent = itemView.findViewById(R.id.ivContent);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message message = messages.get(position);
        if(message.getSender().equals(uid) && message.getType().equals("text")){
            return MSG_SEND_TEXT;
        }

        if(message.getSender().equals(uid) && message.getType().equals("image")){
            return MSG_SEND_IMAGE;
        }

        if(message.getReceiver().equals(uid) && message.getType().equals("text")){
            return MSG_RECEIVE_TEXT;
        }

        return MSG_RECEIVE_IMAGE;
    }
}
