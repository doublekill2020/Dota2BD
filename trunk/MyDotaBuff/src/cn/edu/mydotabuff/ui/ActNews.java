package cn.edu.mydotabuff.ui;

import cn.edu.mydotabuff.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ActNews extends Activity {
	private String newsUrl;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_news);
		initIntent();
		initWebView();

	}

	private void initWebView() {
		webView = (WebView) findViewById(R.id.wv_news);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.loadUrl(newsUrl);
		webView.setWebViewClient(new WebViewClient());
	}

	/**
	 * 初始化intent
	 * 
	 */
	private void initIntent() {
		// TODO Auto-generated method stub
		Intent i = getIntent();
		newsUrl = i.getStringExtra("url");

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webView.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			webView.loadData("", "text/html; charset=UTF-8", null);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
