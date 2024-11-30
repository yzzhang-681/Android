package com.example.ex_four;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class demoWebView extends WebView {

    public demoWebView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 创建进度条
        ProgressBar pb = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);

        // 设置进度条的布局参数
        pb.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
        addView(pb);  // 将进度条添加到 WebView 中

        // 设置 WebView 的 WebChromeClient，用于监听加载进度
        setWebChromeClient(new MyWebChromeClient(context, pb));

        // 设置 WebViewClient，确保页面内部的链接也能在 WebView 中加载
        setWebViewClient(new MyWebViewClient());
    }

    // 自定义 WebChromeClient 类，用于监听网页加载进度
    public class MyWebChromeClient extends WebChromeClient {

        private Context context;
        private ProgressBar progressBar;

        public MyWebChromeClient(Context context, ProgressBar progressBar){
            this.context = context;
            this.progressBar = progressBar;
        }

        // 监听网页加载进度
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);  // 加载完成，隐藏进度条
            } else {
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);  // 显示进度条
                }
                progressBar.setProgress(newProgress);  // 更新进度条
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    // 自定义 WebViewClient 类，用于处理 URL 加载
    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());  // 在 WebView 中加载网页
            return true;
        }
    }
}
