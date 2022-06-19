package com.example.lazysch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.MyActivity;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends MyActivity {

    private EditText tel;//电话号码
    private Button getCode;//获取验证码

    private EditText code;//验证码
    private EditText password;//密码
    private EditText password2;//确认密码

    private CheckBox checkBox;//密码显示
    private CheckBox checkBox2;//确认密码显示

    private Button btn_sign_up;//注册按钮
    private Button to_sign_in;//去登录

    public static final String TAG = "wf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tel = (EditText) findViewById(R.id.edittext_sign_up_tel);
        getCode = (Button) findViewById(R.id.button_sign_up_getCode);
        code = (EditText) findViewById(R.id.edittext_sign_up_code);
        password = (EditText) findViewById(R.id.edittext_sign_up_password);
        password2 = (EditText) findViewById(R.id.edittext_sing_up_password_confirm);
        checkBox = (CheckBox) findViewById(R.id.checkbox_sign_up_password);
        checkBox2 = (CheckBox) findViewById(R.id.checkbox_sing_up_password_confirm);
        btn_sign_up = (Button) findViewById(R.id.Button_sign_up);
        to_sign_in = (Button) findViewById(R.id.button_sign_up_to_sign_in);
        checkBox.setSelected(true);
        checkBox2.setSelected(true);

        //设置输入密码为不可见状态
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // 设置EditText文本为可见的
                    password.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    checkBox.setSelected(false);
                }else{
                    password.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    checkBox.setSelected(true);
                }
                CharSequence charSequence = password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText,
                            charSequence.length());
                }
            }
        });
        //设置再次输入密码为不可见状态
        password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    // 设置EditText文本为可见的
                    password2.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                    checkBox2.setSelected(false);
                }else{
                    password2.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                    checkBox2.setSelected(true);
                }
                CharSequence charSequence = password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText,
                            charSequence.length());
                }
            }
        });

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginId = tel.getText().toString();
                if (!loginId.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(SignUpActivity.this)
                            .setTitle("验证码")
                            .setMessage("111")
                            .create();
                    alertDialog.show();
                } else {
                    ToastUtil.showMsg(getApplicationContext(),"请输入手机号");
                }
                /*String url = Apiurls.server + Apiurls.login;
                Map<String,String> map=new HashMap<>();

                String loginId = tel.getText().toString();*/

                /*//点击按钮获取验证码
                if(!loginId.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("loginId", loginId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    ToastUtil.showMsg(getApplicationContext(),"请输入账号");
                }*/

                //拿到验证码

            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Apiurls.server + Apiurls.register;
                Map<String,String> map=new HashMap<>();

                String loginId = tel.getText().toString();
                String psd = password.getText().toString();
                String psd2 = password2.getText().toString();
                String code1 = code.getText().toString();
                if (!loginId.isEmpty() && !psd.isEmpty() && !psd2.isEmpty() && !code1.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("loginId", loginId);
                        params.put("password", psd);
                        params.put("password2", psd2);
                        params.put("code", code1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetUtil.NetListenerT listenerT=new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject response) throws JSONException {
                            Log.d("wf", "onResponse: 注册成功");
                            ToastUtil.showMsg(SignUpActivity.this,"注册成功");
                            //存储token
                            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            String token = response.getJSONObject("data").getString("token");
                            editor.putString("token", token);
//                            System.out.println(token);
                            editor.commit();
                            //这里往完善个人信息跳
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    };
                    NetUtil.requestSimple(SignUpActivity.this,NetUtil.POST,url,params,listenerT);

                }else if (code1.isEmpty()) {
                    ToastUtil.showMsg(SignUpActivity.this,"请输入验证码");
                }else if(psd.isEmpty() || psd2.isEmpty()){
                    ToastUtil.showMsg(SignUpActivity.this,"请输入密码");
                }

            }
        });

        to_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去登录
                Intent toSign_in = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(toSign_in);
            }
        });

    }
}