package com.example.locationsexplorer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ISITCom.help.CurlActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.Marker;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity extends Activity implements OnMarkerClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	CustomDrawerAdapter adapter;
	List<DrawerItem> dataList;
	private GoogleMap mMap;
	PackageInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		try {
		    info = getPackageManager().getPackageInfo("com.example.locationsexplorer", PackageManager.GET_SIGNATURES);
	        Log.e("hash0", "");
		    for (Signature signature : info.signatures) {
		        MessageDigest md;
		        md = MessageDigest.getInstance("SHA");
		        md.update(signature.toByteArray());
		        String something = new String(Base64.encode(md.digest(), 0));
		        //String something = new String(Base64.encodeBytes(md.digest()));
		        Log.e("hash key", something);
		    }
		} catch (NameNotFoundException e1) {
		    Log.e("name not found", e1.toString());
		} catch (NoSuchAlgorithmException e) {
		    Log.e("no such an algorithm", e.toString());
		} catch (Exception e) {
		    Log.e("exception", e.toString());
		}
		// Initializing
		dataList = new ArrayList<DrawerItem>();
		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,	GravityCompat.START);
		// Add Drawer Item to dataList
	
		dataList.add(new DrawerItem("Menu", R.drawable.ic_action_map));
		dataList.add(new DrawerItem("Categorie", R.drawable.ic_action_good));
		dataList.add(new DrawerItem("Favoris", R.drawable.ic_action_gamepad));
		dataList.add(new DrawerItem("Top", R.drawable.ic_action_labels));
		dataList.add(new DrawerItem("Nouveauté", R.drawable.ic_action_cloud));
		dataList.add(new DrawerItem("Recherche Avancée", R.drawable.ic_action_search));
		dataList.add(new DrawerItem("Help", R.drawable.ic_action_video));
		adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,dataList);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setTitle(mDrawerTitle);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			 if (dataList.get(0).getTitle() == null) {
				 SelectItem(0);			}
			 else 			 
				SelectItem(0);
				}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void SelectItem(int possition) {

		Fragment fragment = null;
//		FragmentOne fragment1 =null;
		Bundle args = new Bundle();
		switch (possition) {
		
		case 0:
			fragment = new FragmentThree();
//			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());
//			fragment.setArguments(args);
//			FragmentManager frgManager = getFragmentManager();
//			frgManager.beginTransaction().replace(R.id.content_frame, fragment)
//					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 1:
			fragment = new FragmentLocations();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			fragment.setArguments(args);
			FragmentManager frgManager1 = getFragmentManager();
			frgManager1.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 2:
			fragment = new FragmentListfavoris();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			
			fragment.setArguments(args);
			FragmentManager frgManager2 = getFragmentManager();
			frgManager2.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 3:
			fragment = new FragmentListTop();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			fragment.setArguments(args);
			FragmentManager frgManager4 = getFragmentManager();
			frgManager4.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 4:
			fragment = new FragmentListNouveaute();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			fragment.setArguments(args);
			FragmentManager frgManager5 = getFragmentManager();
			frgManager5.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 5:
			fragment = new FragementRecherche();
			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
					.getItemName());
			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
					.getImgResID());
			fragment.setArguments(args);
			FragmentManager frgManager6 = getFragmentManager();
			frgManager6.beginTransaction().replace(R.id.content_frame, fragment)
					.commit();

			mDrawerList.setItemChecked(possition, true);
			setTitle(dataList.get(possition).getItemName());
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 6:
			
			Intent i=new Intent(getApplicationContext(),CurlActivity.class);
			 startActivity(i);
//			//fragment = new FragmentThree();
//			args.putString(FragmentTwo.ITEM_NAME, dataList.get(possition)
//					.getItemName());
//			args.putInt(FragmentTwo.IMAGE_RESOURCE_ID, dataList.get(possition)
//					.getImgResID());
			break;
		default:
			break;
		}
		

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return false;
	}
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if (dataList.get(position).getTitle() == null) {
				SelectItem(position);
			}
		}
	}
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		return false;
	}
}
