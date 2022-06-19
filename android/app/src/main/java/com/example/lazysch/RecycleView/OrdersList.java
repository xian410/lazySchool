package com.example.lazysch.RecycleView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.lazysch.R;
import com.example.lazysch.entity.PageRequest;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrdersList extends AppCompatActivity {
    private RecyclerView mRvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        mRvMain = (RecyclerView) findViewById(R.id.rv_main);
        try {
            this.getArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setEnabled(false);

        //为Recycler设置滚动监听，只有在顶部时才能使用刷新
        mRvMain.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!mRvMain.canScrollVertically(-1)) {
                    //滑动到顶部
                    swipeRefreshLayout.setEnabled(true);
                }else {
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
                },1000);
            }
        });
    }

    public void getArrayList() throws JSONException {
        List<JSONObject> list = new ArrayList();
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        String url = Apiurls.server+Apiurls.selectCustomerOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(OrdersList.this));
                    mRvMain.setAdapter(new OrdersAdapter(OrdersList.this,list));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(OrdersList.this));
                    mRvMain.setAdapter(new OrdersAdapter(OrdersList.this,list));
                }

            }
        };

        NetUtil.requestSimple(getApplicationContext(), NetUtil.POST, url, params, listenerT);
    }
}