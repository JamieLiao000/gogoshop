package com.example.liaochieh_yu.gogo2.Others;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.liaochieh_yu.gogo2.Member.MemberDAOImpl;
import com.example.liaochieh_yu.gogo2.Member.MemberVO;
import com.google.gson.Gson;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by liaochieh-yu on 2018/3/26.
 */

public class Util {
  public static String URL = "http://10.0.2.2:8081/BA107_G4/";

    // 模擬器連Tomcat
//  public static String URL = "http://192.168.197.0:8081/BA107_G4/";

    // 偏好設定檔案名稱
    public final static String PREF_FILE = "preference";
    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
