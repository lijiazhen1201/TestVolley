package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;

/**
 * Created by zhenmei on 16/2/15.
 */
public class NetworkImageViewActivity extends Activity {

    private NetworkImageView iv_networkimageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_networkimageview);

		/*
		 * Volley还提供了第三种方式来加载网络图片，即使用NetworkImageView。不同于以上两种方式，
		 * NetworkImageView是一个自定义控制，它是继承自ImageView的，具备ImageView控件的所有功能，
		 * 并且在原生的基础之上加入了加载网络图片的功能。NetworkImageView控件的用法要比前两种方式更加简单，
		 * 大致可以分为以下五步：
		 * 1. 创建一个RequestQueue对象。
		 * 2. 创建一个ImageLoader对象。
		 * 3.在布局文件中添加一个NetworkImageView控件。
		 * 4. 在代码中获取该控件的实例。
		 * 5. 设置要加载的图片地址。
		 */

        // 第一第二步和ImageLoader的用法是完全一样的
        // 首先需要获取到一个RequestQueue对象,参数是上下文对象Context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 建一个ImageLoader对象
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                // TODO Auto-generated method stub
            }

            @Override
            public Bitmap getBitmap(String url) {
                // TODO Auto-generated method stub
                return null;
            }
        });

        // 在布局文件中添加一个NetworkImageView控件,在Activity获取到这个控件的实例
        iv_networkimageview = (NetworkImageView) findViewById(R.id.iv_networkimageview);
		/*
		 * 得到了NetworkImageView控件的实例之后，我们可以调用
		 * setDefaultImageResId()方法设置加载中显示的图片;
		 * setErrorImageResId()方法加载失败时显示的图片 ;
		 * setImageUrl()方法设置目标图片的URL地址;
		 */
        iv_networkimageview.setDefaultImageResId(R.mipmap.ic_launcher);
        iv_networkimageview.setErrorImageResId(R.mipmap.ic_launcher);
        // setImageUrl()方法接收两个参数，第一个参数用于指定图片的URL地址，第二个参数则是前面创建好的ImageLoader对象
        String url = "http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1205/02/c0/11459868_1335958865509.jpg";
        iv_networkimageview.setImageUrl(url, imageLoader);
    }
}
