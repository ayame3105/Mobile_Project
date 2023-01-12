package com.example.fourmencoffee.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.activity.ViewAllActivity;
import com.example.fourmencoffee.model.PoppularModel;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {
    private Context context;
    private List<PoppularModel> poppularModelList;

    public PopularAdapters(Context context, List<PoppularModel> poppularModelList) {
        this.context = context;
        this.poppularModelList = poppularModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_item, parent,  false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(poppularModelList.get(position).getImg_url()).into(holder.popImg);
        holder.name.setText(poppularModelList.get(position).getName());
        holder.rating.setText(poppularModelList.get(position).getRating());
        holder.description.setText(poppularModelList.get(position).getDescription());
        holder.discount.setText(poppularModelList.get(position).getDiscount());
        holder.type.setText(poppularModelList.get(position).getType());




    }

    @Override
    public int getItemCount() {
        return poppularModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name, description,rating,discount, type;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg = itemView.findViewById(R.id.pop_img);
            name = itemView.findViewById(R.id.pop_name);
            description = itemView.findViewById(R.id.pop_des);
            discount = itemView.findViewById(R.id.pop_discount);
            rating = itemView.findViewById(R.id.pop_rating);
            type = itemView.findViewById(R.id.pop_type);
        }
    }
}
