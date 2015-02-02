package com.example.locationsexplorer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

public class WebActivity extends Activity {

	private WebView browser;
	String value = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			value = extras.getString("url");
		}

		// field.setText(value);
		browser = (WebView) findViewById(R.id.webView1);
		browser.setWebViewClient(new MyBrowser());
	}

	public void open(View view) {

		browser.getSettings().setLoadsImagesAutomatically(true);
		browser.getSettings().setJavaScriptEnabled(true);
		browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		browser.loadUrl(value);

	}

	private class MyBrowser extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}