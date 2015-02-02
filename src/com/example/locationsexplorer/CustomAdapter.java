package com.example.locationsexplorer;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<DrawerItem>{


	Context context;
	List<DrawerItem> drawerItemList;
	int layoutResID;

	public CustomAdapter(Context context, int layoutResourceID,
			ArrayList<DrawerItem> dataList) {
		super(context, layoutResourceID, dataList);
		this.context = context;
		this.drawerItemList = dataList;
		this.layoutResID = layoutResourceID;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		DrawerItemHolder drawerHolder;
		View view = convertView;
		if (view == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			drawerHolder = new DrawerItemHolder();

			view = inflater.inflate(layoutResID, parent, false);
			drawerHolder.icon = (ImageView) view.findViewById(R.id.imageinvit);
			drawerHolder.ItemName = (TextView) view.findViewById(R.id.nameinvit);
			view.setTag(drawerHolder);
		} else {
			drawerHolder = (DrawerItemHolder) view.getTag();

		}
		DrawerItem dItem = (DrawerItem) this.drawerItemList.get(position);
			drawerHolder.icon.setImageDrawable(view.getResources().getDrawable(
					dItem.getImgResID()));
			drawerHolder.ItemName.setText(dItem.getItemName());
			Log.d("Getview","Passed5");
		
		return view;
			}
	private static class DrawerItemHolder {

		TextView ItemName;
		ImageView icon;
	}
}