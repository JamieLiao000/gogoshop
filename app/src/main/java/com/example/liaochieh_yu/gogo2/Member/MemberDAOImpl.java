package com.example.liaochieh_yu.gogo2.Member;

import android.util.Log;

import com.example.liaochieh_yu.gogo2.Others.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by liaochieh-yu on 2018/3/26.
 */

public class MemberDAOImpl implements MemberDAO{
    private final static String TAG = "MemberDAOImpl";


    @Override
    public boolean isMember(String account, String password) {
        String urlString = Util.URL + "MemberServlet";

        DataOutputStream dos = null;
        HttpURLConnection con = null;
        StringBuilder strBuIn = null;//讀進來的

        try {
            URL url=new URL(urlString);
            con= (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.connect();
            /********************************  送出去  ***********************************/
            dos=new DataOutputStream(con.getOutputStream());
            String outStr="action=isMember&userId=" + account + "&password=" + password;
            dos.writeBytes(outStr);
            dos.flush();
            /********************************  傳回來  ***********************************/
            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                strBuIn=new StringBuilder();
                String strLine=null;
                while ((strLine = br.readLine()) != null) {
                    strBuIn.append(strLine);
                }
                br.close();
            }else {
                Log.e(TAG, "statusCode = " + statusCode);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
        return Boolean.valueOf(strBuIn.toString());

    }
}
