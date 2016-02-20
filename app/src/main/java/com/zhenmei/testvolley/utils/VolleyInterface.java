package com.zhenmei.testvolley.utils;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Volley回调接口
 */
public abstract class VolleyInterface<T> {

    /**
     * 访问成功时的回调
     *
     * @param t
     */
    public abstract void onMySuccessResponse(T t);

    /**
     * 访问失败时的回调
     *
     * @param volleyError
     */
    public abstract void onMyErrorResponse(VolleyError volleyError);

    /**
     * 获得listener
     *
     * @return
     */
    public Response.Listener<T> loadingListener() {
        Response.Listener<T> listener = new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                onMySuccessResponse(t);
            }
        };
        return listener;
    }

    /**
     * 获得errorListener
     *
     * @return
     */
    public Response.ErrorListener loadingErrorListener() {
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                onMyErrorResponse(volleyError);
            }
        };
        return errorListener;
    }
}
