package com.example.wolny.Adapter.JobDetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.wolny.Activity.AnotherProfileActivity;
import com.example.wolny.IMain;
import com.example.wolny.Model.Bid;
import com.example.wolny.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.BidViewHolder> {

    Context mContext;
    List<Bid> list;
    String currency, type;
    String uid;
    BottomSheetDialog bottomSheetDialog;
    View bottomSheetView;
    DatabaseReference databaseReference;


    public BidAdapter(Context mContext, String currency, String type, String uid, BottomSheetDialog bottomSheetDialog, View bottomSheetView, DatabaseReference databaseReference) {
        this.mContext = mContext;
        this.currency = currency;
        this.type = type;
        this.uid = uid;
        this.bottomSheetDialog = bottomSheetDialog;
        this.bottomSheetView = bottomSheetView;
        this.databaseReference = databaseReference;
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

        if (!bid.getFreelancerID().equals(uid)) {
            holder.ivEdit.setVisibility(View.GONE);
        } else {
            ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
            holder.ivEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.show();

                    Button btCancel = bottomSheetView.findViewById(R.id.btCancel);
                    Button btPlaceBid = bottomSheetView.findViewById(R.id.btPlaceBid);
                    EditText etBudget = bottomSheetView.findViewById(R.id.etBudget);
                    EditText etTime = bottomSheetView.findViewById(R.id.etTime);
                    EditText etDescription = bottomSheetView.findViewById(R.id.etDescription);

                    btCancel.setOnClickListener(v1 -> {
                        bottomSheetDialog.dismiss();
                    });

                    etBudget.setText(bid.getBudget());
                    etTime.setText(bid.getTime());
                    etDescription.setText(bid.getDescription());

                    btPlaceBid.setOnClickListener(v1 -> {
                        String budget = etBudget.getText().toString().trim();
                        String time = etTime.getText().toString().trim();
                        String description = etDescription.getText().toString().trim();

                        if (budget.isEmpty() || time.isEmpty() || description.isEmpty()) {
                            Toast.makeText(mContext, "Empty information, check and try again", Toast.LENGTH_SHORT).show();
                        } else {

                            Bid bid1 = new Bid(bid.getJobId(), bid.getEmployerID(), uid, bid.getFreelancerName(), bid.getFreelancerAvatar(), description, time, budget);

                            databaseReference.child("Bids").child(bid.getJobId()).child(uid).setValue(bid1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(mContext, "Update bid successfully", Toast.LENGTH_SHORT).show();
                                        list.set(position, bid1);
                                        notifyDataSetChanged();
                                    } else {
                                        Toast.makeText(mContext, "Error!!!!", Toast.LENGTH_SHORT).show();
                                    }
                                    bottomSheetDialog.dismiss();
                                }
                            });
                        }
                    });
                }
            });

            viewBinderHelper.bind(holder.swipeRevealLayout, bid.getFreelancerID());
            holder.llDelete.setOnClickListener(v -> {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("CONFIRM DELETE BID")
                        .setMessage("Do you want to delete your bid")
                        .setPositiveButton("OK", (dialog, which) -> {
                            list.remove(position);
                            notifyDataSetChanged();
                            databaseReference.child("Bids").child(bid.getJobId()).child(bid.getFreelancerID()).removeValue().addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(mContext, "Delete successfully", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(mContext, "Error!!!!", Toast.LENGTH_LONG).show();
                                }
                            });
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> {

                        })
                        .create();

                alertDialog.show();
            });
        }

        String imgUrl = bid.getFreelancerAvatar();
        if (imgUrl != null && !imgUrl.equals("default")) {
            Glide.with(mContext).load(imgUrl).placeholder(R.drawable.progress_bar).into(holder.ivAvatar);
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

        if (bid.getEmployerID().equals(uid)) {
            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AnotherProfileActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("information", list.get(position));
                    bundle.putString("currency", currency);
                    bundle.putString("type", type);
                    intent.putExtra("bundle", bundle);
                    mContext.startActivity(intent);
                }
            });
        }

        if (bid.getFreelancerID().equals(uid)) {

            holder.tvUsername.setTextColor(Color.parseColor("#017fed"));

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
        ImageView ivAvatar, ivEdit;
        TextView tvUsername, tvBudget, tvTime, tvDescription;
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout llDelete, main;

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
            ivEdit = itemView.findViewById(R.id.ivEdit);
            swipeRevealLayout = itemView.findViewById(R.id.mainLayout);
            llDelete = itemView.findViewById(R.id.llDelete);
            main = itemView.findViewById(R.id.main);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
