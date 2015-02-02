package com.example.locationsexplorer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ISITCom.Top.Aeroporttop;
import com.ISITCom.Top.Banktop;
import com.ISITCom.Top.CafeLocationtop;
import com.ISITCom.Top.Cliniquetop;
import com.ISITCom.Top.Hoteltop;
import com.ISITCom.Top.Instituttop;
import com.ISITCom.Top.Mosquetop;
import com.ISITCom.Top.Restauranttop;
import com.ISITCom.Top.hopitaletop;
import com.ISITCom.Top.pharmacietop;
import com.ISITCom.location.Aeroport;
import com.ISITCom.location.Bank;
import com.ISITCom.location.CafeLocation;
import com.ISITCom.location.Clinique;
import com.ISITCom.location.Hotel;
import com.ISITCom.location.Institut;
import com.ISITCom.location.Mosque;
import com.ISITCom.location.Restaurant;
import com.ISITCom.location.hopitale;
import com.ISITCom.location.pharmacie;
import com.location.Nouveaute.Aeroportnouveaute;
import com.location.Nouveaute.CafeLocationnouveaute;
import com.location.favoris.AeroportFavoris;
import com.location.favoris.BanqueFavoris;
import com.location.favoris.CafeFavoris;
import com.location.favoris.CliniqueFavoris;
import com.location.favoris.HopitalFavoris;
import com.location.favoris.HotelFavoris;
import com.location.favoris.InstitutFavoris;
import com.location.favoris.MosqueFavoris;
import com.location.favoris.PharmacieFavoris;
import com.location.favoris.RestaurantFavoris;

public class FragmentListTop extends Fragment {
	FragmentTransaction fragMentTra = null;
	String result;
	String obj;
	JSONObject AptJsonObject;
	JSONArray data;
	ArrayList<JavaBean> ItemList = new ArrayList<JavaBean>();
	ProgressDialog pDialog;
	ListView list;

	public FragmentListTop() {
	}

	private ArrayList<DrawerItem> dataList;
	private CustomAdapter adapter;
	private ListView lv;
	private CharSequence mTitle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_fragment_locations,
				container, false);
		lv = (ListView) view.findViewById(R.id.list_friend);
		dataList = new ArrayList<DrawerItem>();
		dataList.add(new DrawerItem("Airport", R.drawable.airport));
		dataList.add(new DrawerItem("Cafe", R.drawable.cafe));
		dataList.add(new DrawerItem("Bank", R.drawable.bank));
		dataList.add(new DrawerItem("Hopitale", R.drawable.doctor));
		dataList.add(new DrawerItem("Restaurant", R.drawable.restaurant));
		dataList.add(new DrawerItem("Hotel", R.drawable.bus_icon));
		dataList.add(new DrawerItem("Mosque", R.drawable.worship_islam));
		dataList.add(new DrawerItem("Institut", R.drawable.parkin));
		dataList.add(new DrawerItem("Clinique", R.drawable.atm));
		dataList.add(new DrawerItem("Pharmacie", R.drawable.pharmacy));
		adapter = new CustomAdapter(getActivity(), R.layout.custum_list_item,
				dataList);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new DrawerItemClickListener());

		return view;
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			switch (position) {
			case 0:

				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Aeroporttop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}

				break;
			case 1:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new CafeLocationtop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 2:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Banktop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;

			case 3:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new hopitaletop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;

			case 4:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Restauranttop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;

			case 5:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Hoteltop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 6:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Mosquetop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 7:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Instituttop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 8:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new Cliniquetop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 9:
				if (dataList.get(position).getTitle() == null) {
					Bundle args = new Bundle();
					Fragment fragment = null;
					fragment = new pharmacietop();
					args.putString(FragmentTwo.ITEM_NAME, dataList
							.get(position).getItemName());
					fragment.setArguments(args);
					FragmentManager frgManager = getFragmentManager();
					frgManager.beginTransaction()
							.replace(R.id.content_frame, fragment)
							.addToBackStack(null).commit();
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			default:
				break;
			}

		}
	}

	private void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		mTitle = title;
	}

	// class Loade extends AsyncTask<String, String, String> {
	// String result;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// // pDialog = new ProgressDialog(FragmentLocations.this);
	// // pDialog.setMessage(Html.fromHtml("<b>Chargement....</b><br/>"));
	// // pDialog.setIndeterminate(false);
	// // pDialog.setCancelable(false);
	// // pDialog.show();
	// }
	//
	// @Override
	// protected String doInBackground(String... arg0) {
	//
	// HttpClient httpClient = new DefaultHttpClient();
	//
	// final HttpParams httpParams = httpClient.getParams();
	// HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
	// HttpConnectionParams.setSoTimeout(httpParams, 100000);
	//
	// HttpGet httpGet = new HttpGet(
	// "http://10.0.2.2:8080/restProject/resources/entity.restaurant/Recherche");
	//
	// httpGet.setHeader("Content-Type", "application/json");
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// result = EntityUtils.toString(response.getEntity());
	//
	// System.out.println("test" + result);
	//
	// JSONArray a = new JSONArray(result);
	// AptJsonObject = new JSONObject();
	// AptJsonObject.put("data", a);
	// data = AptJsonObject.getJSONArray("data");
	//
	// for (int t = 0; t < data.length(); t++) {
	//
	// JavaBean List = new JavaBean();
	// JSONObject JObject = data.getJSONObject(t);
	// List.setItemName(JObject.getString("nom"));
	// // List.setTitle(JObject.getString("adresse"));
	// // List.setAdresse(JObject.getString("image"));
	// // List.setNumtel(JObject.getString("idRest"));
	// // List.setUrl(JObject.getString("idRest"));
	//
	// ItemList.add(List);
	//
	// }
	//
	// }
	//
	// catch (Exception e) {
	//
	// Log.e("ItemList","");
	//
	// }
	//
	// return result;
	// }
	//
	// protected void onPostExecute(String file_url) {
	//
	// System.out.println("Result" + result);
	//
	// pDialog.cancel();
	//
	// adapter = new CustomAdapter(getActivity(),
	// R.layout.custum_list_item,dataList);
	// lv.setAdapter(adapter);
	// lv.setOnItemClickListener(new DrawerItemClickListener());
	//
	// }
	// }
	//
	//
	public void SelectItem(int possition) {

		Fragment fragment = null;
		// FragmentOne fragment1 =null;
		Bundle args = new Bundle();
		switch (possition) {
		case 0:
			fragment = new HomeFragment();

			break;
		case 1:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 2:
			fragment = new FragmentLocations();
			;
			// args.putString(FragmentOne.ITEM_NAME, dataList.get(possition)
			// .getItemName());
			// args.putInt(FragmentOne.IMAGE_RESOURCE_ID,
			// dataList.get(possition)
			// .getImgResID());
			break;
		case 3:
			fragment = new FragmentFavoris();
			break;
		case 4:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 5:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 6:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 7:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		case 8:
			fragment = new FragmentThree();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			break;
		default:
			break;
		}
		fragment.setArguments(args);
		FragmentManager frgManager = getFragmentManager();
		frgManager.beginTransaction().replace(R.id.content_frame, fragment)
				.commit();

		// mDrawerList.setItemChecked(possition, true);
		setTitle(dataList.get(possition).getItemName());
		// mDrawerLayout.closeDrawer(mDrawerList);

	}

}
