package com.gabby.librarylogs.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabby.librarylogs.ProductModel;
import com.gabby.librarylogs.R;
import com.gabby.librarylogs.databinding.FragmentNotificationsBinding;
import com.gabby.librarylogs.myadapterlike;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    RecyclerView recyclerView;
    myadapterlike myadpater;
    ArrayList<ProductModel> list;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs";
    TextView totalamount;
    TextView countitem;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        recyclerView = root.findViewById(R.id.productlistcard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalamount = root.findViewById(R.id.totalamount);
        countitem = root.findViewById(R.id.itemtext);
        list = new ArrayList<>();
        myadpater = new myadapterlike(getContext(), list);
        recyclerView.setAdapter(myadpater);

        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Map<String, ?> keys = sharedPreferences.getAll();
        int total = 0;
        int count = 0;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            Log.d("map values", entry.getKey() + ": " +
                    entry.getValue().toString());
            String a = entry.getKey();
            String[] key = a.split(" ");
            if (Objects.equals(key[0].trim(), "watchlist")) {

                String b = entry.getValue().toString();
                Type type = new TypeToken<List<String>>() {
                }.getType();
                Gson gson = new Gson();
                List<String> arrPackageData = gson.fromJson(entry.getValue().toString(), type);
                ProductModel pd = new ProductModel();
                pd.setPrice(Integer.parseInt(arrPackageData.get(0)));
                pd.setProduct_desc(arrPackageData.get(1));
                pd.setProduct_name(arrPackageData.get(3));
                pd.setUrl(arrPackageData.get(2));
                pd.setQuantity(Integer.parseInt(arrPackageData.get(4)));
                pd.setReview(Integer.parseInt(arrPackageData.get(5)));
                list.add(pd);
                total += Integer.parseInt(arrPackageData.get(0)) * Integer.parseInt(arrPackageData.get(4));
                count += 1;
            }
        }
        countitem.setText(String.valueOf(count) + " item");
        myadpater.notifyDataSetChanged();
        total += 3.95;
        totalamount.setText(String.valueOf(total));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}