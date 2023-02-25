package com.example.fourmencoffee.leftnagivationview;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.activity.PlacedOrderActivity;
import com.example.fourmencoffee.adapters.MyCartAdapter;
import com.example.fourmencoffee.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    MyCartAdapter myCartAdapter;
    List<MyCartModel> cartModelList;

    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView overTotalAmount;
    Button buyNow;
    Toolbar toolbar;
    public MyCartsFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);
        //Xu ly tong tien


//        toolbar = root.findViewById(R.id.toolbar_my_cart);
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.setSupportActionBar(toolbar);
//        activity.getSupportActionBar().setTitle("Giỏ hàng của tôi");

        buyNow = root.findViewById(R.id.buy_now);
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

        db.collection("AddToCart").document(auth.
                    getCurrentUser().getUid()).collection("CurrentUser")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            for (DocumentSnapshot documentSnapshot: task.getResult().getDocuments()){
                                String documentId = documentSnapshot.getId();
                                MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                                cartModel.setDocumentId(documentId);
                                cartModelList.add(cartModel);
                                myCartAdapter.notifyDataSetChanged();
                                recyclerView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCartAdapter.getItemCount() == 0)
                {
                    Toast.makeText(getActivity(), "Giỏ hàng bạn đang trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
                    intent.putExtra("itemList", (Serializable) cartModelList);
                    startActivity(intent);

                    db.collection("AddToCart").document(auth.
                                    getCurrentUser().getUid()).collection("CurrentUser")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {

                                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                        String documentId = documentSnapshot.getId();
                                        db.collection("AddToCart")
                                                .document(documentId)
                                                .delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                    }
                                                });
                                    }
                                }
                            });




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