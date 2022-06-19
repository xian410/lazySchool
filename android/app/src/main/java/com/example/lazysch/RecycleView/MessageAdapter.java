package com.example.lazysch.RecycleView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazysch.R;
import com.example.lazysch.utils.Apiurls;
import com.example.lazysch.utils.NetUtil;
import com.example.lazysch.utils.ToastUtil;
import com.xuexiang.xui.widget.dialog.DialogLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.LinearViewHolder>{
    private Context mContext;
    private List<JSONObject> dataList;

    public MessageAdapter(Context context, List<JSONObject> dataList) throws JSONException {
        this.mContext = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_message,parent,false);
        LinearViewHolder holder = new LinearViewHolder(view);


        //为提交订单设置点击事件
        holder.ll_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                int pos = holder.getAdapterPosition();
                System.out.println("click");
            }
        });

        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.LinearViewHolder holder, int position) {
        JSONObject data = dataList.get(position);
        try {
            String message = data.getString( "message");
            String time = data.getString( "createTime");
            holder.message.setText(message);
            holder.time.setText(time);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView message,time;
        private LinearLayout ll_msg;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            ll_msg = itemView.findViewById(R.id.ll_msg);
        }
    }
}

