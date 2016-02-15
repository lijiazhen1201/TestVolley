package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;

/**
 * Created by zhenmei on 16/2/15.
 */
public class ImageRequestActivity extends Activity {

    private ImageView iv_imagerequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagerequest);

        iv_imagerequest = (ImageView) findViewById(R.id.iv_imagerequest);

        // ImageRequest也是继承自Request的,首先需要获取到一个RequestQueue对象,,参数是上下文对象Context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 接下来自然要去new出一个ImageRequest对象,ImageRequest的构造函数接收六个参数。
        // 第一个参数就是图片的URL地址
        String url = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1205/02/c0/11459868_1335958865509.jpg";
        // 第二个参数是图片请求成功的回调，
        Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                // TODO Auto-generated method stub
                // 把返回的Bitmap参数设置到ImageView中
                iv_imagerequest.setImageBitmap(response);
            }
        };
        // 第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，
        // 指定成0的话就表示不管图片有多大，都不会进行压缩。
        int maxWidth = 0;
        int maxHeight = 0;
		/*
		 * 第五个参数用于指定图片的颜色属性，Bitmap.Config下的几个常量都可以在这里使用，
		 * 其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小， 而RGB_565则表示每个图片像素占据2个字节大小。
		 */
        Bitmap.Config decodeConfig = Bitmap.Config.ARGB_8888;
        // 第六个参数是图片请求失败的回调
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // 当请求失败时在ImageView中显示一张默认图片
                iv_imagerequest.setImageResource(R.mipmap.ic_launcher);
            }
        };

        ImageRequest imageRequest = new ImageRequest(url, listener, maxWidth, maxHeight, decodeConfig, errorListener);

        // 最后将这个ImageRequest对象添加到RequestQueue里就可以了
        mQueue.add(imageRequest);
    }



}
