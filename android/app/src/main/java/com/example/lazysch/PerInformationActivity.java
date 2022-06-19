package com.example.lazysch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.KeyValues;
import com.example.lazysch.utils.MyActivity;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ShowImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class PerInformationActivity extends MyActivity {

    private ImageView headPic;
    private TextView name;
    private TextView gender;
    private TextView telephone;
    private TextView address;
    private TextView college;
    private Button edit;
    private TextView nickname;
    private TextView num;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_information);
        getInfo();

        androidx.appcompat.widget.Toolbar back = findViewById(R.id.back);
        edit =(Button) findViewById(R.id.per_info_mod_information);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerInformationActivity.this, CompleteInformationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getInfo() {
        headPic = (ImageView) findViewById(R.id.headPic);
        name = (TextView) findViewById(R.id.per_name);
        nickname = (TextView) findViewById(R.id.per_nickname);
        gender = (TextView) findViewById(R.id.gender);
        telephone = (TextView) findViewById(R.id.telephone);
        address = (TextView) findViewById(R.id.address);
        college = (TextView) findViewById(R.id.college);
        name = (TextView) findViewById(R.id.per_name);
        num = (TextView) findViewById(R.id.student_number);

        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.getInformation;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                name.setText(data.getString("name"));
                gender.setText(data.getString("gender"));
                address.setText(data.getString("address"));
                KeyValues keyValues = new KeyValues();
                college.setText(keyValues.getCollege(data.getString("college")));
                telephone.setText(data.getString("telephone"));
                nickname.setText(data.getString("nickname"));
                num.setText(data.getString("studentNumber"));
                ShowImageUtils.showImage(getApplicationContext(),data.getString("headPicUrl"),headPic);
            }
        };
        NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);
    }

}
