package com.example.fourmencoffee.leftnagivationview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.activity.QLHDActivity;
import com.example.fourmencoffee.adapters.MyCartAdapter;
import com.example.fourmencoffee.adapters.MyOrderedAdapter;
import com.example.fourmencoffee.adapters.QLHD_Da_XuLy_Adapter;
import com.example.fourmencoffee.adapters.QLHD_Dang_XuLy_Adapter;
import com.example.fourmencoffee.model.MyCartModel;
import com.example.fourmencoffee.model.MyOrderedModel;
import com.example.fourmencoffee.model.QLHD_Da_Xu_Ly_Model;
import com.example.fourmencoffee.model.QLHD_Dang_Xu_Ly_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MyOrdersFragment extends Fragment {
    RecyclerView recyclerView;
    MyOrderedAdapter myOrderedAdapter;
    List<MyOrderedModel> orderedModelList;

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount, banchuamuagica;
    ImageView myorderimg;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);


        overTotalAmount = root.findViewById(R.id.textView3);
        LocalBroadcastManager.getInstance(getActivity()).
                registerReceiver(mMessageReceiver,new IntentFilter("MyTotalAmount"));
        //
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = root.findViewById(R.id.recycle_view_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderedModelList = new ArrayList<>();
        myOrderedAdapter = new MyOrderedAdapter(getActivity(),orderedModelList);
        recyclerView.setAdapter(myOrderedAdapter);

        {
            db.collection("DonHangCuaUserDaXuLy").document(auth.
                            getCurrentUser().getUid()).collection("CurrentUser")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful())
                            {
                                for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                    String documentId = documentSnapshot.getId();
                                    MyOrderedModel orderedModel = documentSnapshot.toObject(MyOrderedModel.class);
                                    orderedModel.setDocumentId(documentId);
                                    orderedModelList.add(orderedModel);
                                    myOrderedAdapter.notifyDataSetChanged();
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    });

        }







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