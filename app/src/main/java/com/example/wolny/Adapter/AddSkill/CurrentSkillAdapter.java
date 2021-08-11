package com.example.wolny.Adapter.AddSkill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.IMain;
import com.example.wolny.R;

import java.util.List;

public class CurrentSkillAdapter extends RecyclerView.Adapter<CurrentSkillAdapter.CurrentSkillViewHolder>{

    Context mContext;
    public static List<String> skills;

    public CurrentSkillAdapter(Context context){
        this.mContext = context;
    }

    public void setList(List<String> skills) {
        CurrentSkillAdapter.skills = skills;
        notifyDataSetChanged();
    }

    public List<String> getList() {
        return skills;
    }


    public static void addSkill(String skill){
        skills.add(skill);
    }

    @NonNull
    @Override
    public CurrentSkillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curent_skill, parent, false);

        return new CurrentSkillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentSkillViewHolder holder, int position) {
        holder.tvSkill.setText(skills.get(position).trim());

        holder.ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skills.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (skills == null) ? 0 : skills.size();
    }

    static class CurrentSkillViewHolder extends RecyclerView.ViewHolder{

        TextView tvSkill;
        ImageView ivCancel;

        public CurrentSkillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSkill = itemView.findViewById(R.id.tvSkill);
            ivCancel = itemView.findViewById(R.id.ivCancel);

        }

    }
}
