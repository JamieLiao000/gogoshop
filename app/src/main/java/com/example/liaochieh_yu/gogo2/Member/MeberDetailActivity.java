package com.example.liaochieh_yu.gogo2.Member;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.liaochieh_yu.gogo2.MainActivity;
import com.example.liaochieh_yu.gogo2.R;

/**
 * Created by liaochieh-yu on 2018/3/29.
 */

public class MeberDetailActivity extends AppCompatActivity {
    Button btnUpdateProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_detail);
        findViews();

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentUpdate=new Intent(MeberDetailActivity.this,UpdateProfileActivity.class);
                startActivity(intentUpdate);
            }
        });
    }
    private void findViews(){
        btnUpdateProfile=findViewById(R.id.btnUpdateProfile);
    }


}
