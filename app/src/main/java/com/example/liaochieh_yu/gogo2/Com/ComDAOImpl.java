package com.example.liaochieh_yu.gogo2.Com;

import android.util.Log;

import com.example.liaochieh_yu.gogo2.Others.Util;
import com.example.liaochieh_yu.gogo2.Promotion.PromotionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by liaochieh-yu on 2018/4/1.
 */

public class ComDAOImpl implements ComDAO{
    private static final String TAG = "ComDAOImpl";

    @Override
    public List<ComVO> getGroupBuyCase() {
        String urlString=Util.URL+"ComServlet";
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
            String content = "action=getGroupBuyCase&imageSize=200";/*（請求參數的組合）*/
            dos.writeBytes(content);
            dos.flush();

            int statusCode=con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                Log.d(TAG,"有200嗎！！！");
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
            Log.d(TAG,strBuIn.toString());
            Type listType= new TypeToken<List<ComVO>>(){}.getType();
            return  gson.fromJson(strBuIn.toString(),listType);//先把strBu 轉成字串[{},{},{}]再把它轉換回 裝滿promotion的集合

        }
        return null;
    }
    @Override
    public List<ComVO> getSingleCase() {
        String urlString= Util.URL+"ComServlet";
        DataOutputStream dos = null;
        HttpURLConnection con = null;
        StringBuilder strBuIn = null;
        URL url = null;
        try {
            url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setUseCaches(false);
            con.connect();

            dos=new DataOutputStream(con.getOutputStream());
            String content = "action=getSingleCase&imageSize=200";/*（請求參數的組合）*/
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
            e.printStackTrace();
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
            // decode JSON to List<ComVo>
            Gson gson=new Gson();
            Type listType= new TypeToken<List<ComVO>>(){}.getType();
            return  gson.fromJson(strBuIn.toString(),listType);//先把strBu 轉成字串[{},{},{}]再把它轉換回 裝滿com的集合

        }
        return null;

    }
}
