package com.gabby.librarylogs.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

 import com.gabby.librarylogs.HomeAdapter;
import com.gabby.librarylogs.ProductModel;
import com.gabby.librarylogs.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerView;
    TextView username;
    DatabaseReference databaseReference;
    com.gabby.librarylogs.HomeAdapter HomeAdapter;
    EditText search;
    ArrayList<ProductModel> list = new ArrayList<>();;
    ArrayList<ProductModel> listfull = new ArrayList<>();
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";


    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sh = view.getContext().getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        if(sh.contains("Username")){
            String s2 = sh.getString("Username", "");
            username = view.findViewById(R.id.textView4);
            username.setText("Hi, "+s2);
        }


        HomeAdapter = new HomeAdapter(getContext(), list , listfull);
        recyclerView = view.findViewById(R.id.productlist);
        databaseReference = FirebaseDatabase.getInstance().getReference("product");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(HomeAdapter);
        HomeAdapter.getFilter().filter("");



// Data ko onReume pr load krwa lnn.. maybe hojaye axha..bas?


        search = view.findViewById(R.id.text_home);

        search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                String text = mEdit.toString();
                HomeAdapter.getFilter().filter(text);
//                Toast.makeText(MainActivity3.this, text, Toast.LENGTH_SHORT).show();
            }


            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        return view;
    }

    @Override
    public  void  onResume(){
        super.onResume();

        if(list.size()==0) {


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ProductModel pd = dataSnapshot.getValue(ProductModel.class);
                        //                    Toast.makeText(getContext(),String.valueOf(pd.getProduct_id()),Toast.LENGTH_SHORT).show();
                        list.add(pd);
                        listfull.add(pd);
                    }
                    HomeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            HomeAdapter.notifyDataSetChanged();
        }

    }


}