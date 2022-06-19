package com.example.lazysch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.MyActivity;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ShowImageUtils;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CompleteInformationActivity extends MyActivity {

    private EditText nickname;
    private EditText name;
    private Spinner gender;
    private EditText address;
    private Spinner college;
    private EditText studentNumber;
    private Button btn_complete, upload;
    private ImageView headPic;
    private TextView tel;
    private static final int REQUEST_CODE = 0x001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_information);

        nickname = findViewById(R.id.edittext_per_nickname);
        name = findViewById(R.id.edittext_per_name);
        gender = findViewById(R.id.spinner_per_gender);
        address = findViewById(R.id.edittext_per_address);
        college = findViewById(R.id.spinner_per_college);
        tel = findViewById(R.id.edit_telephone);
        studentNumber = findViewById(R.id.edittext_per_student_number);
        btn_complete = findViewById(R.id.Button_complete_info);
        headPic = findViewById(R.id.edit_headPic);
        upload = findViewById(R.id.upload);

        getInfo();
        androidx.appcompat.widget.Toolbar back = findViewById(R.id.edit_per_toolbar);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = Apiurls.server + Apiurls.completeInformation;
                Map<String, String> map = new HashMap<>();

                String nName = nickname.getText().toString();
                String pName = name.getText().toString();
                int sex = (int) gender.getSelectedItemId();
                String add = address.getText().toString();
                String col = Long.toString(college.getSelectedItemId());
                String num = studentNumber.getText().toString();

                if (!nName.isEmpty() && !pName.isEmpty() && !col.isEmpty() && !num.isEmpty()) {
                    JSONObject params = new JSONObject();
                    try {
                        params.put("nickname", nName);
                        params.put("name", pName);
                        params.put("gender", sex);
                        params.put("address", add);
                        params.put("college", col);
                        params.put("studentNumber", num);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                        @Override
                        public <T> void onResponse(JSONObject jsonObject) {
                            ToastUtil.showMsg(getApplicationContext(), "完善成功!");
                            finish();
                        }
                    };
                    NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);

                } else {
                    ToastUtil.showMsg(getApplicationContext(), "信息不完整（*必填）");
                }
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                //处理返回集
                startActivityForResult(intent, 2);
            }
        });
    }

    public void getInfo() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server + Apiurls.getInformation;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                nickname.setText(data.getString("nickname"));

                String gen = data.getString("gender");
                int genderNum = gen.equals("男") ? 1 : 0;
                int genderValue = genderNum;
                gender.setSelection(genderValue, true);

                String collegeValue = data.getString("college");
                college.setSelection(Integer.parseInt(collegeValue), true);

                address.setText(data.getString("address"));
                tel.setText(data.getString("telephone"));
                name.setText(data.getString("name"));
                studentNumber.setText(data.getString("studentNumber"));

                ShowImageUtils.showImage(getApplicationContext(), data.getString("headPicUrl"), headPic);
            }
        };
        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1://拍照处理

            case 2://相册处理
                Uri path = data.getData();
                Bitmap bitmap = null;
                String img = "";
                try {
                    InputStream is = getContentResolver().openInputStream(path);
                    bitmap = BitmapFactory.decodeStream(is);
//                    img =bitmapToBase64(bitmap);
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //利用框架加载图片资源
                Glide.with(getApplicationContext()).load(path).into(headPic);
                HashMap<String, Object> params = new HashMap<>();
                params.put("file",bitmap);
                String url = Apiurls.server+Apiurls.uploadHeadPic;
                NetUtil.uploadFile(CompleteInformationActivity.this,url,params);
                break;
        }
    }

    public String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] bitmapBytes = baos.toByteArray();
//                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
