package com.example.fourmencoffee.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.activity.DetailedActivity;
import com.example.fourmencoffee.activity.DetailedClassify;
import com.example.fourmencoffee.classify.Classify;
import com.example.fourmencoffee.classify.ClassifyFragment;
import com.example.fourmencoffee.classify.DescriptionFragment;
import com.example.fourmencoffee.model.ViewAllModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class ClassifyAdapter extends FirebaseRecyclerAdapter<Classify,ClassifyAdapter.myviewholder> {
    Context context;
    List<Classify> list;

    public ClassifyAdapter(@NonNull FirebaseRecyclerOptions<Classify> options, Context context, List<Classify> list) {
        super(options);
        this.context = context;
        this.list = list;
    }

    public ClassifyAdapter(@NonNull FirebaseRecyclerOptions<Classify> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull Classify model) {
        holder.title_news.setText(model.getTitle());
        holder.title_news.setTextSize(23);

        holder.sub_title.setText(model.getSub_title());
        holder.sub_title.setTextSize(20);
        holder.sub_title.setLines(5);

        Glide.with(holder.img1.getContext()).load(model.getUrl()).override(500,500)
                .into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.relative_layout_main
                , new DescriptionFragment( model.getTitle()
                                ,model.getSub_title(), model.getSub_title1(),model.getSub_title2()
                                , model.getSub_title3(),model.getUrl())).addToBackStack(null).commit();
//                Intent intent = new Intent(context, DetailedClassify.class);
//                intent.putExtra("detail_classify", list.get(position));
//                context.startActivity(intent);
            }
        });


    }
    private void onClickGoToDetail(Classify classify)
    {
        Intent intent = new Intent(context,DetailedClassify.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", classify);
        intent.putExtras(bundle);
        context.startActivity(intent) ;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myviewholder(view);
    }

    public static class myviewholder extends RecyclerView.ViewHolder{
        ImageView img1;
        TextView title_news, sub_title;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            //progressBar = itemView.findViewById(R.id.custom_progress_bar);

            title_news = itemView.findViewById(R.id.title_news);
            sub_title = itemView.findViewById(R.id.sub_title);
            img1 = itemView.findViewById(R.id.img1);
        }
    }
//    @Override
//    public void onDataChanged(){
//        if (progressBar != null)
//            progressBar.setVisibility(View.GONE);
//    }


}
