package com.example.webviewtest;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        String scheme = intent.getScheme();
        System.out.println("------scheme:" + scheme);

        Uri uri = intent.getData();
        if (uri != null) {
            String host = uri.getHost();
            String dataString = intent.getDataString();
            String id = uri.getQueryParameter("id");
            String path = uri.getPath();
            String path1 = uri.getEncodedPath();
            String queryString = uri.getQuery();
            int port = uri.getPort();
            System.out.println("-----host:" + host);
            System.out.println("-----dataString:" + dataString);
            System.out.println("-----id:" + id);
            System.out.println("-----path:" + path);
            System.out.println("-----path1:" + path1);
            System.out.println("-----queryString:" + queryString);
            System.out.println("-----port:" + port);
        }
    }

}
