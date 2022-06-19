package com.example.lazysch.ui.takeRequires;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lazysch.R;
import com.example.lazysch.RecycleView.RequiresList;
import com.example.lazysch.databinding.ActivityToTakeRequiresBinding;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class TakeRequiresFragment extends Fragment {

    private TextView earn_money,size,nums,rate;
    private Button orders,notifies,digital;
    private CardView to_take;
    private ActivityToTakeRequiresBinding binding;
    private View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TakeRequiresViewModel takeRequiresViewModel =
                new ViewModelProvider(this).get(TakeRequiresViewModel.class);

        binding = ActivityToTakeRequiresBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        to_take = root.findViewById(R.id.to_take);
        to_take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(),RequiresList.class);
                startActivity(intent);
            }
        });
        earn_money = root.findViewById(R.id.earn_money);
        nums = root.findViewById(R.id.orders_num);
        init();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void init() {
        JSONObject params = new JSONObject();
        String url = Apiurls.server+Apiurls.ordersCount;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                nums.setText(response.getString("data"));
            }
        };
        NetUtil.requestSimple(root.getContext(), NetUtil.POST, url, params, listenerT);
        String url1 = Apiurls.server+Apiurls.profile;
        NetUtil.NetListenerT listenerT1 = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                earn_money.setText(response.getString("data"));
            }
        };
        NetUtil.requestSimple(root.getContext(), NetUtil.POST, url1, params, listenerT1);
    }


}