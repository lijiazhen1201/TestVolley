package com.zhenmei.testvolley.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.zhenmei.testvolley.app.MyApplication;

import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

/**
 * Volley访问网络工具类
 */
public class VolleyUtils {

    /**
     * Volley通过get请求，获得String数据
     *
     * @param url             请求地址
     * @param tag             tag标签
     * @param volleyInterface 回调接口
     */
    public static void requestGetString(String url, String tag,
                                        VolleyInterface<String> volleyInterface) {
        MyApplication.getmQueue().cancelAll(tag);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                volleyInterface.loadingListener(),
                volleyInterface.loadingErrorListener());
        request.setTag(tag);
        MyApplication.getmQueue().add(request);
        MyApplication.getmQueue().start();
    }

    /**
     * Volley通过post请求，获得String数据
     *
     * @param url             请求地址
     * @param tag             tag标签
     * @param volleyInterface 回调接口
     * @param map             请求参数
     */
    public static void requestPostString(String url, String tag,
                                         VolleyInterface<String> volleyInterface,
                                         final Map<String, String> map) {
        MyApplication.getmQueue().cancelAll(tag);
        StringRequest request = new StringRequest(Request.Method.POST, url,
                volleyInterface.loadingListener(),
                volleyInterface.loadingErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        request.setTag(tag);
        MyApplication.getmQueue().add(request);
        MyApplication.getmQueue().start();
    }

    /**
     * Volley通过get请求，获得JsonObject数据
     *
     * @param url             请求地址
     * @param tag             tag标签
     * @param volleyInterface 回调接口
     */
    public static void requestGetJson(String url, String tag,
                                      VolleyInterface<JSONObject> volleyInterface) {
        MyApplication.getmQueue().cancelAll(tag);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                volleyInterface.loadingListener(),
                volleyInterface.loadingErrorListener());
        request.setTag(tag);
        MyApplication.getmQueue().add(request);
        MyApplication.getmQueue().start();
    }

    /**
     * Volley通过post请求，获得JsonObject数据
     *
     * @param url             请求地址
     * @param tag             tag标签
     * @param volleyInterface 回调接口
     * @param map             请求参数
     */
    public static void requestPostJson(String url, String tag,
                                       VolleyInterface<JSONObject> volleyInterface,
                                       final Map<String, String> map) {
        MyApplication.getmQueue().cancelAll(tag);
        JSONObject obj = new JSONObject(map);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, obj,
                volleyInterface.loadingListener(),
                volleyInterface.loadingErrorListener());
        request.setTag(tag);
        MyApplication.getmQueue().add(request);
        MyApplication.getmQueue().start();
    }

    /**
     * 加载图片
     *
     * @param url             图片地址
     * @param tag             tag标签
     * @param volleyInterface 回调接口
     * @param maxWidth        图片最大宽度，设置0为显示原图
     * @param maxHeight       图片最大高度，设置0为显示原图
     * @param decodeConfig    图片颜色属性
     *                        Bitmap.Config.ARGB_8888
     *                        Bitmap.Config.RGB_565
     */
    public static void requestImage(String url, String tag,
                                    VolleyInterface<Bitmap> volleyInterface,
                                    int maxWidth, int maxHeight,
                                    Bitmap.Config decodeConfig) {
        MyApplication.getmQueue().cancelAll(tag);
        ImageRequest request = new ImageRequest(url, volleyInterface.loadingListener(),
                maxWidth, maxHeight, decodeConfig,
                volleyInterface.loadingErrorListener());
        request.setTag(tag);
        MyApplication.getmQueue().add(request);
        MyApplication.getmQueue().start();
    }

    /**
     * ImageView加载缓存图片
     *
     * @param url               图片链接
     * @param imageView         要显示图片的ImageView
     * @param defaultImageResId 加载过程中显示的图片的id
     * @param errorImageResId   加载失败时显示的图片的id
     */
    public static void loaderImage(String url, ImageView imageView,
                                   int defaultImageResId, int errorImageResId) {
        ImageLoader loader = new ImageLoader(
                MyApplication.getmQueue(), new BitmapCache());
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(
                imageView, defaultImageResId, errorImageResId);
        loader.get(url, listener);
    }

    /**
     * NetworkImageView加载缓存图片
     *
     * @param url               图片链接
     * @param netImageView      要显示图片的NetworkImageView
     * @param defaultImageResId 加载过程中显示的图片的id
     * @param errorImageResId   加载失败时显示的图片的id
     */
    public static void netLoaderImage(String url, NetworkImageView netImageView,
                                      int defaultImageResId, int errorImageResId) {
        ImageLoader loader = new ImageLoader(
                MyApplication.getmQueue(), new BitmapCache());
        netImageView.setDefaultImageResId(defaultImageResId);
        netImageView.setErrorImageResId(errorImageResId);
        netImageView.setImageUrl(url, loader);
    }

    /**
     * 关闭指定队列，一般在onStop()方法中调用
     *
     * @param tag tag标签
     */
    public static void closeRequest(String tag) {
        MyApplication.getmQueue().cancelAll(tag);
    }

    /**
     * Map拼接参数成数据
     *
     * @param map 请求参数
     * @return
     */
    public static String checkMap(Map<String, String> map) {
        String data = "";
        Set<String> set = map.keySet();
        for (String key : set) {
            data = data + key + "=" + map.get(key) + "&";
        }
        data = data.substring(0, data.length() - 1);
        return data;
    }

}
