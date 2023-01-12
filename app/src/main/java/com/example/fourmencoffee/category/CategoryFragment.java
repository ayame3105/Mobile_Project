package com.example.fourmencoffee.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.adapters.NavCategoryAdapter;
import com.example.fourmencoffee.adapters.PopularAdapters;
import com.example.fourmencoffee.model.NavCategoryModel;
import com.example.fourmencoffee.model.PoppularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

