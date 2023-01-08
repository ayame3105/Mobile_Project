package com.example.fourmencoffee.classify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.adapters.ClassifyAdapter;
import com.example.fourmencoffee.databinding.FragmentClassifyBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ClassifyFragment extends Fragment {
    private FragmentClassifyBinding fragmentClassifyBinding;
    private View view;
    private RecyclerView recyclerViewtrongnuoc, recyclerViewngoainuoc;

    ClassifyAdapter classifyAdapterTrongNuoc;
    ClassifyAdapter classifyAdapterNgoaiNuoc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_classify,container, false);
        //Trong nước
        recyclerViewtrongnuoc = (RecyclerView)view.findViewById(R.id.rcv_classify_trong_nuoc);
        recyclerViewtrongnuoc.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Classify> optionsTrongNuoc =
                new FirebaseRecyclerOptions.Builder<Classify>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Classify")
                                .child("TrongNuoc"), Classify.class)
                        .build();

        classifyAdapterTrongNuoc = new ClassifyAdapter(optionsTrongNuoc);
        recyclerViewtrongnuoc.setAdapter(classifyAdapterTrongNuoc);
        //Ngoài nước
        recyclerViewngoainuoc = (RecyclerView)view.findViewById(R.id.rcv_classify_ngoai_nuoc);
        recyclerViewngoainuoc.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Classify> optionsNgoaiNuoc =
                new FirebaseRecyclerOptions.Builder<Classify>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Classify")
                                .child("NgoaiNuoc"), Classify.class)
                        .build();

        classifyAdapterNgoaiNuoc = new ClassifyAdapter(optionsNgoaiNuoc);
        recyclerViewngoainuoc.setAdapter(classifyAdapterNgoaiNuoc);

        //
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        classifyAdapterTrongNuoc.startListening();
        classifyAdapterNgoaiNuoc.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        classifyAdapterTrongNuoc.stopListening();
        classifyAdapterNgoaiNuoc.stopListening();
    }




}