package com.example.fourmencoffee.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.fourmencoffee.R;
import com.example.fourmencoffee.model.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    int totalQuantity = 1;
    int totalPrice = 0;
    TextView price, rating, description, quantity;
    Button addToCart;
    ImageView addItem, removeItem;
    ViewAllModel viewAllModel = null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();



        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        detailedImg = findViewById(R.id.detailed_img);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_description);
        addToCart = findViewById(R.id.add_to_cart);
        removeItem = findViewById(R.id.remove_item);
        addItem = findViewById(R.id.add_item);
        quantity = findViewById(R.id.quantity);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.detailed_toolbar);
       setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (viewAllModel != null)
        {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Giá: "+viewAllModel.getPrice()+"đ");
            totalPrice = viewAllModel.getPrice() * totalQuantity;
        }


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity < 20){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalQuantity > 0){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });











    }

    private void addToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();

        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });




    }









}