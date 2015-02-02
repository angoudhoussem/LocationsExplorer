package com.example.activity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationsexplorer.R;

public class Inscription extends Activity {
	Button btvalide;
	EditText username, email, password,passwordconfirm;
	TextView textView1;
	InputStream is;
	ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);

		btvalide = (Button) findViewById(R.id.valider);
		username = (EditText) findViewById(R.id.editText1);
		email = (EditText) findViewById(R.id.editText2);
		password = (EditText) findViewById(R.id.editText3);
		passwordconfirm = (EditText) findViewById(R.id.editText4);
		textView1 = (TextView) findViewById(R.id.textView1);
		
		btvalide.setOnClickListener(new OnClickListener() {

			
			@Override
			public void onClick(View v) {
				final String Email = email.getText().toString();
				final String Password = password.getText().toString();
				final String Passwordconfirm = passwordconfirm.getText().toString();
				 if (Password.equals(Passwordconfirm)) {
					 textView1.setText("le meme");
				if (isValidEmail(Email) && isValidPassword(Password)){
				new Loade().execute();
				} else {
					
					password.setError("erreur");
					email.setError("erreur");
				}
				 }else {
					 textView1.setText("n'est pas le meme");
				 }
			}
		});
	}

	class Loade extends AsyncTask<String, String, String> {
		String result = null;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Inscription.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... arg0) {

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			String url="http://10.0.2.2:23755/Application/webresources/entity.utilisateur/inscription/"
					+ username.getText().toString()
					+ "/"
					+ email.getText().toString()
					+ "/"
					+ password.getText().toString();
			
			System.out.println("url"+url);
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("Content-Type", "text/plain");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				System.out.println("result" + result);
			} catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

			}
			return result;
		}

		protected void onPostExecute(String file_url) {

			System.out.println("result" + file_url);

			if (result.equals("yes")) {
				final Intent intent = new Intent(Inscription.this,
						Authentification.class);

				startActivity(intent);
				finish();

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_success,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("Inscription réussite");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				pDialog.cancel();
				

			} 
			
			if(result.equals("nook")){

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_error,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("notok");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				pDialog.cancel();
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			startActivity(new Intent(getApplicationContext(),
					Authentification.class));

		}

		return super.onKeyDown(keyCode, event);
	}
	// validating email id
	private boolean isValidEmail(String email) {
		String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
 
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
 
	// validating password with retype password
	private boolean isValidPassword(String pass) {
		if (pass != null && pass.length() > 6) {
			return true;
		}
		return false;
	}
}
