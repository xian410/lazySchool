package com.example.lazysch.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.lazysch.R;

public class ShowImageUtils {
    private static ShowImageUtils showImageUtils ;
    private static int errorImg = R.color.white;


    public static ShowImageUtils getInstance(){
        if (showImageUtils == null){
            showImageUtils = new ShowImageUtils();
        }
        return showImageUtils;
    }

    public static void showImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(Apiurls.server+url).error(errorImg).into(imageView);
    }


    //加载本地图片
    public void showImage(Context context, int url, ImageView imageView){
        Glide.with(context).load(url).error(errorImg).into(imageView);
    }

    //加载GIF图片
    public void showGif(Context context,String url,ImageView imageView){
        Glide.with(context).asGif().load(url).error(errorImg).into(imageView);
    }

}
