package com.example.wolny.Adapter.AddSkill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.IMain;
import com.example.wolny.R;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder>{

    Context mContext;
    private List<String> skills;
    private final CurrentSkillAdapter currentSkillAdapter;
    public SkillAdapter(Context mContext, CurrentSkillAdapter currentSkillAdapter) {
        this.mContext = mContext;
        this.currentSkillAdapter = currentSkillAdapter;
    }

    public void setList(List<String> skills) {
        this.skills = skills;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);

        return new SkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillViewHolder holder, int position) {
        holder.tvSkill.setText(skills.get(position));
        holder.setItemClickListener(new IMain.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if(!CurrentSkillAdapter.skills.contains(skills.get(position))){
                    CurrentSkillAdapter.skills.add(skills.get(position));
                    currentSkillAdapter.setList(CurrentSkillAdapter.skills);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (skills == null) ? 0 : skills.size();
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        private final TextView tvSkill;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkill = itemView.findViewById(R.id.tvSkill);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
             itemClickListener.onClick(v,getAdapterPosition(),true);
        }
    }
}
