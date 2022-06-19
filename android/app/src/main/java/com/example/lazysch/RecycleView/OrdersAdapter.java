package com.example.lazysch.RecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazysch.R;
import com.example.lazysch.SendRequireActivity;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.Common;
import com.example.lazysch.utils.KeyValues;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ShowImageUtils;
import com.example.lazysch.utils.ToastUtil;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.LinearViewHolder>{
    private Context mContext;
    private List<JSONObject> dataList;
    private Integer type = 0;
    String details = "",driverId ="";

    public OrdersAdapter(Context context, List<JSONObject> dataList) throws JSONException {
        this.mContext = context;
        this.dataList = dataList;
    }

    public OrdersAdapter(Context context, List<JSONObject> dataList,int type) throws JSONException {
        this.mContext = context;
        this.dataList = dataList;
        this.type = type;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_orders,parent,false);
        LinearViewHolder holder = new LinearViewHolder(view);

        //为查看详情设置点击事件
        holder.check_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                try {
                    details = dataList.get(pos).getString("detail");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                builder1.setTitle("详细信息").setMessage(details).show();
            }
        });

        holder.check_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                showDrivers(holder,pos);
            }
        });
        holder.cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if(type == 1) {
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
                    View view1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_money_input,null);
                    EditText input_money = view1.findViewById(R.id.money_text);
                    Button confirm = view1.findViewById(R.id.confirm);
                    TextView title = view1.findViewById(R.id.title);
                    title.setText("如果金额有变化，可以修改哦");
                    AlertDialog alertDialog = builder3.setView(view1).show();
                    try {
                        String fees = dataList.get(pos).getString("fees");
                        input_money.setText(fees);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String str = input_money.getText().toString();
                            if(Common.isNumber(str)) {
                                try {
                                    payOrders(view,pos, (int) Float.parseFloat(str));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                alertDialog.dismiss();
                            }else {
                                ToastUtil.showMsg(mContext.getApplicationContext(), "请输入合理金额");
                            }
                        }
                    });
                }else if(type == 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("您确定要取消此订单状态吗？超过二十分钟后不得取消哦！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                cancelOrders(view,pos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("您确定要删除此订单吗？！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                deleteOrders(view,pos);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    }).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        JSONObject data = dataList.get(position);
        try {
            String id = data.getString( "orderId").substring(32).toUpperCase(Locale.ROOT);
            holder.order_id.setText("编号："+id);
            holder.pick_address.setText(data.getString("start"));
            holder.send_address.setText(data.getString( "destination"));
            String time = data.getString( "deadline");
            String[] arr = time.split("T");
            String time1 = arr[0]+"日  "+arr[1];
            holder.deadline.setText(time1);
            holder.phone.setText(data.getString( "driverId"));
            holder.title.setText("帮我买-"+data.getString( "requireName"));
            this.details = data.getString( "detail");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        System.out.println(dataList);
        return dataList == null ? 0 : dataList.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView order_id,deadline,title,pick_address,send_address,phone;
        private Button check_orders,check_driver,cancel_order;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            order_id = itemView.findViewById(R.id.order_id);
            deadline = itemView.findViewById(R.id.deadline);
            pick_address = itemView.findViewById(R.id.pick_address);
            send_address = itemView.findViewById(R.id.send_address);
            title = itemView.findViewById(R.id.title);
            phone = itemView.findViewById(R.id.phone);
            check_orders = itemView.findViewById(R.id.check_orders);
            check_driver = itemView.findViewById(R.id.check_driver);
            cancel_order = itemView.findViewById(R.id.cancel_order);
            switch (type) {
                case 0:break;
                case 1:cancel_order.setVisibility(View.VISIBLE);cancel_order.setText("去支付");break;
                case 2:cancel_order.setVisibility(View.VISIBLE);cancel_order.setText("取消订单");break;
                case 3:cancel_order.setVisibility(View.VISIBLE);break;
                default:;
            }
        }
    }

    void showDrivers(OrdersAdapter.LinearViewHolder holder, int position) {
        KeyValues keyValues = new KeyValues();
        AlertDialog.Builder builder3 = new AlertDialog.Builder(mContext);
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.dialog_show_drivers_informatoin,null);
        AlertDialog alertDialog = builder3.setView(view1).show();
        Button confirm = view1.findViewById(R.id.confirm);
        JSONObject data = dataList.get(position);
        try {
            ImageView headPic = view1.findViewById(R.id.headPic);
            TextView name = view1.findViewById(R.id.name);
            TextView gender = view1.findViewById(R.id.gender);
            TextView telephone = view1.findViewById(R.id.telephone);
            TextView college = view1.findViewById(R.id.college);

            name.setText(data.getString("driverName"));
            if (data.getString("driverGender").equals("0")) {
                gender.setText("女");
            }else {
                gender.setText("男");
            }
            college.setText(keyValues.getCollege(data.getString("driverColl")));
            telephone.setText(data.getString("driverTel"));
            ShowImageUtils.showImage(mContext,data.getString("driverHeadPic"),headPic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    public void payOrders(View view,int pos,int money) throws JSONException {
        String customerId = dataList.get(pos).getString("customerId");
        String driverId = dataList.get(pos).getString("driverId");
        JSONObject params = new JSONObject();
        JSONObject params1 = new JSONObject();
        params.put("money", money);
        params.put("customerId", customerId);
        params.put("driverId", driverId);
        String url = Apiurls.server+Apiurls.modifyAccount;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ToastUtil.showMsg(mContext,"支付成功");
                modOrders(view,pos);
            }
        };
        NetUtil.requestSimple(mContext, NetUtil.POST, url, params, listenerT);
    }
    public void cancelOrders(View view,int pos) throws JSONException {
        String orderId = dataList.get(pos).getString("orderId");
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        String url = Apiurls.server+Apiurls.cancelOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ToastUtil.showMsg(mContext,"取消成功");
                removeData(pos);
            }
        };
        NetUtil.requestSimple(mContext, NetUtil.POST, url, params, listenerT);
    }
    public void deleteOrders(View view,int pos) throws JSONException {
        String orderId = dataList.get(pos).getString("orderId");
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        String url = Apiurls.server+Apiurls.delOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ToastUtil.showMsg(mContext,"删除成功");
                removeData(pos);
            }
        };
        NetUtil.requestSimple(mContext, NetUtil.POST, url, params, listenerT);
    }
    public void modOrders(View view,int pos) throws JSONException {
        String orderId = dataList.get(pos).getString("orderId");
        String customerId = dataList.get(pos).getString("customerId");
        JSONObject params = new JSONObject();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        params.put("status", "2");
        String url = Apiurls.server+Apiurls.modifyOrders;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ToastUtil.showMsg(mContext,"修改成功");
                removeData(pos);
            }
        };
        NetUtil.requestSimple(mContext, NetUtil.POST, url, params, listenerT);
    }
    //  删除数据
    public void removeData(int position) {
        dataList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();//为了数据同步防止错位
    }


}
