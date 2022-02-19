package com.example.wolny.Adapter.Main.Browse;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Activity.JobDetailActivity;
import com.example.wolny.IMain;
import com.example.wolny.Model.Job;
import com.example.wolny.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.PopularJobViewHolder> {

    Context mContext;
    List<Job> list;
    DatabaseReference databaseReference;

    public JobAdapter(Context mContext, DatabaseReference databaseReference, List<Job> list) {
        this.mContext = mContext;
        this.databaseReference = databaseReference;
        this.list = list;
    }

    public void setList(List<Job> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PopularJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job, parent, false);
        return new PopularJobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PopularJobViewHolder holder, int position) {
        Job job = list.get(position);
        if (job != null) {
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
        assert job != null;
        holder.tvTitle.setText(job.getTitle());
        holder.tvTime.setText(job.getTime());

        String budget = job.getBudget() + " " + job.getCurrency();

        if(job.getType().equals("Fixed Price")){
            holder.tvBudget.setText(budget);
        } else {
            holder.tvBudget.setText(budget + " /hour");
        }

        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("job", list.get(position));
                intent.putExtra("bundle", bundle);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (list == null ? 0 : list.size());
    }

    public static class PopularJobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvTime, tvBudget, tvNumberOfBids;
        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public PopularJobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvBudget = itemView.findViewById(R.id.tvBudget);
            tvNumberOfBids = itemView.findViewById(R.id.tvNumberOfBids);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
