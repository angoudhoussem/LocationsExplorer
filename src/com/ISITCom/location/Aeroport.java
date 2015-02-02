package com.ISITCom.location;

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
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.example.locationsexplorer.FragmentTwo;
import com.example.locationsexplorer.JavaBean;
import com.example.locationsexplorer.R;
import com.example.map.FragmentMap;
import com.location.constant.ConstantValues;
import com.location.constant.InformationValues;
import com.location.constant.ListInfoAdapter;
import com.location.favoris.FragmentSinglePlaceaeroport;

public class Aeroport extends Fragment {
	String result, result1;
	String obj;
	JSONObject AptJsonObject, AptJsonObject1;
	JSONArray data, data1;
	InputStream is = null, is1 = null;
	private ListView lv;
	private ArrayList<InformationValues> dataList;
	private String type;
	private ProgressDialog dialog;
	ArrayList<InformationValues> listinfo = new ArrayList<InformationValues>();
	ArrayList<InformationValues> listinfo1 = new ArrayList<InformationValues>();

	public Aeroport() {
	}

	@SuppressLint("DefaultLocale")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("onCreateView", "onCreateView");
		View view = inflater.inflate(R.layout.activity_fragment_locations,
				container, false);
		lv = (ListView) view.findViewById(R.id.list_friend);
		dataList = new ArrayList<InformationValues>();

		type = getArguments().getString(FragmentTwo.ITEM_NAME).toLowerCase();
		Log.i("type", "" + type);
		listinfo.clear();
		new taskList().execute();
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
			String url = "entity.aeroport/recherche";
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
					List.setId(JObject.getString("lieuId"));
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
					args.putString("id", listinfo.get(position).getId());
					args.putDouble("longitude", listinfo.get(position)
							.getLangitude());
					args.putDouble("latitude", listinfo.get(position).getLatitude());
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

}
