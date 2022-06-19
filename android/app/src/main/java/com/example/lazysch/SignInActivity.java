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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.MyActivity;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends MyActivity {

    private EditText tel;
    private EditText password;
    private CheckBox checkBox;
    private Button forget_password;
    private Button to_sign_up;
    private Button btn_sign_in;
    private Switch aSwitch;
    private TextView identity;
    boolean myIdentity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        checkBox = (CheckBox) findViewById(R.id.checkbox_sign_in_password);
        forget_password = (Button) findViewById(R.id.button_sign_in_forgetPassword);
        to_sign_up = (Button) findViewById(R.id.button_sign_in_to_sign_up);
        btn_sign_in = (Button) findViewById(R.id.Button_sign_in);
        tel = (EditText) findViewById(R.id.edittext_sign_in_tel);
        password = (EditText) findViewById(R.id.edittext_sign_in_password);
        aSwitch = (Switch) findViewById(R.id.switch_sign_in);
        identity = (TextView) findViewById(R.id.identity_sign_in);

        checkBox.setSelected(true);
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

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = Apiurls.server + Apiurls.login;
                Map<String,String> map=new HashMap<>();
                String loginId = tel.getText().toString();
                String psd = password.getText().toString();

                if(!loginId.isEmpty() && !psd.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("loginId", loginId);
                        params.put("password", psd);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject jsonObject) throws JSONException {
                            ToastUtil.showMsg(getApplicationContext(),"登录成功");
                            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=preferences.edit();
                            String token = jsonObject.getJSONObject("data").getString("token");
                            editor.putString("token", token);
                            editor.putBoolean("myIdentity", myIdentity);
//                            System.out.println(token);
                            editor.commit();

                            Intent intent;
                            if (myIdentity) {
                                intent = new Intent(SignInActivity.this, MainDriverActivity.class);
                            } else {
                                intent = new Intent(SignInActivity.this, MainActivity.class);
                            }
                            startActivity(intent);
                        }
                    };
                    NetUtil.requestSimple(getApplicationContext(),NetUtil.POST,url,params,listenerT);
                }else {
                    androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(SignInActivity.this);
                    builder1.setTitle("错误").setMessage("请输入账号或密码").show();
                }

            }
        });

        to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //去注册
                Intent toSign_up = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(toSign_up);
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(SignInActivity.this)
                        .setTitle("忘记密码?")
                        .setMessage("请联系管理员!   QQ:1736728480")
                        .create();
                alertDialog.show();
            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    identity.setText("骑手端");
                    myIdentity = true;
                } else {
                    identity.setText("客户端");
                    myIdentity = false;
                }
            }
        });

    }
}