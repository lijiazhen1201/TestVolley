package com.zhenmei.testvolley.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 全局Application
 */
public class MyApplication extends Application {

    /**
     * Volley的任务队列
     */
    public static RequestQueue mQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 初始化Volley的任务队列
         */
        mQueue = Volley.newRequestQueue(getApplicationContext());
    }

    /**
     * 获得Volley的任务队列
     *
     * @return
     */
    public static RequestQueue getmQueue() {
        return mQueue;
    }
}
