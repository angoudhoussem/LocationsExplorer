package com.example.map;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;

import com.ISITCom.location.Restaurant;
import com.example.locationsexplorer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.location.constant.ConstantValues;
import com.location.constant.InformationValues;

public class MapInstitut extends FragmentActivity {
	private static LatLng position, ESSTHS;
	String result;
	JSONObject usersJsonObject;
	ArrayList<InformationValues> alluser = new ArrayList<InformationValues>();
	ProgressDialog pDialog;
	ArrayList<Marker> markerList;
	Marker marker, myplace;
	MarkerOptions marker1;
	JSONArray data, data1;
	GoogleMap map;
	InputStream is = null;
	JSONObject AptJsonObject, AptJsonObject1;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		map.setTrafficEnabled(true);

		markerList = new ArrayList<Marker>();

		new Loade().execute();

	}

	class Loade extends AsyncTask<String, String, String> {
		String result;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MapInstitut.this);
			pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			String url = "entity.institut/recherche";
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ url);

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));

				StringBuilder sb = new StringBuilder();

				String line = null;

				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();
				System.out.println("linaaa" + result);
				JSONArray a = new JSONArray(result);
				AptJsonObject = new JSONObject();
				AptJsonObject.put("data", a);
				data = AptJsonObject.getJSONArray("data");

				for (int t = 0; t < data.length(); t++) {

					InformationValues List = new InformationValues();
					JSONObject JObject = data.getJSONObject(t);
					
					List.setNom(JObject.getString("nom"));
					List.setUrl(JObject.getString("url"));
					List.setTel(JObject.getString("téléphone"));
					List.setAdress(JObject.getString("adresse"));
					List.setLatitude((Double.parseDouble(JObject
							.getString("latitude"))));
					List.setLangitude((Double.parseDouble(JObject
							.getString("longitude"))));
                   
					alluser.add(List);

				}

			}

			catch (Exception e) {

				Log.e("ItemList", e.getMessage());

			}

			return null;
		}

		protected void onPostExecute(String file_url) {

			for (int i = 0; i < alluser.size(); i++) {
				
				position = new LatLng(alluser.get(i)
						.getLatitude(),alluser.get(i)
						.getLangitude());
				marker = map.addMarker(new MarkerOptions().position(position)
						.title(alluser.get(i).getNom())
						.snippet(alluser.get(i).getAdress())
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.ic_map)));

				map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 35));
				map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
				markerList.add(marker);

			}
		

			pDialog.cancel();
		}
	}

}
