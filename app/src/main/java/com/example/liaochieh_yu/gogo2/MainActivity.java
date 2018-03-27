package com.example.liaochieh_yu.gogo2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.Main.HomeFragment;
import com.example.liaochieh_yu.gogo2.Main.LogInActivity;
import com.example.liaochieh_yu.gogo2.Others.Util;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {
    private  static  final String TAG="MainActivity";
    FragmentManager myFragmentManager;
    FragmentTransaction myFragmentTransaction;
    private final int LOGIN_REQUEST = 0;
    TextView userDrawerTextView;
    boolean login ;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //設定第一次開啟的首頁tab
        myFragmentManager=getSupportFragmentManager();
        myFragmentTransaction = myFragmentManager.beginTransaction();
        myFragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
        //設定浮動按鈕
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        userDrawerTextView = (TextView) header.findViewById(R.id.drawerUser);

        navigationView.setNavigationItemSelectedListener(this);
        //Drawer User
        /**檢查他有沒有登入過**/
        pref=getSharedPreferences(Util.PREF_FILE,MODE_PRIVATE);
        login=pref.getBoolean("login",false);
        if(login){
            userDrawerTextView.setText(pref.getString("account","歡迎光臨"));

        }

    }//onCreate end

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       getMenuInflater().inflate(R.menu.main, menu);

        if ( pref.getBoolean("login",false)) {

            Log.d("測試Main","!!!");
            MenuItem menuItemMem= menu.findItem(R.id.action_member);
            menuItemMem.setVisible(true);
            MenuItem menuItemLogin= menu.findItem(R.id.action_login);
            menuItemLogin.setVisible(false);
        }
        Log.d(TAG,"訪客visited");


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        /*訪客*/
        if (id == R.id.action_login) {
             Intent intent=new Intent(MainActivity.this,LogInActivity.class);
            startActivityForResult(intent,LOGIN_REQUEST);
        }
        /*會員*/
        else if(id==R.id.action_member){
            View menuItemView=findViewById(R.id.action_member);
           PopupMenu popupMenu= new PopupMenu(this,menuItemView);
           popupMenu.inflate(R.menu.mem_popup_menu);
           popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
               @Override
               public boolean onMenuItemClick(MenuItem menuItem) {
                   /*右上角會員視窗popup處理*/
                   switch (menuItem.getItemId()){
                       case R.id.mem_popup_01:
                           Toast.makeText(getParent(),"member01",Toast.LENGTH_SHORT).show();
                           break;
                       case R.id.mem_popup_02:

                           break;
                       case R.id.mem_popup_logout:
                           /*登出處理*/
                           SharedPreferences pref=getSharedPreferences(Util.PREF_FILE,MODE_PRIVATE);
                           pref.edit().putBoolean("login",false).apply();
                           pref.edit().remove("account").apply();  pref.edit().remove("password").apply();

                           Toast.makeText(MainActivity.this, "登出....", Toast.LENGTH_LONG).show();
                           Intent intent=new Intent(MainActivity.this,LogInActivity.class);
                           userDrawerTextView.setText(R.string.drawer_welcome);
                           startActivityForResult(intent,LOGIN_REQUEST);

                           break;
                       default:
                           return MainActivity.super.onOptionsItemSelected(menuItem);
                   }
                   return true;
               }
           });
           popupMenu.show();

        }



        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gogoshop) {
            // Handle the camera action
        } else if (id == R.id.nav_sale) {

        } else if (id == R.id.nav_singlecase) {
            Intent intent=new Intent(MainActivity.this,PurchaseCaseActivity.class);
            startActivityForResult(intent,LOGIN_REQUEST);


        } else if (id == R.id.nav_groupbuying) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




//    @Override                              //(請求代碼 , 結果代碼 , 回傳回來的intent物件)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判斷請求代碼是否相同，確認來源是否正確
        if(requestCode==LOGIN_REQUEST){

            MainActivity.this.invalidateOptionsMenu();//在呼叫onCreateOptionMenu
            if(resultCode== RESULT_OK){
                    userDrawerTextView.setText(data.getStringExtra("account"));
             }
            else if(resultCode== RESULT_CANCELED){

            Log.d(TAG,"返回失敗");

            }

         }


    }

}
