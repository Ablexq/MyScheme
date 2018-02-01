package com.example.webviewtest;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends Activity {

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

		/*原生 跳转*/
        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(new Intent(WebViewActivity.this, MainActivity.class));
            }
        });

        /*shouldOverrideUrlLoading重定向 跳转*/
        WebView myWebView1 = (WebView) findViewById(R.id.myWebView1);
        myWebView1.loadUrl("file:///android_asset/test1.html");//加载本地的url
        myWebView1.setWebViewClient(new myWebViewClient());

        /*scheme 跳转*/
        //scheme跳转，不可重写shouldOverrideUrlLoading方法，否则scheme不起作用
        WebView myWebView2 = (WebView) findViewById(R.id.myWebView2);
        myWebView2.loadUrl("file:///android_asset/test1.html");

        /*js与android交互 跳转*/
        WebView myWebView3 = (WebView) findViewById(R.id.myWebView3);
        myWebView3.loadUrl("file:///android_asset/test1.html");
        myWebView3.getSettings().setJavaScriptEnabled(true);
        // 与js交互，JsCallAndroidinterface 是个接口，与js交互时用到的，这个接口实现了从网页跳到app中的activity 的方法，特别重要
        myWebView3.addJavascriptInterface(new JsCallAndroidinterface(this), "android");
    }

    private class myWebViewClient extends WebViewClient {

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {/*此处获取的url的scheme都是小写*/
            if (!TextUtils.isEmpty(url) && url.contains("topicid://aa.bb:80/test?p=12&d=1")) {
                Intent intent = new Intent();
                intent.setClass(WebViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
            return true;
        }
    }

    private class JsCallAndroidinterface {

        Activity mActivity;

        JsCallAndroidinterface(Activity mActivity) {
            this.mActivity = mActivity;
        }

        /**
         * 与js交互时用到的方法，在js里直接调用的
         * 定义JS需要调用的方法,被JS调用的方法必须加入@JavascriptInterface注解,否则HTML中的js报错：toActivity不是一个function
         */
        @JavascriptInterface
        public void toActivity() {
            System.out.println("----------------toActivity--");
            mActivity.startActivity(new Intent(mActivity, MainActivity.class));
        }
    }

}
