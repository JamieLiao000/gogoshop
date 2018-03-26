package com.example.liaochieh_yu.gogo2.Main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by liaochieh-yu on 2018/3/20.
 */

public class HomeTabLeftFragment extends Fragment {
    CarouselView carouselView;
    RecyclerView recyclerView;
    ImageButton heartButton;

    int[] sampleImages = {R.drawable.carousel01, R.drawable.carousel02, R.drawable.carousel03};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab_left, null);

        carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());


//Log.d("我的訊息2",String.valueOf(numOfRecycler));
        recyclerView.setLayoutManager(layoutManager);


        final List<Promotion> promotionList = new ArrayList<>();
        promotionList.add(new Promotion("春季活動", R.drawable.carousel03));
        promotionList.add(new Promotion("夏季活動", R.drawable.carousel02));
        promotionList.add(new Promotion("秋季活動", R.drawable.carousel01));
        promotionList.add(new Promotion("冬季活動", R.drawable.carousel03));
        promotionList.add(new Promotion("冬季活動", R.drawable.carousel02));
        promotionList.add(new Promotion("冬季活動", R.drawable.carousel01));
        promotionList.add(new Promotion("冬季活動", R.drawable.carousel02));
        promotionList.add(new Promotion("冬季活動", R.drawable.carousel03));


        recyclerView.setAdapter(new PromotionAdapter(promotionList));
        return rootView;
    }

    private class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
        private List<Promotion> promotionList;

        public PromotionAdapter(List<Promotion> promotionList) {
            this.promotionList = promotionList;
        }

        @Override//1利用單一項目的介面佈局檔來建立View物件
        public PromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_card, parent, false);
            return new ViewHolder(view);

        }


        @Override//3
        public void onBindViewHolder(PromotionAdapter.ViewHolder holder, int position) {
            final Promotion pro = promotionList.get(position);
            holder.iView.setImageResource(pro.getPic());
            holder.tTitle.setText(pro.getName());
            Log.d("我的", String.valueOf(position));
            /*處理點擊活動跳轉事件點擊*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), pro.getName(), Toast.LENGTH_LONG).show();
                }
            });
            /*處理收藏事件點擊*/
            holder.heartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "收藏", Toast.LENGTH_LONG).show();
                }
            });



        }

        @Override
        public int getItemCount() {
            return promotionList.size();
        }

        //2-1
        class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView iView;
            private TextView tTitle;
            private ImageButton heartButton;

            //建構子
            public ViewHolder(View view) {
                super(view);
                iView = view.findViewById(R.id.ivPic);
                tTitle = view.findViewById(R.id.tvTitle);
                heartButton=view.findViewById(R.id.heartButton);

            }

        }
    }


    //    處理輪播的點擊
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "Clicked item: " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };//carouselView click end



}
