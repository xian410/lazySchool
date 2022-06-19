package com.example.lazysch.RecycleView;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lazysch.R;

import java.util.List;

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {
    private Activity mActivity;
    private Context context;
    private List<LocationBean> list;
    SharedPreferences mSharedPreferences;
    public MapAdapter(Activity activity,Context context) {
        this.mActivity = activity;
        this.context = context;
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(String name, String content);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_map, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        if (list.size() != 0) {
            viewHolder.mMapname.setText(list.get(i).getTitle());
            viewHolder.mMapcontent.setText(list.get(i).getText());
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(list.get(i).getTitle(), list.get(i).getText());
                }
            }
        });
    }

    /**
     * 设置数据集
     *
     * @param data
     */
    public void setData(List<LocationBean> data) {
        this.list = data;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mMapname, mMapcontent;
        private final RelativeLayout relay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relay = itemView.findViewById(R.id.relay);
            mMapname = itemView.findViewById(R.id.map_title);
            mMapcontent = itemView.findViewById(R.id.map_content);
        }
    }
}

