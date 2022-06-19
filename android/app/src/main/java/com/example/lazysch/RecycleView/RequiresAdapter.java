package com.example.lazysch.RecycleView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazysch.R;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RequiresAdapter extends RecyclerView.Adapter<RequiresAdapter.LinearViewHolder>{
    private Context mContext;
    private List<JSONObject> dataList;
    String details = "";
    Integer type = 0;

    public RequiresAdapter(Context context, List<JSONObject> dataList) throws JSONException {
        this.mContext = context;
        this.dataList = dataList;
    }

    public RequiresAdapter(Context context, List<JSONObject> dataList, Integer type) throws JSONException {
        this.mContext = context;
        this.dataList = dataList;
        this.type = type;
    }
    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_requires,parent,false);
        LinearViewHolder holder = new LinearViewHolder(view);
        //为查看详情设置点击事件
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                System.out.println(pos);
                try {
                    details = dataList.get(pos).getString("detail");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    builder1.setTitle("详细信息").setMessage(details).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //为提交订单设置点击事件
        holder.take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                int pos = holder.getAdapterPosition();
                try {
                    takeOrders(holder,pos);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("您确定要删除此订单吗？！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int pos = holder.getAdapterPosition();
                        try {
                            deleteRequires(view,pos);
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
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull RequiresAdapter.LinearViewHolder holder, int position) {
        JSONObject data = dataList.get(position);
        try {
            holder.pick_address.setText(data.getString("start"));
            holder.send_address.setText(data.getString( "destination"));
            holder.note.setText(data.getString( "remark"));
            holder.money.setText("￥"+data.getString( "fees"));
            String time = data.getString( "deadline");
            String[] arr = time.split("T");
            String time1 = "期望"+arr[0]+"日  "+arr[1]+"送达";
            holder.time.setText(time1);
            this.details = data.getString( "detail");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView more,pick_address,send_address,note,time,money;
        private Button take,delete;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            take = itemView.findViewById(R.id.take_orders);
            more = itemView.findViewById(R.id.more);
            pick_address = itemView.findViewById(R.id.pick_address);
            send_address = itemView.findViewById(R.id.send_address);
            note = itemView.findViewById(R.id.note);
            time = itemView.findViewById(R.id.time);
            money = itemView.findViewById(R.id.money);
            delete = itemView.findViewById(R.id.delete);
            if(type == 0) {
                take.setVisibility(View.VISIBLE);
            }else {
                delete.setVisibility(View.VISIBLE);
            }
        }
    }

    public void takeOrders(RequiresAdapter.LinearViewHolder holder, int position) throws JSONException {

        String url = Apiurls.server+Apiurls.addOrders;
        String requireId = dataList.get(position).getString("requireId");
        String customerId = dataList.get(position).getString("loginId");
        JSONObject params = new JSONObject();
        params.put("requireId",requireId);
        params.put("customerId",customerId);

        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public <T> void onResponse(JSONObject jsonObject) {
                ToastUtil.showMsg(mContext,"抢单成功");
                removeData(position);
            }
        };
        NetUtil.requestSimple(mContext,NetUtil.POST,url,params,listenerT);
    }

    public void deleteRequires(View view,int pos) throws JSONException {
        String requireId = dataList.get(pos).getString("requireId");
        JSONObject params = new JSONObject();
        params.put("requireId", requireId);
        String url = Apiurls.server+Apiurls.deleteRequire;
        NetUtil.NetListenerT listenerT = new NetUtil.NetListenerT() {
            @Override
            public void onResponse(JSONObject response) throws JSONException {
                ToastUtil.showMsg(mContext,"删除成功");
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
