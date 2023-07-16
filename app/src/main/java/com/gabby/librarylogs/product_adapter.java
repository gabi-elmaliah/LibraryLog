package com.gabby.librarylogs;

import android.content.Context;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class product_adapter extends RecyclerView.Adapter<product_adapter.MyViewHolder>{
    Context context;
    ArrayList<ProductModel> list;
    ArrayList<ProductModel> listfull;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";

    public product_adapter(Context context, ArrayList<ProductModel> list,ArrayList<ProductModel> listfull) {
        this.context = context;
        this.list = list;
        this.listfull = listfull;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        ProductModel pd = list.get(position);
        holder.productname.setText(pd.getProduct_name());
        holder.productdes.setText(pd.getProduct_desc());
        holder.productprice.setText(String.valueOf(pd.getPrice()) + " GBP");
        Picasso.get().load(pd.getUrl()).into(holder.productimg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productdes, productprice, selectbox;
        ImageView productimg, addtocard;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            productname = itemview.findViewById(R.id.productName);
            productimg = itemview.findViewById(R.id.productImg);
            productdes = itemview.findViewById(R.id.productDes);
            productprice = itemview.findViewById(R.id.productPrice);
        }
    }
}
