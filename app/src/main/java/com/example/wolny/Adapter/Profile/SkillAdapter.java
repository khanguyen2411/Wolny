package com.example.wolny.Adapter.Profile;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Adapter.AddSkill.CurrentSkillAdapter;
import com.example.wolny.IMain;
import com.example.wolny.R;

import java.util.List;

public class SkillAdapter extends RecyclerView.Adapter<SkillAdapter.SkillViewHolder>{

    Context mContext;
    private List<String> skills;

    public SkillAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setList(List<String> skills){
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
    }


    @Override
    public int getItemCount() {
        return (skills == null) ? 0 : skills.size();
    }

    public static class SkillViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvSkill;

        public SkillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkill = itemView.findViewById(R.id.tvSkill);

        }


    }
}
