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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;

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

