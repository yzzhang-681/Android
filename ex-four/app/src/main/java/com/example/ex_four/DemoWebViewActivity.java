package com.example.ex_four;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import androidx.appcompat.app.AppCompatActivity;

public class DemoWebViewActivity extends AppCompatActivity {

    private demoWebView dw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);  // 设置布局文件，里面包含自定义的 WebView

        // 获取 Intent 中传递的 URL
        Intent getInfo = getIntent();
        Uri uri = getInfo.getData();  // 获取 URL 地址

        // 通过 URL 对象构造网址
        dw = findViewById(R.id.webView);  // 获取自定义 WebView
        dw.getSettings().setJavaScriptEnabled(true);  // 启用 JavaScript
        dw.loadUrl(uri.toString());  // 加载 URL
    }
}
