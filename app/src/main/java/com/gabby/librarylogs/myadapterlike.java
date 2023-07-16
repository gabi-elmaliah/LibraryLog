package com.gabby.librarylogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class myadapterlike extends RecyclerView.Adapter<myadapterlike.MyViewHolder> {

    Context context;
    ArrayList<ProductModel> list;
    ArrayList<ProductModel> listfull;

    public myadapterlike(Context context, ArrayList<ProductModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public myadapterlike.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        return new myadapterlike.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myadapterlike.MyViewHolder holder, int position) {
        ProductModel pd = list.get(position);
        holder.productname.setText(pd.getProduct_name());
        holder.productdes.setText(pd.getProduct_desc());
        holder.productprice.setText(String.valueOf(pd.getPrice()));
        System.out.println(pd.quantity);
        float cal =  (float) pd.getReview()/2;
        holder.ratingBar1.setRating(cal);

        Picasso.get().load(pd.getUrl()).into(holder.productimg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productdes, productprice, productquanty;
        EditText search;
        RatingBar ratingBar1;
        ImageView productimg, addtocard;
        AutoCompleteTextView autoCompleteTextView;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            search = itemview.findViewById(R.id.text_home);
            productname = itemview.findViewById(R.id.productName);
            productimg = itemview.findViewById(R.id.productImg);
            productdes = itemview.findViewById(R.id.productDes);
            productprice = itemview.findViewById(R.id.productPrice);
            ratingBar1 = itemview.findViewById(R.id.simpleRatingBar);
        }
    }


}
