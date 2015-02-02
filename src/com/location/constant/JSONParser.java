package com.location.constant;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.Log;

public class JSONParser {
	static InputStream is;

	public String getJson(String url) throws IllegalStateException, IOException {
		String result = "";
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		HttpClient httpclient = new DefaultHttpClient();

		

		Log.e("ttttt", url);
		HttpPost httppost = new HttpPost(url);
		Log.e("ttttt", "1");
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		Log.e("ttttt", "2");
		HttpResponse response = httpclient.execute(httppost);
		Log.e("ttttt", "3");
		HttpEntity entity = response.getEntity();
		Log.e("ttttt", "4");
		is = entity.getContent();
		Log.e("ttttt", "5");

		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);
		Log.e("ttttt", "6");
		
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");

		}
		is.close();
		Log.e("ttttt", "8");
		result = sb.toString();

		return result;
	}

	public String getValidUrl(String url) {
		url.replaceAll(" ", "%20");
		return url;
	}
}
