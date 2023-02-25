package com.example.fourmencoffee.classify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.R;


public class DescriptionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    String title, sub_title, sub_title1, sub_title2, sub_title3, url;
    public DescriptionFragment() {

    }
    public DescriptionFragment(String title, String sub_title, String sub_title1
                               ,String sub_title2, String sub_title3, String url) {
        this.title = title;
        this.sub_title=sub_title;
        this.sub_title1=sub_title1;
        this.sub_title2=sub_title2;
        this.sub_title3=sub_title3;
        this.url=url;
    }

    public static DescriptionFragment newInstance(String param1, String param2) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentphuc@gmail.com
        View view = inflater.inflate(R.layout.fragment_description, container, false);
        ImageView imageholder=view.findViewById(R.id.imageholder);
        TextView titleholder=view.findViewById(R.id.titleholder);
        TextView subtitleholder1=view.findViewById(R.id.subtitleholder1);
        TextView subtitleholder2=view.findViewById(R.id.subtitleholder2);
        TextView subtitleholder3=view.findViewById(R.id.subtitleholder3);
        TextView subtitleholder4=view.findViewById(R.id.subtitleholder4);



        titleholder.setText(title);
        subtitleholder1.setText(sub_title);
        subtitleholder2.setText(sub_title1);
        subtitleholder3.setText(sub_title2);
        subtitleholder4.setText(sub_title3);
        Glide.with(getContext()).load(url).into(imageholder);

        return view;
    }
    public void onBackPressed(){
        AppCompatActivity activity = (AppCompatActivity)getContext();
//        activity.getSupportFragmentManager().beginTransaction().replace(R.id.raplaceclassifylayout
//                ,new ClassifyFragment()).addToBackStack(null);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_classify,
                new ClassifyFragment()).addToBackStack(null );
        fragmentTransaction.commit();



    }
}