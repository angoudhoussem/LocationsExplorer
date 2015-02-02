package com.example.locationsexplorer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.map.MapAereport;
import com.example.map.MapBanque;
import com.example.map.MapCafe;
import com.example.map.MapClinique;
import com.example.map.MapInstitut;
import com.example.map.MapMosque;
import com.example.map.MapPharmacie;
import com.example.map.MapRestaurant;
import com.example.map.Maphopitale;
import com.example.map.Maphotel;
import com.location.favoris.BanqueFavoris;
import com.location.favoris.CafeFavoris;
import com.location.favoris.CliniqueFavoris;
import com.location.favoris.HopitalFavoris;
import com.location.favoris.HotelFavoris;
import com.location.favoris.InstitutFavoris;
import com.location.favoris.MosqueFavoris;
import com.location.favoris.PharmacieFavoris;
import com.location.favoris.RestaurantFavoris;

public class FragementRecherche extends Fragment {
	FragmentTransaction fragMentTra = null;
	String result;
	String obj;
	JSONObject AptJsonObject;
	JSONArray data;
	ArrayList<JavaBean> ItemList = new ArrayList<JavaBean>();
	ProgressDialog pDialog;
	ListView list;

	public  FragementRecherche() {
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
					
					Intent i=new Intent(getActivity(),MapAereport.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				
				break;
			case 1:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapCafe.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 2:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapBanque.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
				
			case 3:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),Maphopitale.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
				
			case 4:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapRestaurant.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
				
			case 5:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),Maphotel.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 6:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapMosque.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 7:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapInstitut.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 8:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapClinique.class);
					startActivity(i);
					lv.setItemChecked(position, true);
					setTitle(dataList.get(position).getItemName());
				}
				break;
			case 9:
				if (dataList.get(position).getTitle() == null) {
					Intent i=new Intent(getActivity(),MapPharmacie.class);
					startActivity(i);
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
