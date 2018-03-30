package com.example.liaochieh_yu.gogo2.Member;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.Main.LogInActivity;
import com.example.liaochieh_yu.gogo2.MainActivity;
import com.example.liaochieh_yu.gogo2.Others.Util;
import com.example.liaochieh_yu.gogo2.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by liaochieh-yu on 2018/3/29.
 */

public class UpdateProfileActivity extends AppCompatActivity {

    EditText etMemAcc, etMemPsw, etMemBirth, etAddr, etPay, etEmail;
    private static final String TAG = "UpdateProfileActivity";
    private int mYear, mMonth, mDay;
    Button btnSubmit;
    private SharedPreferences pref;
    MemberVO memberVO;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);
        findViews();
        fillForms();
    }

    private void findViews() {

        etMemAcc = findViewById(R.id.etMemId);
        etMemPsw = findViewById(R.id.etMemPsw);
        etMemBirth = findViewById(R.id.etMemBirth);
        etAddr = findViewById(R.id.etAddr);
        etPay = findViewById(R.id.etPay);
        etEmail = findViewById(R.id.etEmail);


        btnSubmit = findViewById(R.id.btnSubmit);


        /**1.InputType Date Picker**/
        etMemBirth.setInputType(InputType.TYPE_NULL);
        etPay.setInputType(InputType.TYPE_NULL);
        //使輸入鍵盤不要彈出
        etMemBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(UpdateProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        String format = setDateFormat(year, month, day);
                        etMemBirth.setText(format);
                    }

                }, mYear, mMonth, mDay).show();
            }

        });
        /*InputType Date Picker END*/
        /**2.Pay PopUpMenu**/
        etPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(UpdateProfileActivity.this, view);
                popupMenu.inflate(R.menu.pay_popup_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                   /*右上角會員視窗popup處理*/
                        switch (menuItem.getItemId()) {
                            case R.id.pay_item_01:
                                etPay.setText(menuItem.getTitle());
                                break;
                            case R.id.pay_item_02:
                                etPay.setText(menuItem.getTitle());
                                break;
                            case R.id.pay_item_03:
                                etPay.setText(menuItem.getTitle());

                                break;
                            default:
                                return UpdateProfileActivity.super.onOptionsItemSelected(menuItem);
                        }
                        return true;
                    }
                });
                popupMenu.show();

            }
        });


    }

    private void fillForms() {
        pref = getSharedPreferences(Util.PREF_FILE, MODE_PRIVATE);
        String memberProfile = pref.getString("MemberProfile", "");
        if (memberProfile.isEmpty()) {
            Log.d(TAG, "there is no Profile in SharedPreferences.xml !");
        }
        memberVO = new Gson().fromJson(memberProfile, MemberVO.class);

        etMemAcc.setText(memberVO.getAcc());
        etMemPsw.setText(memberVO.getPsw());
        etMemBirth.setText((memberVO.getBir_dt().toString()));
        etAddr.setText(memberVO.getAddress());
        etPay.setText(memberVO.getMem_pay());
        etEmail.setText(memberVO.getMem_email());
    }

    public void onSubmitClick(View view) {
        String memAcc = etMemAcc.getText().toString().trim();
        String memPsw = etMemPsw.getText().toString().trim();
        String memBirth = etMemBirth.getText().toString().trim();
        String addr = etAddr.getText().toString().trim();
        String pay = etPay.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        Date birthDate = null;
        java.sql.Date sqlDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            birthDate = format.parse(memBirth);
            sqlDate = new java.sql.Date(birthDate.getTime()); //把java date 改成 sql date
        } catch (ParseException e) {
            Log.d(TAG, "Date格式錯誤.");
        }


        boolean isInputValid = true;

        String message = "";

        if (memPsw.isEmpty()) {
            message ="密碼不得為空值";
            isInputValid = false;
        }
        if (addr.isEmpty()) {
            message ="地址不得為空值";
            isInputValid = false;
        }
        if (email.isEmpty()) {
            message ="E-mail不得為空值";
            isInputValid = false;
        }



        if (isInputValid) {
            boolean isUpdated = false;
            memberVO.setAcc(memAcc);
            memberVO.setPsw(memPsw);
            memberVO.setBir_dt(sqlDate);
            memberVO.setAddress(addr);
            memberVO.setMem_pay(pay);
            memberVO.setMem_email(email);
            try {//送修改資料出去
                isUpdated = new AsyncTask<Void, Void, Boolean>() {
                    @Override
                    protected Boolean doInBackground(Void... voids) {
                        return new MemberDAOImpl().update(memberVO);
                    }
                }.execute().get();

            } catch (Exception e) {
                Toast.makeText(UpdateProfileActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isUpdated) {
                Toast.makeText(UpdateProfileActivity.this, "更新失敗", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(UpdateProfileActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                //更新成功後,修改偏好設定檔裡的東西!
                Gson gson = new Gson();
                String memJson = gson.toJson(memberVO);
                pref.edit().putString("MemberProfile", memJson).commit();
                finish();


            }

        }
        else {
            /**輸入有錯誤在這邊警告**/
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(UpdateProfileActivity.this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(UpdateProfileActivity.this);
            }
            builder.setTitle("輸入有錯誤")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(R.drawable.icons_eye)
                    .show();
            Log.d(TAG,"再檢查一次");
            return;
        }



    }

    /**
     * 設定日期顯示格式
     **/
    private String setDateFormat(int year, int monthOfYear, int dayOfMonth) {
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }


}
