package com.example.lazysch;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.lazysch.RecycleView.MapActivity;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.Common;
import com.example.lazysch.utils.KeyValues;
import com.example.lazysch.utils.MyActivity;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ShowImageUtils;
import com.example.lazysch.utils.TimeUtil;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SendRequireActivity extends MyActivity {

    private TimePickerDialog timeDialog;
    private int hour = 0,minute = 0;
    private TextView tv_time;
    private EditText details,buy_address,receive;
    private TextView money;
    private Spinner isGender;
    private RelativeLayout money_div;
    private String getStringtext;

    private  String typeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_require);

        //上方服务类型的文本框显示
        SharedPreferences mSharedPreferences = getSharedPreferences("message_data" , MODE_PRIVATE);
        getStringtext = mSharedPreferences.getString("message",null);
        getTypeName();

        KeyValues keyValues = new KeyValues();
        details = (EditText) findViewById(R.id.details);
//        buy_address = (TextView) findViewById(R.id.buy_address);
//        receive = (TextView) findViewById(R.id.receive);
        buy_address = (EditText) findViewById(R.id.buy_address);
        receive = (EditText) findViewById(R.id.receive);

        isGender = (Spinner) findViewById(R.id.is_gender);
        money = (TextView) findViewById(R.id.money);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendRequireActivity.this, MapActivity.class);
                startActivityForResult(intent,2);
            }
        });

        buy_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendRequireActivity.this, MapActivity.class);
                startActivityForResult(intent,1);
            }
        });

        Calendar cal = Calendar.getInstance();
        hour = cal.get( Calendar.HOUR_OF_DAY );
        minute = cal.get( Calendar.MINUTE );
        //选择时间
        timeDialog = new TimePickerDialog(this, 3,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                tv_time = (TextView) findViewById(R.id.time);
                String time = Integer.toString(hour) + ":" + Integer.toString(minute);
                tv_time.setText(time);
            }
        },hour,minute,true);



        RelativeLayout timePicker = (RelativeLayout) findViewById(R.id.time_picker_btn);
        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 timeDialog.show();
            }
        });

        //点击下单按钮
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                Map<String,String> map=new HashMap<>();
                String url = Apiurls.server+Apiurls.releaseRequire;

                String details_text = details.getText().toString();
                String buy_address_text = buy_address.getText().toString();
                String receive_text = receive.getText().toString();
                String genderLimit = isGender.getSelectedItem().toString();
                String money_input = money.getText().toString();
                LocalDateTime time = TimeUtil.setTime(hour,minute);

                if(!details_text.isEmpty() && !buy_address_text.isEmpty() && !receive_text.isEmpty()
                        && Common.isNumber(money_input)) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("name",typeName);
                        params.put("detail",details_text);
                        params.put("start",buy_address_text);
                        params.put("destination",receive_text);
                        params.put("deadline",time);
                        params.put("fees",Integer.parseInt(money_input));
                        params.put("remark",genderLimit);
                        params.put("typeId",getStringtext);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject response) throws JSONException, InterruptedException {
                            ToastUtil.showMsg(SendRequireActivity.this,"发布成功");
                            Thread.sleep(2000);
                            finish();
                        }
                    };

                    NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);

                }else {
                    ToastUtil.showMsg(SendRequireActivity.this,"请填写完整信息");
                }

            }
        });

        money_div = (RelativeLayout) findViewById(R.id.money_div);
        money_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3 = new AlertDialog.Builder(SendRequireActivity.this);
                View view1 = LayoutInflater.from(SendRequireActivity.this).inflate(R.layout.dialog_money_input,null);
                EditText input_money = view1.findViewById(R.id.money_text);
                Button confirm = view1.findViewById(R.id.confirm);
                AlertDialog alertDialog = builder3.setView(view1).show();
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = input_money.getText().toString();
                        if(Common.isNumber(str)) {
                            money.setText(str);
                            alertDialog.dismiss();
                        }else {
                            ToastUtil.showMsg(SendRequireActivity.this,"请输入合理金额");
                        }
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == 1) {
                buy_address.setText(data.getExtras().getString("address"));
            }else {
                receive.setText(data.getExtras().getString("address"));
            }
        }
    }

    public void getTypeName() {

        TextView set_message = findViewById(R.id.title);
        JSONObject params = new JSONObject();
        try {
            params.put("typeId",getStringtext);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String url = Apiurls.server+Apiurls.getRequireType;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                typeName = response.getString("data");
                set_message.setText(typeName);//结束
            }
        };
        NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);
    }
}