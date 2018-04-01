package com.example.liaochieh_yu.gogo2.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.Member.MemberDAO;
import com.example.liaochieh_yu.gogo2.Member.MemberDAOImpl;
import com.example.liaochieh_yu.gogo2.Others.Util;
import com.example.liaochieh_yu.gogo2.R;

import static com.example.liaochieh_yu.gogo2.Others.Util.showToast;

/**
 * Created by liaochieh-yu on 2018/3/26.
 */

public class LogInActivity extends AppCompatActivity{
    public static final int STARTUP_DELAY = 100;
    public static final int ANIM_ITEM_DURATION = 300;
    public static final int EDITTEXT_DELAY = 300;
    public static final int BUTTON_DELAY = 300;
    public static final int VIEW_DELAY = 400;
    private EditText etUser,etPassword;
    private TextView tvMessage;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        findViews();
        animate();
        setResult(RESULT_CANCELED);



    }


    private void animate(){

        ImageView logoImageView = (ImageView) findViewById(R.id.img_logo);
        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        ViewCompat.animate(logoImageView)
                .translationY(-150)
                .setStartDelay(STARTUP_DELAY)
                .setDuration(ANIM_ITEM_DURATION).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator;

            if (v instanceof EditText) {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((EDITTEXT_DELAY * i) + 200)
                        .setDuration(400);
            } else if (v instanceof Button) {
                viewAnimator = ViewCompat.animate(v)
                        .scaleY(1).scaleX(1)
                        .setStartDelay((BUTTON_DELAY * i) + 200)
                        .setDuration(400);
            } else {
                viewAnimator = ViewCompat.animate(v)
                        .translationY(50).alpha(1)
                        .setStartDelay((VIEW_DELAY * i) + 200)
                        .setDuration(800);
            }

            viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
        }
    }
    private boolean isMember(final String account, final String password){
        boolean isMember;
        try {
            isMember= new AsyncTask<String, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(String... strings) {
                    MemberDAO dao=new MemberDAOImpl();
                    return dao.isMember(account,password);
                }
            }.execute().get();//直接拿結果(有這個會員true｜沒有這個會員false)
        } catch (Exception e) {
            isMember=false;

        }
        return isMember;

    }
    private void findViews(){
        etUser=findViewById(R.id.etUser);
        etPassword=findViewById(R.id.etPassword);
        btLogin=findViewById(R.id.login);
        tvMessage=findViewById(R.id.tvMessage);
        /**按下登入的處理**/
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String account=etUser.getText().toString().trim();
               String password=etPassword.getText().toString().trim();
               if(account.length()<=0||password.length()<=0){
                   showMessage(R.string.msg_InvalidUserOrPassword);
                   return;
               }
               if(isMember(account,password)){
                   SharedPreferences pref=getSharedPreferences(Util.PREF_FILE,MODE_PRIVATE);
                   pref.edit().putBoolean("gogoshoplogin",true)/*登入過了*/
                           .putString("gogoshopaccount",account)
                           .putString("gogoshoppassword",password)
                           .apply();
                   Intent intent=new Intent();
                   intent.putExtra("account",account);

                   Log.d("這裡這裡", intent.getStringExtra("account"));
                   setResult(RESULT_OK,intent);
                   Toast.makeText(view.getContext(), "登入成功....", Toast.LENGTH_LONG).show();
                   /**/
                   finish();

               }
               else {
                   showMessage(R.string.msg_InvalidUserOrPassword);

               }
            }
        });
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }


}
