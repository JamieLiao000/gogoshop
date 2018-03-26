package com.example.liaochieh_yu.gogo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.liaochieh_yu.gogo2.PurchaseCase.PurchaseCaseItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaochieh-yu on 2018/3/25.
 */

public class PurchaseCaseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_case_list);
        recyclerView = findViewById(R.id.recyclerview_purchase);




        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<PurchaseCaseItem> caseList = new ArrayList<>();
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.pro_nike_01));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.avatar));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.pro_nike_01));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.avatar));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.pro_nike_02));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.pro_nike_01));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.avatar));
        caseList.add(new PurchaseCaseItem("求幫買衣服","想要一件在米蘭的大衣","20180505","吳明珠",R.drawable.pro_nike_01));

        recyclerView.setAdapter(new CaseAdapter(caseList));


    }

    private class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.ViewHolder> {
        private List<PurchaseCaseItem> caseList;

        public CaseAdapter(List<PurchaseCaseItem> caseList) {
            this.caseList = caseList;
        }

        @Override
        public  CaseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_case_single, parent, false);
         return  new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
        final PurchaseCaseItem purchaseCaseItem =caseList.get(position);
        holder.ivPic.setImageResource(purchaseCaseItem.getPic());
        holder.caseTitle.setText(purchaseCaseItem.getTitle());
        holder.caseContent.setText(purchaseCaseItem.getContent());
        holder.caseDate.setText(purchaseCaseItem.getDate());


        }

        @Override
        public int getItemCount() {
            return caseList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView caseTitle, caseContent, caseDate;
            private ImageView ivPic;
            //建構子
            public ViewHolder(View view) {
                super(view);
                caseTitle = view.findViewById(R.id.caseTitle);
                caseContent = view.findViewById(R.id.caseContent);
                caseDate = view.findViewById(R.id.caseDate);
                ivPic = view.findViewById(R.id.casePic);
            }
        }
    }
}
