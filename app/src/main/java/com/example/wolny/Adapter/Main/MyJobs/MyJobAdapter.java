package com.example.wolny.Adapter.Main.MyJobs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.wolny.Activity.JobDetailActivity;
import com.example.wolny.IMain;
import com.example.wolny.Model.Job;
import com.example.wolny.Model.User;
import com.example.wolny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.MyJobViewHolder> {

    public static final int IN_PROGRESS = 0;
    public static final int OPEN = 1;
    public static final int PAST = 2;

    List<Job> list;
    Context mContext;
    DatabaseReference databaseReference;
    String uid, as;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    String username = "";

    public void setList(List<Job> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyJobAdapter(Context mContext, String uid, DatabaseReference databaseReference, String as) {
        this.mContext = mContext;
        this.uid = uid;
        this.databaseReference = databaseReference;
        this.as = as;
    }

    @NonNull
    @Override
    public MyJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_job_open, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_job_progress, parent, false);
        }
        return new MyJobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyJobViewHolder holder, int position) {
        Job job = list.get(position);

        if(getItemViewType(position) == 1){
            databaseReference.child("Bids").child(job.getJobID()).addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int a = (int) snapshot.getChildrenCount();
                    holder.tvNumberOfBids.setText(a + " bids");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        holder.tvTitle.setText(job.getTitle());
        holder.tvCategory.setText(job.getCategory());
        String budgetText = job.getBudget() + "" + job.getCurrency();
        holder.tvBudget.setText(budgetText);
        String timeText = job.getTime() + " to complete";
        holder.tvTime.setText(timeText);

        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("job", list.get(position));
                intent.putExtra("bundle", bundle);
                mContext.startActivity(intent);
            }
        });

        if (getItemViewType(position) == 0 || getItemViewType(position) == 2) {
            if (as.equals("employer")) {
                holder.tvName.setText("Freelancer: " + job.getFreelancerName());
            } else {
                holder.tvName.setText("Employer: " + job.getEmployerName());
            }

            String message = "Do you confirm this job was completed by " + job.getFreelancerName();
            if (job.getEmployerID().equals(uid) && job.getStatus().equals("progress")) {
                viewBinderHelper.bind(holder.swipeRevealLayout, job.getJobID());

                holder.llComplete.setOnClickListener(v -> {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                            .setTitle("CONFIRM COMPLETE PROJECT")
                            .setMessage(message)
                            .setPositiveButton("OK", (dialog, which) -> {
                                databaseReference.child("Jobs").child(job.getJobID()).child("status").setValue("past");
                            })
                            .setNegativeButton("Cancel", (dialog, which) -> {

                            })
                            .create();

                    alertDialog.show();
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public static class MyJobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvCategory, tvBudget, tvNumberOfBids, tvTime, tvName;
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout llComplete;
        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyJobViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvNumberOfBids = itemView.findViewById(R.id.tvNumberOfBids);
            tvTime = itemView.findViewById(R.id.tvTime);
            swipeRevealLayout = itemView.findViewById(R.id.layout);
            llComplete = itemView.findViewById(R.id.llComplete);
            tvName = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (list.get(position).getStatus()) {
            case "open":
                return OPEN;
            case "progress":
                return IN_PROGRESS;
            case "past":
                return PAST;
        }
        return OPEN;
    }
}
