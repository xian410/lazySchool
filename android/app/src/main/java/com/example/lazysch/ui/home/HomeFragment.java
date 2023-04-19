package com.example.lazysch.ui.home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lazysch.R;
import com.example.lazysch.SendRequireActivity;
import com.example.lazysch.databinding.FragmentHomeBinding;

public class  HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    //定义八个服务的图片
    private ImageView express , food , print , carry , get , buy , handle , elsething;
    private TextView to_details;
    SharedPreferences mSharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //进行对应界面跳转 ， 以下很多行
        express = (ImageView) root.findViewById(R.id.expressiv);
        food = (ImageView) root.findViewById(R.id.foodiv);
        print = (ImageView) root.findViewById(R.id.printiv);
        carry = (ImageView) root.findViewById(R.id.carryiv);
        get = (ImageView) root.findViewById(R.id.getiv);
        buy = (ImageView) root.findViewById(R.id.buyiv);
        handle = (ImageView) root.findViewById(R.id.handleiv);
        elsething = (ImageView) root.findViewById(R.id.elsethingiv);
        to_details = (TextView) root.findViewById(R.id.to_details);

        express.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "1");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        food.setOnClickListener(view -> {

            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "2");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        print.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "3");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        carry.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "4");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        get.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "5");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        buy.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "6");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        handle.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "7");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        elsething.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "8");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });

        to_details.setOnClickListener(view -> {
            mSharedPreferences = root.getContext().getSharedPreferences("message_data", MODE_PRIVATE);
            SharedPreferences.Editor message_editor = mSharedPreferences.edit();
            message_editor.putString("message", "8");
            message_editor.commit();

            Intent intent = new Intent(root.getContext(), SendRequireActivity.class);
            startActivity(intent);
        });


        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}