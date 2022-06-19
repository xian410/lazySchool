package com.example.lazysch.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetUtil {
    protected static final String TAG = "NetUtil";
    public static RequestQueue queue;
    public final static int TIME_OUT_NORMAL = 30 * 1000;
    public final static int RETRY_TIME = 0;

    public static final int GET = Request.Method.GET;
    public static final int POST = Request.Method.POST;

    /**
     * 回调接口
     */
    public interface NetListenerT {
        <T> void onResponse(JSONObject response) throws Exception;
    }

    /**
     * 网络请求（POST、GET）
     */
    public static <T> void requestSimple(Context context, final int MethodType, String url, final JSONObject params, final NetListenerT listener) {

        queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("res", response.toString());
                        try {
                            if(response.getString("msg").equals("success")) {
                                listener.onResponse(response);
                            }else {
                                androidx.appcompat.app.AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setTitle("请求失败!").setMessage(response.getString("msg")).show();
                            }
                        }  catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers  = new HashMap<>();
                String token = "";
                SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                token=preferences.getString("token", null);
//                headers .put("token","token-417dddb0-1cde-4b90-9371-67c370c05475");
                headers .put("token",token);
                return headers ;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_NORMAL, RETRY_TIME, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    //图片文件上传工具类，类似web的FormData形式
    public static void uploadFile(Context context,String url, final HashMap<String, Object> params) {
        JSONObject parameters = new JSONObject(params);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonRequest =new JsonObjectRequest(Request.Method.POST, url,parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse (JSONObject response){
                        Log.i("上传成功", "onResponse: "+response);
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse (VolleyError error){
                Log.e("errorUpload", "info: "+error);// 请求失败错误调用
            }
        }
        ){
            private final String fixFormat = "--";
            private final String mFilename = System.currentTimeMillis()+".jpeg";
            private final String splitLine = "\r\n";
            private final String boundary = "joneSplitLineHere"; // 分隔符

            @Override
            public String getBodyContentType() {
                return "multipart/form-data;boundary=" + boundary;
            }
            @Override
            public byte[] getBody() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                // 构建请求体
                try{
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        Bitmap orgBit = (Bitmap) entry.getValue();
                        //压缩并转格式Start
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        if(orgBit.compress(Bitmap.CompressFormat.JPEG,100,out)) {
                            out.flush();
                            out.close();
                        }
                        //压缩并转格式End
                        dos.write(getBytes(fixFormat + boundary + splitLine));
                        dos.write(getBytes("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"; filename=\""+ mFilename + "\"" + splitLine));
                        dos.write(getBytes(splitLine));
                        dos.write(out.toByteArray());
                        dos.write(getBytes(splitLine));
                    }
                    dos.write(getBytes(fixFormat + boundary + fixFormat + splitLine));// 结束标记
                }catch (IOException e){
                    e.printStackTrace();
                }

                return baos.toByteArray();
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String,String> headers  = new HashMap<>();
                String token = "";
                SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                token=preferences.getString("token", null);
//                headers .put("token","token-417dddb0-1cde-4b90-9371-67c370c05475");
                headers .put("token",token);
                return headers ;
            }
        };
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 0, 0));
        jsonRequest.setTag("JoneRequestTag");
        requestQueue.add(jsonRequest);
    }
    public static byte[] getBytes(String str){
        return str.getBytes();
    }

}
