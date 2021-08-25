package com.example.wolny.Adapter.Main.Browse;

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

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.PopularJobViewHolder> {

    Context mContext;
    List<Job> list;

    public JobAdapter(Context mContext){
        this.mContext = mContext;
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

    @Override
    public void onBindViewHolder(@NonNull PopularJobViewHolder holder, int position) {
        Job job = list.get(position);
        holder.tvTitle.setText(job.getTitle());
        holder.tvTime.setText(job.getTime());
        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(mContext, JobDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("job", list.get(position));
                intent.putExtra("bundle",bundle);
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
