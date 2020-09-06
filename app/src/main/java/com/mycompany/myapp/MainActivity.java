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
import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.alibaba.fastjson.TypeReference;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import android.text.TextUtils;
import android.widget.LinearLayout;
import tech.gujin.toast.ToastUtil;

public class MainActivity extends AppCompatActivity {

	CustomActionWebView mWebView;
	String url = "https://m.examcoo.com/";
    String dbStr = "";
    List<LocalExam> allLocalDB;
    LinearLayout controlOut;
    
    List<String> result = new ArrayList<String>();
    int index=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToastUtil.initialize(this, ToastUtil.Mode.REPLACEABLE);
        
        controlOut = findViewById(R.id.ll_con_out);
        controlOut.setVisibility(View.GONE);
        
		Log.d("ksk", "init");

		mWebView = findViewById(R.id.activity_mainWebView);

        initWebView();
		initWebSettings();	
		initWebViewClient();
		initLong();
        
        try {
            dbStr = readDb();
            allLocalDB = JSON.parseArray(dbStr, LocalExam.class);
            
            ToastUtil.show("Load DB success -> " + allLocalDB.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        View next = findViewById(R.id.btn_next);
        View pre = findViewById(R.id.btn_pre);
        View show = findViewById(R.id.btn_show);
        next.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    try{
                        {
                            if (index == 0) return;
                            index --;
                            ToastUtil.show( "" + index + "\n" +result.get(index));
                        }
                    } catch(Exception e){
                        
                    }
                }
            });
        pre.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    try{
                        {
                            if (index == 0) return;
                            index ++;
                            ToastUtil.show( "" + index + "\n" +result.get(index));
                        }
                    } catch(Exception e){

                    }
                }
            });
        show.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View p1) {
                    try{
                        {
                            
                            ToastUtil.show( "" + index + "\n" +result.get(index));
                        }
                    } catch(Exception e){

                    }
                }
            });
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
        MainActivity.this.runOnUiThread(new Runnable(){

                @Override
                public void run() {
                    controlOut.setVisibility(View.VISIBLE);
                }
            });
        
        result.clear();
		Thread laodThread = new Thread(new Runnable(){

				@Override
				public void run() {
					final String examStr = HttpUtils.doGet(url);
					Log.d("ksk", "题目内容=" + examStr);
					MainActivity.this.runOnUiThread(new Runnable(){

							@Override
							public void run() {
								Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                                praseExamJson(examStr);
							}
						}
					);
				}
			});
		laodThread.start();
	}

    
    private void praseExamJson(String json){
        NewBean allbean = JSON.parseObject(json, NewBean.class);
        Toast.makeText(MainActivity.this, allbean.getData().getResult().getB().get(0).getA(), Toast.LENGTH_SHORT).show();
        
        List exams = allbean.getData().getResult().getB();
        
        for(NewBean.Data.Result.B b: exams){
            if(TextUtils.isEmpty(b.getA())){
                result.add("unknown");
                continue;
            }
            result.add(getAnswer(b.getA()));
        }
        ToastUtil.show("quean success -> " + result.size());
        
    }
    
    
    private String readDb() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("/storage/emulated/0/题库序列化.json"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
    
    
    String getAnswer(String qiestion){
        int max = 0;
        String result = "";
        for(LocalExam localExam : allLocalDB){
            int ratio = FuzzySearch.ratio(qiestion, localExam.getQuestion());
            if (ratio > max){
                max = ratio;
                result = localExam.getQuestion() + "\n" + localExam.getAnswer();
            }
        }
        return result;
    }
}
