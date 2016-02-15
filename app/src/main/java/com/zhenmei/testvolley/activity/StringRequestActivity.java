package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhenmei on 16/2/15.
 */
public class StringRequestActivity extends Activity {

    private TextView tv_stringrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stringrequest);

        tv_stringrequest = (TextView) findViewById(R.id.tv_stringrequest);

		/*
		 * 一个最基本的HTTP发送与响应的功能,主要就是进行了以下三步操作：
		 * 1.创建一个RequestQueue对象。
		 * 2.创建一个StringRequest对象。
		 * 3.将StringRequest对象添加到RequestQueue里面。
		 */

        // 首先需要获取到一个RequestQueue对象,参数是上下文对象Context context
        RequestQueue mQueue = Volley.newRequestQueue(this);
		/*
		 * 这里拿到的RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求。
		 * RequestQueue内部的设计就是非常合适高并发的，因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，
		 * 这是非常浪费资源的，基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了
		 */
        // 为了要发出一条HTTP请求，我们还需要创建一个StringRequest对象
		/*
		 * StringRequest的构造函数需要传入三个参数
		 */
        // 第一个参数就是目标服务器的URL地址,目标服务器地址我们填写的是百度的首页
        String url = "http://www.baidu.com";
        // 第二个参数是服务器响应成功的回调
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                // 在响应成功的回调里打印出服务器返回的内容
                Log.i("info", response);
                tv_stringrequest.setText(response);
            }
        };
        // 第三个参数是服务器响应失败的回调
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                // 在响应失败的回调里打印出失败的详细信息
                Log.i("info", error.getMessage(), error);
                tv_stringrequest.setText(error.getMessage());
            }
        };
        // StringRequest stringRequest = new StringRequest(url, listener,
        // errorListener);
        // StringRequest中还提供了另外一种四个参数的构造函数，其中第一个参数就是指定请求类型的
        // int method=Method.GET;
        int method = Request.Method.POST;
		/*
		 * StringRequest中并没有提供设置POST参数的方法，但是当发出POST请求的时候，
		 * Volley会尝试调用StringRequest的父类——Request中的getParams()方法来获取POST参数
		 * 只需要在StringRequest的匿名类中重写getParams()方法，在这里设置POST参数就可以了
		 */
        StringRequest stringRequest = new StringRequest(method, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO Auto-generated method stub
                Map<String, String> map = new HashMap<String, String>();
                map.put("params1", "value1");
                map.put("params2", "value2");
                return map;
                // return super.getParams();
            }
        };

        // 最后，将这个StringRequest对象添加到RequestQueue里面
        mQueue.add(stringRequest);

    }
}
