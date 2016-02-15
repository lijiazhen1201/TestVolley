package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.zhenmei.testvolley.R;

/**
 * Created by zhenmei on 16/2/15.
 */
public class ImageLoaderActivity extends Activity {

    private ImageView iv_imageloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);

        iv_imageloader = (ImageView) findViewById(R.id.iv_imageloader);

		/*
		 * Volley在请求网络图片方面可以做到的还远远不止这些，而ImageLoader就是一个很好的例子。
		 * ImageLoader也可以用于加载网络上的图片，并且它的内部也是使用ImageRequest来实现的，
		 * 不过ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，
		 * 还可以过滤掉重复的链接，避免重复发送请求。
		 */

		/*
		 * 由于ImageLoader已经不是继承自Request的了，所以它的用法也和我们之前学到的内容有所不同，总结起来大致可以分为以下四步：
		 * 1. 创建一个RequestQueue对象。
		 * 2. 创建一个ImageLoader对象。
		 * 3. 获取一个ImageListener对象。
		 * 4. 调用ImageLoader的get()方法加载网络上的图片。
		 */

        // 首先需要获取到一个RequestQueue对象,,参数是上下文对象Context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 新建一个ImageLoader对象,ImageLoader的构造函数接收2个参数
        // 第一个参数是RequestQueue对象，
        // 第二个参数是一个ImageCache对象
        ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                // TODO Auto-generated method stub

            }

            @Override
            public Bitmap getBitmap(String url) {
                // TODO Auto-generated method stub
                return null;
            }
        };
        ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
        // 接下来需要获取一个ImageListener对象,通过调用ImageLoader的getImageListener()方法能够获取到一个ImageListener对象
		/*
		 * getImageListener()方法接收三个参数，
		 * 第一个参数ImageView view指定用于显示图片的ImageView控件，
		 * 第二个参数int defaultImageResId指定加载图片的过程中显示的图片，
		 * 第三个参数int errorImageResId指定加载图片失败的情况下显示的图片。
		 */
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(iv_imageloader, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher);
        //最后，调用ImageLoader的get()方法来加载图片
		/*
		 * get()方法接收两个参数，
		 * 第一个参数就是图片的URL地址，
		 * 第二个参数则是刚刚获取到的ImageListener对象
		 */
        String requestUrl="http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1205/02/c0/11459868_1335958865509.jpg";
        imageLoader.get(requestUrl, listener);
        //当然，如果你想对图片的大小进行限制，也可以使用get()方法的重载，指定图片允许的最大宽度和高度
        //imageLoader.get(requestUrl, imageListener, maxWidth, maxHeight);
    }

	/*
	 * 刚才介绍的ImageLoader的优点却还没有使用到。为什么呢？
	 * 因为这里创建的ImageCache对象是一个空的实现，完全没能起到图片缓存的作用。
	 * 其实写一个ImageCache也非常简单，但是如果想要写一个性能非常好的ImageCache，
	 * 最好就要借助Android提供的LruCache功能了，如果你对LruCache还不了解，可以参考
	 * http://blog.csdn.net/guolin_blog/article/details/9316683
	 */

    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            //这里我们将缓存图片的大小设置为10M
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }
        //接着修改创建ImageLoader实例的代码，第二个参数传入BitmapCache的实例
        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }


}
