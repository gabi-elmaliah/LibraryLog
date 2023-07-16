package com.gabby.librarylogs;

import android.content.Context;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<ProductModel> list;
    ArrayList<ProductModel> listfull;
    String[] item = {"1", "2", "3","4","5"};
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapteritem;

    String MyPREFERENCES = "MyPrefs";

    public HomeAdapter(Context context, ArrayList<ProductModel> list,ArrayList<ProductModel> listfull) {
        this.context = context;
        this.list = list;
        this.listfull = listfull;
    }

    public void clear() {
        int size = list.size();
        list.clear();
        listfull.clear();
        notifyItemRangeRemoved(0, size);
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
        holder.addtocard.setId(pd.getProduct_id());
        holder.addtofovor.setId(pd.getProduct_id());
        holder.constraintLayout.setId(pd.getProduct_id());

//        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(context, "Item Click", Toast.LENGTH_SHORT).show();
//                int id = view.getId();
//                ProductModel pd = list.get(id - 1);
//                ArrayList<String> store = new ArrayList<>();
//                store.add(String.valueOf(pd.getPrice()));
//                store.add(String.valueOf(pd.getProduct_desc()));
//                store.add(String.valueOf(pd.getUrl()));
//                store.add(String.valueOf(pd.getProduct_name()));
//                store.add(String.valueOf(pd.getReview()));
////                context.startActivity(new Intent(context, product_detail.class));
//                view.getContext().startActivity(new Intent(context, product_detail.class));
////                Toast.makeText(context, String.valueOf(id), Toast.LENGTH_SHORT).show();
////                System.out.println(store);
//            }
//        });

        holder.addtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                System.out.println(String.valueOf(id));
                ProductModel pd = list.get(id - 1);
                ArrayList<String> store = new ArrayList<>();
                store.add(String.valueOf(pd.getPrice()));
                store.add(String.valueOf(pd.getProduct_desc()));
                store.add(String.valueOf(pd.getUrl()));
                store.add(String.valueOf(pd.getProduct_name()));
//               store.add(String.valueOf(pd.getReview()));

                SharedPreferences.Editor editor = sharedPreferences.edit();
                String value = sharedPreferences.getString("watchlist "+String.valueOf(pd.getProduct_id()), "");
                System.out.println("value");
                System.out.println(value);

                if (value.isEmpty()) {
//                   // add insert data
                    System.out.println("new added item");
                    store.add(String.valueOf(1));
                    store.add(String.valueOf(pd.getReview()));
                    Gson gson = new Gson();
                    String json = gson.toJson(store);
                    editor.putString("watchlist "+String.valueOf(pd.getProduct_id()), json);
                    editor.apply();
                }
                else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    System.out.println("Increase item");
                    Gson gson = new Gson();
                    List<String> arrPackageData = gson.fromJson(value, type);
                    store.add(String.valueOf(Integer.parseInt(arrPackageData.get(4)) + 1));
                    store.add(String.valueOf(pd.getReview()));
                    String json = gson.toJson(store);
                    editor.putString("watchlist "+String.valueOf(pd.getProduct_id()), json);
                    editor.apply();
                    System.out.println(arrPackageData);
                }
                Toast.makeText(context , "watchlist added",Toast.LENGTH_SHORT).show();
            }
        });
        holder.addtofovor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                System.out.println(String.valueOf(id));
                ProductModel pd = list.get(id - 1);
                ArrayList<String> store = new ArrayList<>();
                store.add(String.valueOf(pd.getPrice()));
                store.add(String.valueOf(pd.getProduct_desc()));
                store.add(String.valueOf(pd.getUrl()));
                store.add(String.valueOf(pd.getProduct_name()));


                SharedPreferences.Editor editor = sharedPreferences.edit();
                String value = sharedPreferences.getString("favorites "+String.valueOf(pd.getProduct_id()), "");
                System.out.println("value");
                System.out.println(value);

                if (value.isEmpty()) {
//                   // add insert data
                    System.out.println("new added item");
                    store.add(String.valueOf(1));
                    store.add(String.valueOf(pd.getReview()));
                    Gson gson = new Gson();
                    String json = gson.toJson(store);
                    editor.putString("favorites "+String.valueOf(pd.getProduct_id()), json);
                    editor.apply();
                }
                else {
                    Type type = new TypeToken<List<String>>() {
                    }.getType();
                    System.out.println("Increase item");
                    Gson gson = new Gson();
                    List<String> arrPackageData = gson.fromJson(value, type);
                    store.add(String.valueOf(Integer.parseInt(arrPackageData.get(4)) + 1));
                    store.add(String.valueOf(pd.getReview()));
                    String json = gson.toJson(store);
                    editor.putString("favorites "+String.valueOf(pd.getProduct_id()), json);
                    editor.apply();
                    System.out.println(arrPackageData);
                }
                Toast.makeText(context , "favorites added",Toast.LENGTH_SHORT).show();
            }
        });


//        adapteritem = new ArrayAdapter<>(context.getApplicationContext(), R.layout.item, item);
//        holder.autoCompleteTextView.setAdapter(adapteritem);

//        holder.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//
//                System.out.println(String.valueOf(item));
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter(){
        return  productfilter;
    }

    private Filter productfilter  = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ProductModel>  filterlist = new ArrayList<>();
            if(charSequence ==null || charSequence.length()==0){
                filterlist.addAll(listfull);

            }
            else{
                String filterpattern = charSequence.toString().toLowerCase().trim();
                for (ProductModel model:listfull) {
                    if(model.getProduct_name().toLowerCase().contains(filterpattern)){
                        filterlist.add(model);
                    }

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filterlist;
            return  filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();

        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productname, productdes, productprice;
        ImageView productimg, addtocard,addtofovor;
        AutoCompleteTextView autoCompleteTextView;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);
            productname = itemview.findViewById(R.id.productName);
            productimg = itemview.findViewById(R.id.productImg);
            productdes = itemview.findViewById(R.id.productDes);
            productprice = itemview.findViewById(R.id.productPrice);
            addtocard = itemview.findViewById(R.id.addToCart);
            addtofovor = itemview.findViewById(R.id.addTOFav);
//            autoCompleteTextView = itemview.findViewById(R.id.auto_text);
            constraintLayout = itemview.findViewById(R.id.item);
        }
    }
}
