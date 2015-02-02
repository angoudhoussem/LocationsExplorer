package com.example.locationsexplorer;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.location.constant.DatabaseHandler;

public class FragmentFavoris extends Fragment {

	ImageView ivIcon;
	TextView tvItemName;
	public static final String IMAGE_RESOURCE_ID = "iconResourceID";
	public static final String ITEM_NAME = "itemName";
	
	private ListView lv;

	public FragmentFavoris() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragment_favoris,
				container, false);
		lv = (ListView) view.findViewById(R.id.listViewfavoris);
		DatabaseHandler db = new DatabaseHandler(getActivity());
		ArrayList<HashMap<String, String>> favovisList = db.getAllFavorite();
		Log.i("studentList.size()", "" + favovisList.size());
		if (favovisList.size() != 0) {
		
			Log.i("favovisList", "" + favovisList);
			ListAdapter adapter = new SimpleAdapter( getActivity(), favovisList, R.layout.view_favoris_list, new String[] {"FavoriteId","FavoriteName","FavoriteReferance"}, new int[] {R.id.fragmentId, R.id.fragmentname,R.id.fragmentreferance}); 
			lv.setAdapter(adapter);
		}
		return view;
	}

}
