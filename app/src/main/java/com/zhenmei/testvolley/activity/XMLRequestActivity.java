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
import com.zhenmei.testvolley.view.XMLRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class XMLRequestActivity extends Activity {

    private TextView tv_xmlrequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmlrequest);

        tv_xmlrequest = (TextView) findViewById(R.id.tv_xmlrequest);

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

}

