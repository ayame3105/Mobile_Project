package com.example.fourmencoffee.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fourmencoffee.R;
import com.example.fourmencoffee.model.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    int totalPrice = 0;


    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public MyCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.ViewHolder holder, int position) {
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.date.setText(cartModelList.get(position).getCurrentDate());
        holder.time.setText(cartModelList.get(position).getCurrentTime());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));

        //pass
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, quantity, totalPrice;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            quantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);






        }
    }
}
