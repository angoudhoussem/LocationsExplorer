package com.example.activity;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.locationsexplorer.MainActivity;
import com.example.locationsexplorer.R;
import com.location.constant.ConstantValues;

public class Authentification extends Activity {

	Button btinscription;
	Button btconnection;
	EditText password, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		btconnection = (Button) findViewById(R.id.connection);
		btinscription = (Button) findViewById(R.id.inscription);
		email = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);

		btinscription.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent i = new Intent(Authentification.this, Inscription.class);
				startActivity(i);
				finish();
			}
		});

		btconnection.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String Username = password.getText().toString();
				String Email = email.getText().toString();
				ConstantValues.Email = Email;
				if (Username.equals("") || Email.equals("")) {
					LayoutInflater inflater = getLayoutInflater();
					View layout = inflater.inflate(
							R.layout.toast_costum_layout_error,
							(ViewGroup) findViewById(R.id.toast_layout_root));
					TextView text = (TextView) layout
							.findViewById(R.id.id_home);
					text.setText("Email et Password vides");
					Toast toast = new Toast(getApplicationContext());
					toast.setGravity(Gravity.BOTTOM, 0, 110);
					toast.setDuration(Toast.LENGTH_LONG);
					toast.setView(layout);
					toast.show();

				} else {

					new Loade().execute();
				}

			}
		});

	}

	class Loade extends AsyncTask<String, String, String> {
		String result = null;
		InputStream is;
		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Authentification.this);
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
			String url = "http://10.0.2.2:23755/Application/webresources/entity.utilisateur/auth/"
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

			if (result.equals("yes")) {
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_success,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("Email et password Valides");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				startActivity(i);
				pDialog.cancel();

			} else {

				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(
						R.layout.toast_costum_layout_error,
						(ViewGroup) findViewById(R.id.toast_layout_root));
				TextView text = (TextView) layout.findViewById(R.id.id_home);
				text.setText("Email et password Invalides");
				Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.BOTTOM, 0, 110);
				toast.setDuration(Toast.LENGTH_LONG);
				toast.setView(layout);
				toast.show();

				pDialog.cancel();

			}

		}
	}

}
