package com.example.fourmencoffee.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.model.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;
    String thoigian ="";
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("AddToCart").document(auth.
                        getCurrentUser().getUid()).collection("CurrentUser").
                        document(cartModelList.get(position).getDocumentId())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    cartModelList.remove(cartModelList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"Đã xoá",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(context,"Lỗi",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        //pass số lượng to My Cart Fragment
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        //
        thoigian = cartModelList.get(position).getCurrentTime();
        Intent intentThoiGian = new Intent("ThoiGian");
        intentThoiGian.putExtra("ThoiGianMua", thoigian);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentThoiGian);


    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, quantity, totalPrice;
        ImageView deleteItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
            deleteItem = itemView.findViewById(R.id.delete);





        }
    }
}
