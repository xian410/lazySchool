package com.example.lazysch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lazysch.RecycleView.OrdersList;
import com.example.lazysch.entity.PageRequest;
import com.example.lazysch.ui.dashboard.DashboardFragment;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.RecycleView.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FragmentListPage extends Fragment {

    private static final String ARG_TEXT = "";
    private String index;
    private RecyclerView mRvMain;
    View rootView;

    public FragmentListPage() {
    }

    public static FragmentListPage newInstance(String param1) {
        FragmentListPage fragment = new FragmentListPage();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            index = getArguments().getString(ARG_TEXT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.activity_orders_list,container,false);

        }
        mRvMain = (RecyclerView) rootView.findViewById(R.id.rv_main);
        try {
            this.getArrayList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final SwipeRefreshLayout swipeRefreshLayout = rootView.findViewById(R.id.swipeLayout);
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
        initView();
        return  rootView;
    }

    public void getArrayList() throws JSONException {
        List<JSONObject> list = new ArrayList();
        switch (index) {
            case "1":ListToPay();break;
            case "2": ListTodoRequires();break;
            case "3": ListGoingOrders();break;
            case "4": ListCompletedOrders();break;
            case "5": ListCanceledRequires();break;
            default:ListToPay();
        }
    }
    private void initView(){

    }

    //获取待支付的列表
    public void ListToPay() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        params.put("status","1");
        String url = Apiurls.server+Apiurls.selectCustomerOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = rootView.findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(),list,1));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(), list,1));
                }
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }

    //获取未接单的列表
    public void ListTodoRequires() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        String url = Apiurls.server+Apiurls.selectMyRequires;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = rootView.findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new RequiresAdapter(getContext(),list,1));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new RequiresAdapter(getContext(), list,1));
                }
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }

    //获取进行中的订单
    public void ListGoingOrders() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        params.put("status", "0");
        String url = Apiurls.server+Apiurls.selectCustomerOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = rootView.findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(),list,2));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(), list,2));
                }
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }

    //获取完成的订单的列表
    public void ListCompletedOrders() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        params.put("status", "2");
        String url = Apiurls.server+Apiurls.selectCustomerOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = rootView.findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(),list,3));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(), list,3));
                }
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }

    //获取已取消的需求
    public void ListCanceledRequires() throws JSONException {
        JSONObject params = new JSONObject();
        params.put("pageRequest", PageRequest.getPage());
        String url = Apiurls.server+Apiurls.selectMyRequires;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                List<JSONObject> list = new ArrayList();
                JSONArray data = response.getJSONObject("data").getJSONArray("records");
                for(int i = 0;i < data.length();i++) {
                    list.add(data.getJSONObject(i));
                }
                RelativeLayout relativeLayout = rootView.findViewById(R.id.no_data);
                if(list.size() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(),list));
                }else {
                    relativeLayout.setVisibility(View.INVISIBLE);
                    mRvMain.setLayoutManager(new LinearLayoutManager(getContext()));
                    mRvMain.setAdapter(new OrdersAdapter(getContext(), list));
                }
            }
        };
        NetUtil.requestSimple(getContext(), NetUtil.POST, url, params, listenerT);
    }
}