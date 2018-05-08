package com.openclassrooms.mynews.Controllers.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.openclassrooms.mynews.R;

public class WebViewActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra("Url");
        WebView webView = findViewById(R.id.webView1);
        webView.setWebViewClient(new AuthClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

    private class AuthClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView v, String url) {
            v.clearHistory();
        }
    }
}
