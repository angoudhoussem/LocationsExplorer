package com.location.favoris;

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

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.locationsexplorer.FragmentTwo;
import com.example.locationsexplorer.JavaBean;
import com.example.locationsexplorer.R;
import com.location.constant.ConstantValues;
import com.location.constant.InformationValues;
import com.location.constant.ListInfoAdapter;

public class CafeFavoris extends Fragment {
	String result, result1;
	String obj;
	JSONObject AptJsonObject, AptJsonObject1;
	JSONArray data, data1;
	InputStream is = null, is1 = null;
	Button b1;
	ArrayList<JavaBean> ItemList = new ArrayList<JavaBean>();
	private ListView lv;

	private ArrayList<InformationValues> dataList;

	private String type;
	private ProgressDialog dialog;

	ArrayList<InformationValues> listinfo = new ArrayList<InformationValues>();
	ArrayList<InformationValues> listinfo1 = new ArrayList<InformationValues>();

	public CafeFavoris() {
	}

	@SuppressLint("DefaultLocale")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("onCreateView", "onCreateView");
		View view = inflater.inflate(R.layout.listfavoris,
				container, false);
		lv = (ListView) view.findViewById(R.id.list_friend);
		b1 = (Button) view.findViewById(R.id.button1);
		dataList = new ArrayList<InformationValues>();

		type = getArguments().getString(FragmentTwo.ITEM_NAME).toLowerCase();
		Log.i("type", "" + type);
		listinfo.clear();
		new taskList().execute();
//
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			public void onItemClick(AdapterView<?> a, View v, int position,
//					long id) {
//				AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
//				adb.setTitle("Delete?");
//				adb.setMessage("Are you sure you want to delete " + position);
//				final int positionToRemove = position;
//				adb.setNegativeButton("Cancel", null);
//				adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//                      
//						
//					}
//				});
//				adb.show();
//			}
//		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("onCreate", "onCreate");
		super.onCreate(savedInstanceState);
	}

	class taskList extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
			dialog.setIndeterminate(false);
			dialog.setCancelable(true);
			dialog.show();
		}

		protected String doInBackground(String... args) {

			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			String url = "entity.favoris/recherchefavoriscafe";
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ url + "/" + ConstantValues.Email);

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
					List.setId(JObject.getString("lieuId"));
					List.setId(JObject.getString("nom"));
					List.setNom(JObject.getString("nom"));
					List.setUrl(JObject.getString("url"));
					List.setTel(JObject.getString("téléphone"));
					List.setAdress(JObject.getString("adresse"));
					List.setLatitude((Double.parseDouble(JObject
							.getString("latitude"))));
					List.setLangitude((Double.parseDouble(JObject
							.getString("longitude"))));

					listinfo.add(List);

				}

			}

			catch (Exception e) {

				Log.e("ItemList", e.getMessage());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			final ListAdapter listinfoadapter = new ListInfoAdapter(
					getActivity(), listinfo);
			lv.setAdapter(listinfoadapter);

			b1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < listinfo.size(); i++) {
						new tasksupp().execute(listinfo.get(i).getId());
					}
				}
			});

			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Fragment fragment = null;
					Bundle args = new Bundle();
					fragment = new FragmentSinglePlaceaeroport();
					FragmentManager frgManager = getFragmentManager();
					args.putString("nom", listinfo.get(position).getNom());
					args.putString("adresse", listinfo.get(position)
							.getAdress());
					args.putString("tel", listinfo.get(position).getTel());
					args.putString("url", listinfo.get(position).getUrl());
					System.out.println("nom" + listinfo.get(position).getNom());

					args.putString("id", listinfo.get(position).getId());

					Log.i("args.nom", "" + listinfo.get(position).getNom());

					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					fragment.setArguments(args);
				}
			});

			super.onPostExecute(result);
			dialog.dismiss();
		}

	}

	class tasksupp extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... args) {
			String id = args[0];
			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			String url = "entity.favoris/suppfvoris";
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ url + "/" + id);

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

			}

			catch (Exception e) {

				Log.e("ItemList", e.getMessage());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}

	}

}
