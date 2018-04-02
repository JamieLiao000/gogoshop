package com.example.liaochieh_yu.gogo2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.Com.ComVO;
import com.example.liaochieh_yu.gogo2.Main.LogInActivity;
import com.example.liaochieh_yu.gogo2.Member.MemberDAO;
import com.example.liaochieh_yu.gogo2.Member.MemberDAOImpl;
import com.example.liaochieh_yu.gogo2.Member.MemberVO;
import com.example.liaochieh_yu.gogo2.Others.Util;
import com.google.gson.Gson;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

/**
 * Created by liaochieh-yu on 2018/3/29.
 */

public class ComCaseDetailActivity extends AppCompatActivity {
    private static final String TAG = "ComCaseDetailActivity";
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    static ComVO theCase;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private FloatingActionButton joinBtn;
    private SharedPreferences pref;
    MemberVO memberVO;
    String memberProfile;
    private final int LOGIN_REQUEST_COMECASE = 3;


    /**
     * The {@link ViewPager} that will host the section contents.
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inner_test);
        View view= getLayoutInflater().inflate(R.layout.inner_test,null);
        theCase=getTheCase();
        Log.d(TAG,theCase.getCom_tit());
        //改大標題
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.caseTitle);
        collapsingToolbarLayout.setTitleEnabled(true);
        collapsingToolbarLayout.setTitle(theCase.getCom_tit());



        //設定開始畫面的fragment primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //解決view Pager不能上下滑動的問題
        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollview);
        scrollView.setFillViewport (true);
        mViewPager = (ViewPager)findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        //加入競標按鈕
        joinBtn=findViewById(R.id.fab_join);
        //需判斷 有無登入
        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences  pref = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                if ((pref.getBoolean("gogoshoplogin",false))) {
                    createBiddingDialog();
                    Log.d("有會員","應該要執行製造Dialog");
                }
                else {
                    //請先登入會員
                    new AlertDialog.Builder(ComCaseDetailActivity.this)
                            .setTitle("參加競標")
                            .setMessage("請先登入會員")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   //導到登入頁面
                                    Intent intent=new Intent(ComCaseDetailActivity.this,LogInActivity.class);
                                    startActivityForResult(intent,LOGIN_REQUEST_COMECASE);
                                }
                            })
                            .setNegativeButton("Canceal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                  return;
                                }
                            })
                            .show();
                    return;
                 }


            }
        });

    }
    private ComVO  getTheCase(){
        Bundle bundle=this.getIntent().getExtras();
        theCase= (ComVO) bundle.getSerializable("theComeCase");
        return theCase;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        //換頁內容
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView=null;
            //判斷換頁載入的layout檔
            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    rootView = inflater.inflate(R.layout.case_detail_fragment1, container, false);
                    findViewsFirst(rootView);

                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.case_detail_fragment2, container, false);
                    findViewsSecond(rootView);

                    break;
                case 3:
                    //留言
                    rootView = inflater.inflate(R.layout.case_detail_fragment1, container, false);

                    break;


            }

            return rootView;
        }
        private void findViewsFirst(View view){
            TextView tvCaseProName= view.findViewById(R.id.tvCaseProName);
            TextView tvCaseOwner= view.findViewById(R.id.tvCaseOwner);
            TextView tvStartDate= view.findViewById(R.id.tvStartDate);
            TextView tvEndDate= view.findViewById(R.id.tvEndDate);
//            TextView tvCaseProNum= view.findViewById(R.id.tvCaseProNum);//商品數量 要從代購案件的表格 去找代購參與名單的數量
            TextView tvCaseLoc= view.findViewById(R.id.tvCaseLoc);
            TextView tvCaseProSize= view.findViewById(R.id.tvCaseProSize);
            TextView tvCaseProColor= view.findViewById(R.id.tvCaseProColor);

            tvCaseProName.setText(theCase.getCom_tit());

//          tvEndDate.setText((theCase.getPur_s_date()).toString()); 等長鄰好時間再打開
//          tvEndDate.setText((theCase.getPur_e_date()).toString());

            tvCaseLoc.setText(theCase.getLmt_lcl());//地區
            tvCaseProSize.setText(theCase.getIt_sz());//大小
            tvCaseProColor.setText(theCase.getIt_col());//顏色
            MemberVO memberVO=null;
            //出任務去拿案主會員的帳號
            try {
                memberVO=new AsyncTask<String, Void, MemberVO>() {
                    @Override
                    protected MemberVO doInBackground(String... strings) {
                        MemberDAO dao=new MemberDAOImpl();
                        return dao.findById(theCase.getMem_id());
                    }
                }.execute().get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
           // Log.d(TAG+"",memberVO.getAcc());
            tvCaseOwner.setText(memberVO.getAcc());
            //任務結束



        }
        private  void findViewsSecond(View view){
            //Inner page2
            ImageView casePic1=view.findViewById(R.id.casePic1);
            ImageView casePic2=view.findViewById(R.id.casePic2);
            Bitmap bitmap1 = BitmapFactory.decodeByteArray(theCase.getCom_pic1(), 0,(theCase.getCom_pic1()).length);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(theCase.getCom_pic2(), 0,(theCase.getCom_pic2()).length);
            casePic1.setImageBitmap(bitmap1);
            casePic2.setImageBitmap(bitmap2);

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            return PlaceholderFragment.newInstance(position + 1); //動態建立fragment
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判斷請求代碼是否相同，確認來源是否正確
        if(requestCode==LOGIN_REQUEST_COMECASE){

            if(resultCode== RESULT_OK){
                SharedPreferences pref=getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
                //有登入成功才能繼續
                String memberProfile = pref.getString("gogoshopMemberProfile", "");
                if (memberProfile.isEmpty()) {
                    Log.d(TAG, "there is no Profile in SharedPreferences.xml !");
                }
                Log.d("我問蒼天!!!!!!",memberProfile);
                Log.d("進入createBiddingDialog","OOOOOOOOOOOOOOOOOOOOOOO");
                createBiddingDialog();
            }
            else if(resultCode== RESULT_CANCELED){

                Log.d(TAG,"返回失敗");

            }

        }


    }
    private void createBiddingDialog(){
        pref=getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
        String memberProfile = pref.getString("gogoshopMemberProfile", "");
        if (memberProfile.isEmpty()) {
            Log.d(TAG, "there is no Profile in SharedPreferences.xml !");
        }
        memberVO = new Gson().fromJson(memberProfile, MemberVO.class);
        if(memberVO.getPar_auth().equals(1)){
            new AlertDialog.Builder(ComCaseDetailActivity.this)
                    .setTitle("競標")
                    .setMessage("開始競標")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })

                    .setNegativeButton("Canceal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .show();
        }else {
            new AlertDialog.Builder(ComCaseDetailActivity.this)
                    .setTitle("競標")
                    .setMessage("權限不足!")
                    .show();
        }




    }


}
