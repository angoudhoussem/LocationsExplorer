package com.location.constant;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.locationsexplorer.R;

public class ListInfoAdapter extends BaseAdapter {

	private ArrayList<InformationValues> listData;

	private LayoutInflater layoutInflater;

	public ListInfoAdapter(Context context,
			ArrayList<InformationValues> listData) {
		this.listData = listData;
		layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return listData.size();
	}

	public Object getItem(int position) {
		return listData.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.list_item, null);

			holder = new ViewHolder();

			holder.textView = (TextView) convertView.findViewById(R.id.title);
			holder.textView2 = (TextView) convertView.findViewById(R.id.artist);
			//holder.info=(TextView)convertView.findViewById(R.id.id_info1);
			//holder.image = (ImageView) convertView.findViewById(R.id.img);
			

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();
		}

		InformationValues newsItem = (InformationValues) listData.get(position);
		holder.textView.setText(newsItem.getNom());
		holder.textView2.setText(newsItem.getAdress());
	//	holder.textView2.setText(String.valueOf(newsItem.getAdresse()));
		//holder.image.setImageBitmap(ConvertImageFromStringToBitmap.convert(newsItem.getImage()));
		return convertView;
	}
	static class ViewHolder {
		TextView textView;
		TextView textView2;
		ImageView image;

	}
}
