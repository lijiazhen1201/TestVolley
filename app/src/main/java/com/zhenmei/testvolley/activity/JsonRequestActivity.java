package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;

import org.json.JSONObject;

/**
 * Created by zhenmei on 16/2/15.
 */
public class JsonRequestActivity extends Activity {


    private TextView tv_jsonrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonrequest);

        tv_jsonrequest = (TextView) findViewById(R.id.tv_jsonrequest);

		/*
		 * JsonRequest的用法。类似于StringRequest，JsonRequest也是继承自Request类的，
		 * 不过由于JsonRequest是一个抽象类，因此我们无法直接创建它的实例，
		 * 那么只能从它的子类入手了。JsonRequest有两个直接的子类
		 * ，JsonObjectRequest和JsonArrayRequest，从名字上你应该能就看出它们的区别
		 * 一个是用于请求一段JSON数据的，一个是用于请求一段JSON数组的。
		 */
        // 首先需要获取到一个RequestQueue对象,参数是上下文对象Context context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 它们的用法也基本上没有什么特殊之处，先new出一个JsonObjectRequest对象
        // 第一个参数就是目标服务器的URL地址,这是中国天气网提供的一个查询天气信息的接口
        // String url = "http://m.weather.com.cn/data/101010100.html";
        String url = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do";
        // 第二个参数是JSONObject对象
        JSONObject jsonRequest = null;
        // 第三个参数是服务器响应成功的回调
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                Log.i("info", response.toString());
                tv_jsonrequest.setText(response.toString());
            }
        };
        // 第四个参数是服务器响应失败的回调
        Response.ErrorListener errorListener = new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("info", error.getMessage(), error);
                tv_jsonrequest.setText(error.getMessage());
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonRequest, listener, errorListener);
        // 同样还提供了另外一种五个参数的构造函数，第一个参数就是指定请求类型的
        // int method = Method.GET;
        // int method = Method.POST;
        // JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method,
        // url, jsonRequest, listener, errorListener);

        // 最后再将这个JsonObjectRequest对象添加到RequestQueue里就可以了
        mQueue.add(jsonObjectRequest);
    }


}
