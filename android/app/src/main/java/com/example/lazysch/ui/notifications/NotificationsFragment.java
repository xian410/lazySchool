package com.example.lazysch.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.lazysch.CompleteInformationActivity;
import com.example.lazysch.PerInformationActivity;
import com.example.lazysch.R;
import com.example.lazysch.RecycleView.MessageActivity;
import com.example.lazysch.RecycleView.MessageAdapter;
import com.example.lazysch.RecycleView.OrdersAdapter;
import com.example.lazysch.databinding.FragmentNotificationsBinding;
import com.example.lazysch.entity.PageRequest;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private RecyclerView mRvMain;
    View root = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        mRvMain = (RecyclerView) root.findViewById(R.id.rv_main);
        try {
            this.getArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final SwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setEnabled(false);

        //为Recycler设置滚动监听，只有在顶部时才能使用刷新
        mRvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mRvMain.canScrollVertically(-1)) {
                    //滑动到顶部
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getArrayList();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        Button clear =(Button) root.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setMessage("您确定要清除所有消息记录吗").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONObject params = new JSONObject();
                        String url = Apiurls.server + Apiurls.delMessage;
                        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
                            @Override
                            public void onResponse(JSONObject response) throws JSONException {
                                ToastUtil.showMsg(getContext(),"清除成功");
                                getArrayList();
                            }
                        };
                        NetUtil.requestSimple(root.getContext(), NetUtil.POST, url,params , listenerT);
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


    public void getArrayList() throws JSONException {
        List<JSONObject> list = new ArrayList();
        String url = Apiurls.server + Apiurls.selectMessage;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for (int i = 0; i < data.length(); i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = root.findViewById(R.id.no_data);
                if (list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new MessageAdapter(root.getContext(), list));
                } else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new MessageAdapter(root.getContext(), list));
                }

            }
        };

        NetUtil.requestSimple(root.getContext(), NetUtil.POST, url, PageRequest.getPage(), listenerT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}