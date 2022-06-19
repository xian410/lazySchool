package com.example.lazysch;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getInfo();
                Intent mainIntent = new Intent(Welcome.this,SignInActivity.class);
                Welcome.this.startActivity(mainIntent);
                Welcome.this.finish();
            }
        },2000);

    }

    public void getInfo() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.getInformation;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                Boolean myIdentity = preferences.getBoolean("myIdentity", false);
                Intent intent;
                if (myIdentity) {
                    intent = new Intent(Welcome.this, MainDriverActivity.class);
                } else {
                    intent = new Intent(Welcome.this, MainActivity.class);
                }
                Welcome.this.startActivity(intent);
            }
        };
        NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);
    }

}