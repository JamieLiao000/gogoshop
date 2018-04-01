package com.example.liaochieh_yu.gogo2;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import com.example.liaochieh_yu.gogo2.Com.ComDAO;
import com.example.liaochieh_yu.gogo2.Com.ComDAOImpl;
import com.example.liaochieh_yu.gogo2.Com.ComVO;
import com.example.liaochieh_yu.gogo2.Others.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaochieh-yu on 2018/3/25.
 */

public class ComCaseActivity extends AppCompatActivity {
    private static final String TAG = "ComCaseActivity";

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private GetAllComCases task;
    private List<ComVO> comList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG,"近來代購案件頁面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_case_list);
        recyclerView = findViewById(R.id.recyclerview_purchase);
        /**出任務**/


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        final List<ComVO> comList = new ArrayList<>();

        task = new GetAllComCases();
        task.execute();
//        recyclerView.setAdapter(new ComAdapter(comList));


    }

    private class ComAdapter extends RecyclerView.Adapter<ComAdapter.ViewHolder> {
        private List<ComVO> comList;

        public ComAdapter(List<ComVO> comList) {
            this.comList = comList;
        }

        @Override
        public  ComAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_case_single, parent, false);
         return  new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ComVO comVO = comList.get(position);
            byte[] bytesPic=comVO.getCom_pic1();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesPic, 0, bytesPic.length);
            holder.ivPic.setImageBitmap(bitmap);
            holder.caseTitle.setText(comVO.getCom_tit());
            holder.caseContent.setText(comVO.getCom_cnt());
            holder.caseDate.setText(comVO.getCom_it_sts());
        }

        @Override
        public int getItemCount() {
            return comList.size();
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
    /**出任務拿回 集合**/
    class GetAllComCases extends AsyncTask<String ,Integer,List<ComVO>> {
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(ComCaseActivity.this);
            progressDialog.setMessage("loading");
            progressDialog.show();
        }

        @Override
        protected List<ComVO>  doInBackground(String... strings) {
            Log.d(TAG,"do IN Background");
            ComDAO dao=new ComDAOImpl();
            comList =dao.getSingleCase();
            return comList;
        }

        @Override
        protected void onPostExecute(List<ComVO> comList) {
            progressDialog.cancel();
            if(comList==null||comList.isEmpty()){
                Util.showToast(ComCaseActivity.this,"case not found");
            }

            showPromotionsView(ComCaseActivity.this,comList);


        }
    }
    public void showPromotionsView(Context context, List<ComVO> comList){
        recyclerView.setAdapter(new ComAdapter(comList));
    }
}
