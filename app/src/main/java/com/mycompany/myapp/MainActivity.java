package com.mycompany.myapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.os.Build;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.widget.Button;
import android.graphics.Color;
import android.view.View;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebSettings;
import java.util.ArrayList;
import java.util.List;
import com.shuyu.action.web.CustomActionWebView;
import com.shuyu.action.web.ActionSelectListener;
import android.widget.Toast;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.util.Log;
import android.graphics.Bitmap;
import android.webkit.WebResourceResponse;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

	CustomActionWebView mWebView;
	String url = "https://m.examcoo.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		Log.d("ksk", "init");

		mWebView = findViewById(R.id.activity_mainWebView);

        initWebView();
		initWebSettings();	
		initWebViewClient();
		initLong();

    }

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return;
		}
		super.onBackPressed();
	}



	private void initWebView() {

		mWebView.loadUrl(url);
		mWebView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
					Log.d("ksk", "loadibg：" + request.getUrl().toString());
					return false;
				}
			});

	}

	private void initWebSettings() {
		WebSettings settings = mWebView.getSettings();
		//支持获取手势焦点
		mWebView.requestFocusFromTouch();
		//支持JS
		settings.setJavaScriptEnabled(true);
		//支持插件
		settings.setPluginState(WebSettings.PluginState.ON);
		//设置适应屏幕
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		//支持缩放
		settings.setSupportZoom(false);
		//隐藏原生的缩放控件
		settings.setDisplayZoomControls(true);
		//支持内容重新布局
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		settings.supportMultipleWindows();
		settings.setSupportMultipleWindows(true);
		//设置缓存模式
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_DEFAULT);
		settings.setAppCacheEnabled(true);
		settings.setAppCachePath(mWebView.getContext().getCacheDir().getAbsolutePath());

		//设置可访问文件
		settings.setAllowFileAccess(true);
		//当webview调用requestFocus时为webview设置节点
		settings.setNeedInitialFocus(true);
		//支持自动加载图片
		if (Build.VERSION.SDK_INT >= 19) {
			settings.setLoadsImagesAutomatically(true);
		} else {
			settings.setLoadsImagesAutomatically(false);
		}
		settings.setNeedInitialFocus(true);
		//设置编码格式
		settings.setDefaultTextEncodingName("UTF-8");
	}


	void initLong() {
		List<String> list = new ArrayList<>();
		list.add("🙈");

        // 设置item
		mWebView.setActionList(list);

        // 链接js注入接口，使能选中返回数据
		mWebView.linkJSInterface();

        // 增加点击回调
		mWebView.setActionSelectListener(new ActionSelectListener(){

				@Override
				public void onClick(String p1, String p2) {
					Toast.makeText(MainActivity.this, p2, Toast.LENGTH_SHORT).show();
				}		
			});
	}

	private void initWebViewClient() {
		mWebView.setWebViewClient(new WebViewClient() {

				//是否在WebView内加载新页面
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					view.loadUrl(url);

					return true;
				}

				@Override
				public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

					Log.d("ksk", request.getUrl().toString());
					if (request.getUrl().toString().startsWith("https://m.examcoo.com/app/editor/getreexamcontent/")) loadExam(request.getUrl().toString());
					return super.shouldInterceptRequest(view, request);
				}
			});

	}

	private void loadExam(final String url) {
		Thread laodThread = new Thread(new Runnable(){

				@Override
				public void run() {
					final String examStr = HttpUtils.doGet(url);
					Log.d("ksk", "题目内容=" + examStr);
					MainActivity.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
							}
						}
					);
				}
			});
		laodThread.start();
	}

}
