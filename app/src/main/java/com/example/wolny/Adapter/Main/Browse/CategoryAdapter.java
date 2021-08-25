package com.example.wolny.Adapter.Main.Browse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wolny.Activity.CategoryActivity;
import com.example.wolny.IMain;
import com.example.wolny.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context mContext;
    List<String> titles;
    List<Integer> images;

    public CategoryAdapter(Context mContext, List<String> titles, List<Integer> images) {
        this.mContext = mContext;
        this.titles = titles;
        this.images = images;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.ivCategory.setImageResource(images.get(position));
        holder.tvCategory.setText(titles.get(position));

        holder.setItemClickListener((view, position1, isLongClick) -> {
            Intent intent = new Intent(mContext, CategoryActivity.class);
            intent.putExtra("category", titles.get(position1));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return (titles == null || images == null) ? 0 : titles.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private IMain.ItemClickListener itemClickListener;

        public void setItemClickListener(IMain.ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        ImageView ivCategory;
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCategory = itemView.findViewById(R.id.ivCategory);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }
}
