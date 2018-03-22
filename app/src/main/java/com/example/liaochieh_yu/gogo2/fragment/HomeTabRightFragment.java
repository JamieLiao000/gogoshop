package com.example.liaochieh_yu.gogo2.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.MainActivity;
import com.example.liaochieh_yu.gogo2.R;
import com.example.liaochieh_yu.gogo2.model.Product;
import com.example.liaochieh_yu.gogo2.model.Promotion;

import java.util.ArrayList;
import java.util.List;

import static com.example.liaochieh_yu.gogo2.R.color.lightgrey;

/**
 * Created by liaochieh-yu on 2018/3/20.
 */

public class HomeTabRightFragment extends Fragment {
    private Spinner spSearch;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab_right, null);
        //SPINNER
        spSearch = rootView.findViewById(R.id.spSearch);
        String[] searchType = {"最新上市", "價錢低到高", "價錢高到低"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, searchType);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spSearch.setAdapter(adapter);
        spSearch.setSelection(0, true);
        spSearch.setOnItemSelectedListener(listener);


        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        final List<Product> productList = new ArrayList<>();
        /*開始加資料*/
        productList.add(new Product("蜂蜜水果燕麥片", 70, R.drawable.pro_nike_01));
        productList.add(new Product("Nike跑鞋", 20, R.drawable.avatar));
        productList.add(new Product("蜂蜜水果燕麥片", 30, R.drawable.pro_nike_01));
        productList.add(new Product("Nike跑鞋", 40, R.drawable.pro_nike_02));
        productList.add(new Product("蜂蜜水果燕麥片", 30, R.drawable.pro_nike_01));
        productList.add(new Product("Nike跑鞋", 40, R.drawable.pro_nike_02));
        productList.add(new Product("蜂蜜水果燕麥片", 30, R.drawable.pro_nike_01));
        productList.add(new Product("Nike跑鞋", 40, R.drawable.pro_nike_02));


        recyclerView.setAdapter(new ProductAdapter(productList));
        return rootView;
    }

    Spinner.OnItemSelectedListener listener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            //切換商品排序方式
            Toast.makeText(getActivity(), "選中了", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // Nothing to do here...
        }
    };

    private class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Product> productList;

        public ProductAdapter(List<Product> productList) {
            this.productList = productList;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_mall_inner, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position) {
            final Product product = productList.get(position);
            holder.ivPic.setImageResource(product.getPic());
            holder.tvName.setText(product.getName());

            holder.tvPrice.setText("售價：" + String.valueOf(product.getPrice()));
           /* 處理點擊商品的跳頁*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), product.getName(), Toast.LENGTH_LONG).show();
                }
            });

            /* 處理收藏商品的點擊*/
            holder.imgBtn.setOnClickListener(new View.OnClickListener() {
                boolean isClicked;

                @Override
                public void onClick(View view) {
                    ImageButton imgBtn = view.findViewById(R.id.imgBtn);
                    if (!isClicked) {
                        imgBtn.setImageResource(R.drawable.icon_heart_pink_02_clicked);
                        Toast.makeText(getContext(), "收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        imgBtn.setImageResource(R.drawable.icon_heart_pink_02);
                        Toast.makeText(getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
                    }
                    isClicked = !isClicked;
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivPic;
            private TextView tvName, tvPrice;
            private ImageButton imgBtn;

            //建構子
            public ViewHolder(View view) {
                super(view);
                ivPic = view.findViewById(R.id.ivPic);
                tvName = view.findViewById(R.id.tvName);
                tvPrice = view.findViewById(R.id.tvPrice);
                imgBtn = view.findViewById(R.id.imgBtn);

            }

        }
    }
}
