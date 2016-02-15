package com.zhenmei.testvolley.view;

/**
 * Created by zhenmei on 16/2/15.
 */

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

//GsonRequest是继承自Request类的，并且同样提供了两个构造函数
public class GsonRequest<T> extends Request<T> {

    private final Listener<T> mListener;

    private Gson mGson;

    private Class<T> mClass;

    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mGson = new Gson();
        mClass = clazz;
        mListener = listener;
    }

    public GsonRequest(String url, Class<T> clazz, Listener<T> listener, ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    //在parseNetworkResponse()方法中，
    //先是将服务器响应的数据解析出来，然后通过调用Gson的fromJson方法将数据组装成对象。
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mGson.fromJson(jsonString, mClass), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    //在deliverResponse方法中仍然是将最终的数据进行回调
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}

