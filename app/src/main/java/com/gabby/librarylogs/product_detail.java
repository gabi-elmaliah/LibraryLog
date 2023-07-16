package com.gabby.librarylogs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class product_detail extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    product_adapter product_adapter;
    EditText search;
    ArrayList<ProductModel> list;
    ArrayList<ProductModel> listfull;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        recyclerView = findViewById(R.id.productlistdetail);
        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        listfull = new ArrayList<>();
        product_adapter = new product_adapter(this, list , listfull);
        recyclerView.setAdapter(product_adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel pd = dataSnapshot.getValue(ProductModel.class);
                    if(pd.getProduct_id()==1){
                        list.add(pd);
                        listfull.add(pd);
                    }

                }
                product_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}