package com.example.liaochieh_yu.gogo2.Main;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import com.example.liaochieh_yu.gogo2.MainActivity;
import com.example.liaochieh_yu.gogo2.Others.Util;
import com.example.liaochieh_yu.gogo2.Promotion.PromotionDAO;
import com.example.liaochieh_yu.gogo2.Promotion.PromotionDAOImpl;
import com.example.liaochieh_yu.gogo2.Promotion.PromotionVO;
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
    private ProgressDialog progressDialog;
    private GetAllPromotions task;
    private List<PromotionVO> promotionList;

    int[] sampleImages = {R.drawable.carousel01, R.drawable.carousel02, R.drawable.carousel03};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_tab_left, null);

        carouselView = (CarouselView) rootView.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        /**出任務**/
//        task = new GetAllPromotions();
//        task.execute();

        return rootView;
    }
     private class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {
        private List<PromotionVO> promotionList;

        public PromotionAdapter(List<PromotionVO> promotionList) {
            this.promotionList = promotionList;
        }

        @Override//1利用單一項目的介面佈局檔來建立View物件
        public PromotionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promotion_card, parent, false);
            return new ViewHolder(view);

        }
       @Override//3
        public void onBindViewHolder(PromotionAdapter.ViewHolder holder, int position) {
            final PromotionVO promotionVO = promotionList.get(position);
            byte[] bytesPic=promotionVO.getPmtpic();
             Bitmap bitmap = BitmapFactory.decodeByteArray(bytesPic, 0, bytesPic.length);


            holder.iView.setImageBitmap(bitmap);
            holder.tTitle.setText(promotionVO.getPmt_name());
            Log.d("我的", String.valueOf(position));
            /*處理點擊活動跳轉事件點擊*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), promotionVO.getPmt_name(), Toast.LENGTH_LONG).show();
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
    /**出任務拿回 活動集合**/
    class GetAllPromotions extends AsyncTask<String ,Integer,List<PromotionVO>>{
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("loading");
            progressDialog.show();


        }

        @Override
        protected List<PromotionVO> doInBackground(String... strings) {
            PromotionDAO dao=new PromotionDAOImpl();
            promotionList =dao.getAll();
            return promotionList;
        }

        @Override
        protected void onPostExecute(List<PromotionVO> promotionList) {
            progressDialog.cancel();
            if(promotionList==null||promotionList.isEmpty()){
                Util.showToast(getActivity(),"promotion not found");
            }

            showPromotionsView(getActivity(),promotionList);


        }
    }
    public void showPromotionsView(Context context, List<PromotionVO> promotionList){
        recyclerView.setAdapter(new PromotionAdapter(promotionList));
    }



}
