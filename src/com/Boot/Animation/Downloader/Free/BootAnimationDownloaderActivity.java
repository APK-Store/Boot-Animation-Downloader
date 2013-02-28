package com.Boot.Animation.Downloader.Free;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class BootAnimationDownloaderActivity extends Activity {

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public void onLoadResource(WebView view, String url) {
			if (url.contains("action=trailer") || url.contains("action=video")) {
				Log.w("wryebash", "trailer clicked:" + url);
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);

			}

		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
	if ((keyCode == KeyEvent.KEYCODE_BACK) && myweb.canGoBack()) {
	myweb.goBack();
	return true;
	}
	return super.onKeyDown(keyCode, event);
	}


	private static final int MENU_HOME = 0;
	private static final int MENU_INSTALL = 1;
	private static final int MENU_DONATE = 2;
	private static final int MENU_ABOUT = 3;
	private static final int MENU_CHLG = 4;

	
	final Activity activity = this; // added
	Boolean isfirsttime = false;

	private WebView myweb;

	public void downloadfileto(String fileurl, String filename) {
		String myString;
		try {
			FileOutputStream f = new FileOutputStream(filename);
			try {
				URL url = new URL(fileurl);
				URLConnection urlConn = url.openConnection();
				InputStream is = urlConn.getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is, 8000);
				int current = 0;
				while ((current = bis.read()) != -1) {
					f.write((byte) current);
				}
			} catch (Exception e) {
				myString = e.getMessage();
			}
			f.flush();
			f.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.main);
		myweb = (WebView) findViewById(R.id.WebView);

		myweb.setWebViewClient(new HelloWebViewClient());

		myweb.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				activity.setProgress(progress * 100);
			}

		});

		Toast.makeText(this, "Please wait while the application loads",
				Toast.LENGTH_LONG).show();

		myweb.loadUrl("file:///android_asset/index.html");

		myweb.getSettings().setJavaScriptEnabled(true);

		myweb.getSettings().setPluginsEnabled(true);
		myweb.getSettings().setBuiltInZoomControls(true);

		SharedPreferences settings = getSharedPreferences("MyPrefsFile", 0);
		isfirsttime = settings.getBoolean("isfirsttime", false);
		if (isfirsttime) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("isfirsttime", false);
			editor.commit();
			downloadfileto(
					"http://crazydroid.net23.net/cgi-bin/wryebashcounter.php",
					"/sdcard/wbrvclientid.txt");
			Log.w("isfirsttime", "isfirsttime is true");
		} else {
			Log.w("isfirsttime", "isfirsttime is false");
		}

	}

	/* Creates the menu items */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_HOME, 0, "Home");
		menu.add(0, MENU_INSTALL, 0, "Installation");
		menu.add(0, MENU_DONATE, 0, "Donate");
		menu.add(0, MENU_ABOUT, 0, "About");
		menu.add(0, MENU_CHLG, 0, "Change Log");

		return true;
	}

	
	/* Handles item selections */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_HOME:
			Toast.makeText(this, "Please wait while the application loads",
					Toast.LENGTH_LONG).show();
			myweb.loadUrl("file:///android_asset/index.html");

			return true;
		case MENU_INSTALL:
			Toast.makeText(this, "Please wait while the application loads",
					Toast.LENGTH_LONG).show();
			myweb.loadUrl("file:///android_asset/INSTALL.html");

			return true;
		case MENU_DONATE:
			Toast.makeText(this, "Please wait while the application loads",
					Toast.LENGTH_LONG).show();
			myweb.loadUrl("file:///android_asset/donate.html");

			return true;
		case MENU_ABOUT:
			Toast.makeText(this, "Please wait while the application loads",
					Toast.LENGTH_LONG).show();
			myweb.loadUrl("file:///android_asset/about.html");

			return true;
		case MENU_CHLG:
			Toast.makeText(this, "Please wait while the application loads",
					Toast.LENGTH_LONG).show();
			myweb.loadUrl("file:///android_asset/chlg.html");

			return true;
		}
		return false;
	}
}
