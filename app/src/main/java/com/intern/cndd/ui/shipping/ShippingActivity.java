package com.intern.cndd.ui.shipping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.intern.cndd.R;
import com.intern.cndd.model.Orders;
import com.intern.cndd.model.Products;
import com.intern.cndd.prevalent.Prevalent;
import com.intern.cndd.ui.profile.ProfileActivity;

import java.util.ArrayList;
import java.util.List;

public class ShippingActivity extends AppCompatActivity {

    public static final String SHIP_KEY = "ship";

    private TextView mEditTextView;
    private TextView mNameTextView;
    private TextView mPhoneTextView;
    private TextView mAddressTextView;
    private TextView mStatusShippingTextView;
    private RecyclerView mProductsRecyclerView;
    private Button mConfirmButton;
    private Orders mOrders;
    private List<Products> mProductsList;
    private ShipAdapter mShipAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);

        mEditTextView = findViewById(R.id.editTextView);
        mNameTextView = findViewById(R.id.nameTextView);
        mPhoneTextView = findViewById(R.id.phoneTextView);
        mAddressTextView = findViewById(R.id.addressTextView);
        mProductsRecyclerView = findViewById(R.id.productsRecyclerView);
        mConfirmButton = findViewById(R.id.confirmButton);
        mStatusShippingTextView = findViewById(R.id.statusShippingTextView);

        mProductsList = new ArrayList<>();

        mShipAdapter = new ShipAdapter(this, mProductsList);
        mProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductsRecyclerView.setAdapter(mShipAdapter);

        loadInformationShipping();

        loadProduct();

        mEditTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOrders.getState().equals("No shipped")) {
                    Intent intent = new Intent(ShippingActivity.this, ProfileActivity.class);
                    intent.putExtra(SHIP_KEY, mOrders);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void loadInformationShipping() {

        final DatabaseReference shipRef =  FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getId());
        shipRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mOrders = snapshot.getValue(Orders.class);

                mNameTextView.setText(mOrders.getName());
                mPhoneTextView.setText(mOrders.getPhone());
                mAddressTextView.setText(mOrders.getAddress());
                mStatusShippingTextView.setText("Status shipping: " + mOrders.getState());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void loadProduct() {
        final DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(Prevalent.currentOnlineUser.getId()).child("Products");

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mProductsList.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Products product = postSnapshot.getValue(Products.class);
                    mProductsList.add(product);
                }
                mShipAdapter.setProductsList(mProductsList);
                mShipAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}