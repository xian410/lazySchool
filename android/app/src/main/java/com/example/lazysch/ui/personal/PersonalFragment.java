package com.example.lazysch.ui.personal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lazysch.MainActivity;
import com.example.lazysch.PerInformationActivity;
import com.example.lazysch.R;
import com.example.lazysch.SignInActivity;
import com.example.lazysch.Welcome;
import com.example.lazysch.databinding.FragmentPersonalBinding;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ShowImageUtils;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalFragment extends Fragment {

    private FragmentPersonalBinding binding;
    private ImageButton jump;
    private Button add_money,quit_Button;
    private TextView account;
    View root = null;
    public static PersonalFragment personalFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PersonalViewModel personalViewModel =
                new ViewModelProvider(this).get(PersonalViewModel.class);
        binding = FragmentPersonalBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        account = (TextView) root.findViewById(R.id.account);
        getInfo();
        jump = (ImageButton) root.findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), PerInformationActivity.class);
                startActivity(intent);
            }
        });
        add_money = (Button) root.findViewById(R.id.add_money);
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addMoney(10.0F);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        quit_Button = (Button) root.findViewById(R.id.quit_Button);
        quit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setMessage("您确定要退出登录状态吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        quit();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

            }
        });


        return root;
    }

    public void addMoney(Float money) throws JSONException {
        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.addMoney;
        params.put("money",money);
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                String data = response.getString("data");
                account.setText(data);
                ToastUtil.showMsg(root.getContext(),"修改成功");
            }
        };
        NetUtil.requestSimple(root.getContext(), NetUtil.POST, url, params, listenerT);
    }


        @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getInfo() {
        ImageView headPic = (ImageView) root.findViewById(R.id.headPic);
        TextView name = (TextView) root.findViewById(R.id.name);
        TextView account = (TextView) root.findViewById(R.id.account);

        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.getInformation;

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                JSONObject data = response.getJSONObject("data");
                name.setText(data.getString("name"));
                account.setText(data.getString("account"));
                ShowImageUtils.showImage(root.getContext(),data.getString("headPicUrl"),headPic);
            }
        };
        NetUtil.requestSimple(root.getContext(),NetUtil.POST,url,params,listenerT);
    }
    public void quit(){
        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.logout;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject response) throws JSONException {
                Intent intent = new Intent(root.getContext(), SignInActivity.class);
                startActivity(intent);
            }
        };
        NetUtil.requestSimple(root.getContext(),NetUtil.POST,url,params,listenerT);

    }


    // 网上找的函数 ， 据说找到  AlertDialog的参数  就能结束
//    public void showdialog(View view)
//
//    {
//
//        //Toast.makeText(this,"clickme",Toast.LENGTH_LONG).show();
//
//        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder();
//
//        alertdialogbuilder.setMessage("您确认要退出程序");
//
//        alertdialogbuilder.setPositiveButton("确定", click1);
//
//        alertdialogbuilder.setNegativeButton("取消", click2);
//
//        AlertDialog alertdialog1=alertdialogbuilder.create();
//
//        alertdialog1.show();
//    }
//
//    private PersonalFragment getPersonalFragment() {
//        return personalFragment;
//    }
//
//    private DialogInterface.OnClickListener click1=new DialogInterface.OnClickListener()
//    {
//        @Override
//        public void onClick(DialogInterface arg0,int arg1)
//        {
//            android.os.Process.killProcess(android.os.Process.myPid());
//        }
//    };
//    private DialogInterface.OnClickListener click2=new DialogInterface.OnClickListener()
//    {
//        @Override
//        public void onClick(DialogInterface arg0,int arg1)
//        {
//            arg0.cancel();
//        }
//    };
}