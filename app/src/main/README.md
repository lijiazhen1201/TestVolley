# TestVolley

## Volley的使用

###Volley简介
我们平时在开发Android应用的时候不可避免地都需要用到网络技术，而多数情况下应用程序都会使用HTTP协议来发送和接收网络数据。

Android系统中主要提供了两种方式来进行HTTP通信，HttpURLConnection和HttpClient，几乎在任何项目的代码中我们都能看到这两个类的身影，使用率非常高。

不过HttpURLConnection和HttpClient的用法还是稍微有些复杂的，如果不进行适当封装的话，很容易就会写出不少重复代码。于是乎，一些Android网络通信框架也就应运而生。

比如说AsyncHttpClient，它把HTTP所有的通信细节全部封装在了内部，我们只需要简单调用几行代码就可以完成通信操作了。再比如Universal-Image-Loader，它使得在界面上显示网络图片的操作变得极度简单，开发者不用关心如何从网络上获取图片，也不用关心开启线程、回收图片资源等细节，Universal-Image-Loader已经把一切都做好了。

Android开发团队也是意识到了有必要将HTTP的通信操作再进行简单化，于是在2013年Google I/O大会上推出了一个新的网络通信框架——Volley。Volley可是说是把AsyncHttpClient和Universal-Image-Loader的优点集于了一身，既可以像AsyncHttpClient一样非常简单地进行HTTP通信，也可以像Universal-Image-Loader一样轻松加载网络上的图片。

除了简单易用之外，Volley在性能方面也进行了大幅度的调整，它的设计目标就是非常适合去进行数据量不大，但通信频繁的网络操作，而对于大数据量的网络操作，比如说下载文件等，Volley的表现就会非常糟糕。

1. StringRequest

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
        
2. JsonRequest

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
        
3. ImageRequest

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
        
4. ImageLoader

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
	    
5. NetworkImageView

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

6. 自定义XMLRequest

		//仿照StringRequest
		//XMLRequest也是继承自Request类的，只不过这里指定的泛型类是XmlPullParser，
		//说明我们准备使用Pull解析的方式来解析XML。
		public class XMLRequest extends Request<XmlPullParser> {
	
	    private final Listener<XmlPullParser> mListener;
	
	    public XMLRequest(int method, String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
	        super(method, url, errorListener);
	        mListener = listener;
	        // TODO Auto-generated constructor stub
	    }
	
	    public XMLRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
	        this(Method.GET, url, listener, errorListener);
	    }
	
	    // 在parseNetworkResponse()方法中，先是将服务器响应的数据解析成一个字符串，然后设置到XmlPullParser对象中
	    @Override
	    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
	        // TODO Auto-generated method stub
	        try {
	            String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
	            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	            XmlPullParser xmlPullParser = factory.newPullParser();
	            xmlPullParser.setInput(new StringReader(xmlString));
	            return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
	        } catch (UnsupportedEncodingException e) {
	            return Response.error(new ParseError(e));
	        } catch (XmlPullParserException e) {
	            return Response.error(new ParseError(e));
	        }
	    }
	
	    // 在deliverResponse()方法中则是将XmlPullParser对象进行回调
	    @Override
	    protected void deliverResponse(XmlPullParser response) {
	        // TODO Auto-generated method stub
	        mListener.onResponse(response);
	    }		
		}
		
使用方法：

        // 自定义一个XMLRequest，用于请求一条XML格式的数据。

        // 首先需要获取到一个RequestQueue对象,参数是上下文对象Context context
        RequestQueue mQueue = Volley.newRequestQueue(this);
        // 新建XMLRequest的实例,传入三个参数
        String url = "http://flash.weather.com.cn/wmaps/xml/china.xml";
        Listener<XmlPullParser> listener = new Listener<XmlPullParser>() {
            @Override
            public void onResponse(XmlPullParser response) {
                // TODO Auto-generated method stub
                parseCityXML(response);
            }
        };
        ErrorListener errorListener = new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i("info", error.getMessage(), error);
            }
        };
        XMLRequest xmlRequest = new XMLRequest(url, listener, errorListener);
        // 最后，将这个XMLRequest对象添加到RequestQueue里面
        mQueue.add(xmlRequest);
    }

    public void parseCityXML(XmlPullParser response) {

        try {
            int eventType = response.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        String nodeName = response.getName();
                        if ("city".equals(nodeName)) {
                            String pName = response.getAttributeValue(0);
                            Log.d("TAG", "pName is " + pName);
                            String city = tv_xmlrequest.getText().toString();
                            tv_xmlrequest.setText(city + pName + "\n");
                        }
                        break;
                }
                eventType = response.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
	
7. 自定义GsonRequest

	
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
		
使用方法：

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
		
