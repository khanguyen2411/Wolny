package com.example.wolny.Adapter.JobDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wolny.Activity.AnotherProfileActivity;
import com.example.wolny.IMain;
import com.example.wolny.Model.Bid;
import com.example.wolny.R;

import java.util.List;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.BidViewHolder> {

    Context mContext;
    List<Bid> list;
    String currency, type;
    String uid;

    public BidAdapter(Context mContext, String currency, String type, String uid) {
        this.mContext = mContext;
        this.currency = currency;
        this.type = type;
        this.uid = uid;

    }

    public void setList(List<Bid> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bid, parent, false);
        return new BidViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull BidViewHolder holder, int position) {
        Bid bid = list.get(position);

        String imgUrl = bid.getFreelancerAvatar();
        if (imgUrl != null && !imgUrl.equals("default")) {
            Glide.with(mContext).load(imgUrl).placeholder(R.drawable.user_icon).into(holder.ivAvatar);
        }

        String username = bid.getFreelancerName();
        if (username != null) {
            holder.tvUsername.setText(bid.getFreelancerName());
        }
        String budget = bid.getBudget() + currency;
        holder.tvBudget.setText(budget);

        holder.tvDescription.setText(bid.getDescription());

        String time = "Complete on " + bid.getTime();
        holder.tvTime.setText(time);

        holder.ratingBar.setRating(5f);

        if(!bid.getFreelancerID().equals(uid)){
            holder.setItemClickListener((view, position1, isLongClick) -> {
                Intent intent = new Intent(mContext, AnotherProfileActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("freelancerID", bid.getFreelancerID());
                bundle.putString("freelancerAvatar", imgUrl);
                bundle.putString("freelancerName", username);
                intent.putExtra("bundle", bundle);
                mContext.startActivity(intent);
            });
        } else {
            holder.setItemClickListener(new IMain.ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return (list == null ? 0 : list.size());
    }

    public static class BidViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivAvatar;
        TextView tvUsername, tvBudget, tvTime, tvDescription;
        AppCompatRatingBar ratingBar;

        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public BidViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
