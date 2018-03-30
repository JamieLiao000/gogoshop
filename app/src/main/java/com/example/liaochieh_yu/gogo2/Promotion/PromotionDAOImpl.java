package com.example.liaochieh_yu.gogo2.Promotion;

import android.util.Log;

import com.example.liaochieh_yu.gogo2.Others.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by liaochieh-yu on 2018/3/28.
 */

public class PromotionDAOImpl implements PromotionDAO{
    private static final String TAG = "PromotionDAOImpl";

    @Override
    public List<PromotionVO> getAll() {
        String urlString=Util.URL+"PromotionServlet";
        DataOutputStream dos = null;
        HttpURLConnection con = null;
        StringBuilder strBuIn = null;

        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.connect();

            dos=new DataOutputStream(con.getOutputStream());
            String content = "action=getAll&imageSize=500";/*（請求參數的組合）*/
            dos.writeBytes(content);
            dos.flush();

            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                strBuIn=new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String strLine = null;
                while((strLine = br.readLine()) != null){
                    strBuIn.append(strLine);

                }br.close();
            } else {
                Log.e(TAG, "statusCode = " + statusCode);
            }

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }finally {
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
        }//finally end
        if(strBuIn!=null){
            // decode JSON to List<PromotionVo>
            Gson gson=new Gson();
           Type listType= new TypeToken<List<PromotionVO>>(){}.getType();
           return  gson.fromJson(strBuIn.toString(),listType);//先把strBu 轉成字串[{},{},{}]再把它轉換回 裝滿promotion的集合

        }
        return null;
    }
}
