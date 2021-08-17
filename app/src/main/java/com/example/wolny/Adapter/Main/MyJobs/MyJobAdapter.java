package com.example.wolny.Adapter.Main.MyJobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.IMain;
import com.example.wolny.Model.Job;
import com.example.wolny.R;

import java.util.List;

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.MyJobViewHolder> {

    List<Job> list;
    Context mContext;

    public void setList(List<Job> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public MyJobAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_job, parent, false);
        return new MyJobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyJobViewHolder holder, int position) {
        Job job = list.get(position);
        holder.tvTitle.setText(job.getTitle());
        holder.tvCategory.setText(job.getCategory());
        String budgetText = job.getBudget() + "" + job.getCurrency();
        holder.tvBudget.setText(budgetText);
        String timeText = job.getTime() + " to complete";
        holder.tvTime.setText(timeText);

        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 0 : list.size();
    }

    public static class MyJobViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvCategory, tvBudget, tvNumberOfBids, tvTime;
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), true);
        }
    }
}
