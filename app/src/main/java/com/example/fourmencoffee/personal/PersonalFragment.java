package com.example.fourmencoffee.personal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fourmencoffee.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalFragment extends Fragment {
    private DatabaseReference databaseReference;
    private TextView username, email, role;
    ImageView profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal, container, false);
        // Inflate the layout for this fragment
        username = root.findViewById(R.id.edit_user_name);
        role = root.findViewById(R.id.edit_role);
        email = root.findViewById(R.id.edit_user_email);
        profile = root.findViewById(R.id.profile_img);


        databaseReference = FirebaseDatabase.getInstance().getReference();




        return root;
    }
}