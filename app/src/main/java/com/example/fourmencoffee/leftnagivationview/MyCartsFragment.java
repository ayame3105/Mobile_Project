package com.example.fourmencoffee.leftnagivationview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.adapters.MyCartAdapter;
import com.example.fourmencoffee.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {


    public MyCartsFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> cartModelList;

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);
    //Xu ly tong tien
        overTotalAmount = root.findViewById(R.id.textView3);
        LocalBroadcastManager.getInstance(getActivity()).
                registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));
        //
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        myCartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(myCartAdapter);

        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });











        return root;
    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount",0);
            overTotalAmount.setText("Tổng hoá đơn: "+totalBill+"đ");
        }
    };
}