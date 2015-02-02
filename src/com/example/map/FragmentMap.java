package com.example.map;

import java.util.ArrayList;

import org.w3c.dom.Document;

import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.locationsexplorer.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class FragmentMap extends Fragment {
	Marker myMarker,myplace;
	public FragmentMap() {
	}
	int SDK_INT = android.os.Build.VERSION.SDK_INT;
	private MapView mapView;
	private GoogleMap map;
	private Double latitude;
	private Double longitude;
	private String nom;
	private String routsStatus;
	private LatLng location, locationMe,ESSTHS;
	private Bundle mBundle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflatedView = inflater.inflate(R.layout.fragment_layout_one, container,
				false);
		if (SDK_INT > 8) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		try {
			MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
			e.printStackTrace();
		}
		GPSTracker gps = new GPSTracker(getActivity());
		// check if GPS enabled
		if (gps.canGetLocation()) {
			latitude = gps.getLatitude();
			longitude = gps.getLongitude();
			///locationMe = new LatLng(latitude, longitude);
		} else {
		}
		// Gets the MapView from the XML layout and creates it
		//mapView = (MapView) inflatedView.findViewById(R.id.mapview);
		map = ((MapView) inflatedView.findViewById(R.id.mapview)).getMap();
		setUpMap();
		//mapView.onCreate(mBundle);
		///setUpMapIfNeeded(inflatedView);
		//location = new LatLng(getArguments().getDouble("latitude"),getArguments().getDouble("longitude"));
		
		return inflatedView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBundle = savedInstanceState;
	}

	private void setUpMapIfNeeded(View inflatedView) {
		
			
			//if (map != null) {
				//setUpMap();
			//}
		}
	

	private void setUpMap() {
		location = new LatLng(getArguments().getDouble("latitude"),
				getArguments().getDouble("longitude"));
		System.out.println("location"+location);
		nom = getArguments().getString("Nom");
		map.getUiSettings().setMyLocationButtonEnabled(true);
		map.setMyLocationEnabled(true);
		
		myMarker = map.addMarker(new MarkerOptions().position(location)
				.title(nom)
				.snippet("blallaalal")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map)));
		myMarker.showInfoWindow();
		ESSTHS = new LatLng(35.850212, 10.597382);
		myplace = map.addMarker(new MarkerOptions().position(ESSTHS)
				.title("ESSTHS")
				.snippet("Ecole Superieur des Sciences et Technologie")
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map)));
	myplace.showInfoWindow();
	//routsStatus = getArguments().getString("TraceRoute");
	//if (routsStatus.equalsIgnoreCase("TRACE_ROUTE_ON")) {
		new taskRoute().execute(ESSTHS,location);
	//}
	}

	class taskRoute extends AsyncTask<LatLng, String, PolylineOptions>    {
		@Override
		protected PolylineOptions doInBackground(LatLng... params) {
			// TODO Auto-generated method stub
			GMapV2Direction md = new GMapV2Direction();
			System.out.println("rrrrrr"+params[0]);
			System.out.println("rrrrrr"+params[1]);
			Log.i("enit",""+params[0]);
			Log.i("mosquee",""+params[1]);
			Document doc = md.getDocument(params[0], params[1],
					GMapV2Direction.MODE_WALKING);
			ArrayList<LatLng> directionPoint = md.getDirection(doc);
			PolylineOptions rectLine = new  PolylineOptions().width(8f)
					.color(Color.BLUE).geodesic(true);

			for (int i = 0; i < directionPoint.size(); i++) {
				rectLine.add(directionPoint.get(i));
			}
			return rectLine;
		}
		@Override
		protected void onPostExecute(PolylineOptions result) {
			// TODO Auto-generated method stub
			map.addPolyline(result);
			super.onPostExecute(result);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		mapView.onDestroy();
		super.onDestroy();
	}

}