package com.zhenmei.testvolley.activity;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;
import com.zhenmei.testvolley.bean.Joke;
import com.zhenmei.testvolley.view.GsonRequest;

import java.util.List;

public class GsonRequestActivity extends Activity {

    private TextView tv_gsonrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsonrequest);

        tv_gsonrequest = (TextView) findViewById(R.id.tv_gsonrequest);

        // 首先需要获取到一个RequestQueue对象,参数是上下文对象Context context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 新建GsonRequest实例
        // 第一个参数是网址
        String url = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do";
        // 解析的实体类
        Class<Joke> clazz = Joke.class;
        // 访问成功时的回调
        Listener<Joke> listener = new Listener<Joke>() {
            // onResponse()方法的回调中直接返回了一个Weather对象，
            // 我们通过它就可以得到实体bean对象，接着就能从中取出JSON中的相关数据
            @Override
            public void onResponse(Joke joke) {
                // TODO Auto-generated method stub
                List<Joke.DetailEntity> detail = joke.getDetail();
                for (Joke.DetailEntity d : detail) {
                    tv_gsonrequest.setText(tv_gsonrequest.getText().toString() + "\n" + d.toString());
                }
            }
        };
        // 访问失败时的回调
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("info", error.getMessage(), error);
            }
        };
        GsonRequest<Joke> gsonRequest = new GsonRequest<Joke>(url, clazz, listener, errorListener);
        //// 最后，将这个GsonRequest对象添加到RequestQueue里面
        mQueue.add(gsonRequest);
    }
}

