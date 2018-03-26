package com.example.liaochieh_yu.gogo2.Others;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by liaochieh-yu on 2018/3/26.
 */

public class Util {
    // 模擬器連Tomcat
    public static String URL = "http://10.0.2.2:8081/BookStoreServer_ex/";
    // 偏好設定檔案名稱
    public final static String PREF_FILE = "preference";
    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}