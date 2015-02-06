package cn.edu.mydotabuff.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import cn.edu.mydotabuff.R;

public class ActNews extends Activity {

	private FrameLayout fullVideoView;// 全屏时视频加载view
	private WebView videoWebView;
	/**
	 * true竖屏 false横屏
	 */
	private Boolean islandport = true;// true表示此时是竖屏，false表示此时横屏。
	private View xCustomView;
	private xWebChromeClient xwebchromeclient;
	private WebChromeClient.CustomViewCallback xCustomViewCallback;

	private String newsUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_news);
		initIntent();
		initWebView();
		videoWebView.loadUrl(newsUrl);

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

	/**
	 * 初始化webview以及设置
	 */
	private void initWebView() {

		fullVideoView = (FrameLayout) findViewById(R.id.video_fullView);
		videoWebView = (WebView) findViewById(R.id.video_webView);
		WebSettings ws = videoWebView.getSettings();
		/**
		 * setAllowFileAccess 启用或禁止WebView访问文件数据 setBlockNetworkImage 是否显示网络图像
		 * setBuiltInZoomControls 设置是否支持缩放 setCacheMode 设置缓冲的模式
		 * setDefaultFontSize 设置默认的字体大小 setDefaultTextEncodingName 设置在解码时使用的默认编码
		 * setFixedFontFamily 设置固定使用的字体 setJavaSciptEnabled 设置是否支持Javascript
		 * setLayoutAlgorithm 设置布局方式 setLightTouchEnabled 设置用鼠标激活被选项
		 * setSupportZoom 设置是否支持变焦
		 * */
		ws.setBuiltInZoomControls(true);// 隐藏缩放按钮
		ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
		ws.setLoadWithOverviewMode(true);// setUseWideViewPort方法设置webview推荐使用的窗口。setLoadWithOverviewMode方法是设置webview加载的页面的模式。
		ws.setSavePassword(true);
		ws.setSaveFormData(true);// 保存表单数据
		ws.setJavaScriptEnabled(true);
		ws.setDomStorageEnabled(true);
		xwebchromeclient = new xWebChromeClient();
		videoWebView.setWebChromeClient(xwebchromeclient);
		videoWebView.setWebViewClient(new xWebViewClientent());
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		videoWebView.onPause();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (inCustomView()) {
				hideCustomView();
				return true;
			} else {
				// 判断是否能返回
				if (videoWebView.canGoBack()) {
					videoWebView.goBack();
					return true;
				} else {
					videoWebView.loadUrl("about:blank");
					ActNews.this.finish();
					Log.i("testwebview", "finish!");
				}
			}
		}
		return true;
	}

	/**
	 * 判断是否是全屏
	 * 
	 * @return
	 */
	public boolean inCustomView() {
		return (xCustomView != null);
	}

	/**
	 * 全屏时按返加键执行退出全屏方法
	 */
	public void hideCustomView() {
		xwebchromeclient.onHideCustomView();
	}

	/**
	 * 处理Javascript的对话框、网站图标、网站标题以及网页加载进度等
	 * 
	 * @author
	 */
	public class xWebChromeClient extends WebChromeClient {
		private Bitmap xdefaltvideo;
		private View xprogressvideo;

		@Override
		// 播放网络视频时全屏会被调用的方法
		public void onShowCustomView(View view,
				WebChromeClient.CustomViewCallback callback) {
			if (islandport) {

			} else {

				// ii = "1";
				// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			videoWebView.setVisibility(View.GONE);
			// 如果一个视图已经存在，那么立刻终止并新建一个
			if (xCustomView != null) {
				callback.onCustomViewHidden();
				return;
			}

			fullVideoView.addView(view);
			xCustomView = view;
			xCustomViewCallback = callback;
			fullVideoView.setVisibility(View.VISIBLE);
		}

		@Override
		// 视频播放退出全屏会被调用的
		public void onHideCustomView() {

			if (xCustomView == null)// 不是全屏播放状态
				return;

			// Hide the custom view.
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			xCustomView.setVisibility(View.GONE);

			// Remove the custom view from its container.
			fullVideoView.removeView(xCustomView);
			xCustomView = null;
			fullVideoView.setVisibility(View.GONE);
			xCustomViewCallback.onCustomViewHidden();

			videoWebView.setVisibility(View.VISIBLE);

		}

		// 视频加载添加默认图标
		@Override
		public Bitmap getDefaultVideoPoster() {
			// Log.i(LOGTAG, "here in on getDefaultVideoPoster");
			if (xdefaltvideo == null) {
				xdefaltvideo = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_launcher);
			}
			return xdefaltvideo;
		}

		// 视频加载时进程loading
		@Override
		public View getVideoLoadingProgressView() {
			// Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

			if (xprogressvideo == null) {
				LayoutInflater inflater = LayoutInflater.from(ActNews.this);
				xprogressvideo = inflater.inflate(
						R.layout.video_loading_progress, null);
			}
			return xprogressvideo;
		}

		// 网页标题
		@Override
		public void onReceivedTitle(WebView view, String title) {
			(ActNews.this).setTitle(title);
		}

	}

	/**
	 * 处理各种通知、请求等事件
	 * 
	 * @author
	 */
	public class xWebViewClientent extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.i("webviewtest", "shouldOverrideUrlLoading: " + url);
			view.loadUrl(url);
			return false;
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		Log.i("testwebview", "=====<<<  onConfigurationChanged  >>>=====");
		super.onConfigurationChanged(newConfig);

		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			Log.i("webview", "   现在是横屏");
			islandport = false;
		} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			Log.i("webview", "   现在是竖屏");
			islandport = true;
		}
	}
}
