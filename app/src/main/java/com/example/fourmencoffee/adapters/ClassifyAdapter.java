package com.example.fourmencoffee.adapters;

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
import com.example.fourmencoffee.classify.Classify;
import com.example.fourmencoffee.classify.DescriptionFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ClassifyAdapter extends FirebaseRecyclerAdapter<Classify,ClassifyAdapter.myviewholder> {

    public ClassifyAdapter(@NonNull FirebaseRecyclerOptions<Classify> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Classify model) {
        holder.title_news.setText(model.getTitle());
        holder.title_news.setTextSize(23);

        holder.sub_title.setText(model.getSub_title());
        holder.sub_title.setTextSize(20);
        holder.sub_title.setLines(5);

        Glide.with(holder.img1.getContext()).load(model.getUrl()).override(500,500)
                .into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity =(AppCompatActivity)view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_container,
                        new DescriptionFragment(model.getTitle(),model.getSub_title(),model.getUrl()))
                        .addToBackStack(null).commit();
            }
        });


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
