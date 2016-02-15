package com.zhenmei.testvolley.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zhenmei.testvolley.R;

/**
 * Created by zhenmei on 16/2/15.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private Button btn_stringrequest;
    private Button btn_jsonrequest;
    private Button btn_imagerequest;
    private Button btn_imageloader;
    private Button btn_networkimageview;
    private Button btn_xmlrequest;
    private Button btn_gsonrequest;

    public void initView() {
        btn_stringrequest = (Button) findViewById(R.id.btn_stringrequest);
        btn_stringrequest.setOnClickListener(this);
        btn_jsonrequest = (Button) findViewById(R.id.btn_jsonrequest);
        btn_jsonrequest.setOnClickListener(this);
        btn_imagerequest = (Button) findViewById(R.id.btn_imagerequest);
        btn_imagerequest.setOnClickListener(this);
        btn_imageloader = (Button) findViewById(R.id.btn_imageloader);
        btn_imageloader.setOnClickListener(this);
        btn_networkimageview = (Button) findViewById(R.id.btn_networkimageview);
        btn_networkimageview.setOnClickListener(this);
        btn_xmlrequest = (Button) findViewById(R.id.btn_xmlrequest);
        btn_xmlrequest.setOnClickListener(this);
        btn_gsonrequest = (Button) findViewById(R.id.btn_gsonrequest);
        btn_gsonrequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_stringrequest:
                startActivity(new Intent(MainActivity.this, StringRequestActivity.class));
                break;
            case R.id.btn_jsonrequest:
                startActivity(new Intent(MainActivity.this, JsonRequestActivity.class));
                break;
            case R.id.btn_imagerequest:
                startActivity(new Intent(MainActivity.this, ImageRequestActivity.class));
                break;
            case R.id.btn_imageloader:
                startActivity(new Intent(MainActivity.this, ImageLoaderActivity.class));
                break;
            case R.id.btn_networkimageview:
                startActivity(new Intent(MainActivity.this, NetworkImageViewActivity.class));
                break;
            case R.id.btn_xmlrequest:
                startActivity(new Intent(MainActivity.this, XMLRequestActivity.class));
                break;
            case R.id.btn_gsonrequest:
                startActivity(new Intent(MainActivity.this, GsonRequestActivity.class));
                break;

            default:
                break;
        }
    }





}
