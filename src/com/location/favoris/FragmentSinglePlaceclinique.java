package com.location.favoris;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

import com.digitalaria.gama.carousel.Carousel;
import com.example.locationsexplorer.R;
import com.example.locationsexplorer.WebActivity;
import com.example.map.FragmentMap;
import com.example.map.MainActivity;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.location.constant.ConstantValues;
import com.location.constant.DatabaseHandler;
import com.location.constant.InformationValues;
import com.location.constant.JSONParser;
import com.location.favoris.FragmentSinglePlacebanque.taskTop;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created with IntelliJ IDEA. User: ServusKevin Date: 5/12/13 Time: 9:02 AM To
 * change this template use File | Settings | File Templates.
 */
public class FragmentSinglePlaceclinique extends Fragment {
	private ProgressDialog dialog;
	String idlieu;
	String result, nom, adresse;
	double longitude, latitude;
	String obj;
	JSONObject AptJsonObject;
	JSONArray data;
	InputStream is = null, is1 = null;
	private TextView textViewName, textViewAddress, textViewPhone, textSiteWeb;
	private ImageView imageView;
	private Button btAppel;
	private Button btMap, buttonRoute, buttonFacebook;
	private ImageButton btFavoris;
	private String reference;
	private String urlimage = "", urlimage2 = "", urlimage3 = "";
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private RatingBar ratingBar;
	private Carousel carousell;
	private ImageAdapter adapter;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	JSONParser jsonParser = new JSONParser();
	ArrayList<String> listInformation = new ArrayList<String>();
	ArrayList<String> mylist = new ArrayList<String>();
	ArrayList<InformationValues> listinfo = new ArrayList<InformationValues>();
	InformationValues info = ConstantValues.c;
	private String phone = "Non Enregistrer", name = "Pas d'information",
			Adresse = "Adresse Non Enregistrer", web_site = "Pas de site web",
			Latitude, Longitude, rating;
	private static String APP_ID = "624100760991692"; // Replace with your App

	// Instance of Facebook Class
	private Facebook facebook = new Facebook(APP_ID);
	private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs;
	Bundle b = new Bundle();
	String site;

	// SQLiteDatabase sq = db.getWritableDatabase();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.single_place, container,
				false);
		imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
		textViewName = (TextView) view.findViewById(R.id.textViewName);
		textViewAddress = (TextView) view.findViewById(R.id.textViewAddress);
		textViewPhone = (TextView) view.findViewById(R.id.textViewPhone);
		textSiteWeb = (TextView) view.findViewById(R.id.textsiteweb);
		btAppel = (Button) view.findViewById(R.id.butAppel);
		btMap = (Button) view.findViewById(R.id.buttonMap);
		buttonRoute = (Button) view.findViewById(R.id.buttonroutes);
		buttonFacebook = (Button) view.findViewById(R.id.button2);
		btFavoris = (ImageButton) view.findViewById(R.id.ImageButton01);
		// reference = getArguments().getString("reference");
		ratingBar = ((RatingBar) view.findViewById(R.id.RatingBar01));
		carousell = (Carousel) view.findViewById(R.id.caarousel);
		// configurations for the carousel.
		carousell.setType(Carousel.TYPE_COVERFLOW);
		carousell.setOverScrollBounceEnabled(true);
		carousell.setInfiniteScrollEnabled(false);
		carousell.setItemRearrangeEnabled(true);
		// // set images for the carousel.
		mylist.add(urlimage);
		mylist.add(urlimage2);
		mylist.add(urlimage3);
		nom = getArguments().getString("nom");
		adresse = getArguments().getString("adresse");
		String phone = getArguments().getString("tel");
		String url = getArguments().getString("url");
		latitude = getArguments().getDouble("latitude");
		longitude = getArguments().getDouble("longitude");

		idlieu = getArguments().getString("id");
		textViewName.setText(nom);
		textViewAddress.setText(adresse);
		textViewPhone.setText(phone);
		String url1 = "https://";
		site = url1 + url;
		textSiteWeb.setText(site);
		adapter = new ImageAdapter(getActivity(), mylist);
		carousell.setAdapter(adapter);
		// // change the first selected position.
		carousell.setCenterPosition(1);
		new GetPlacesDetail().execute(idlieu);

		// tooooooooooooooooooooooooooooooop///
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {

				String cmp = String.valueOf(rating);

				String s = cmp.substring(0, 1);

				System.out.println("stringS" + s);

				new taskTOP().execute(idlieu, s);

			}
		});

		// toooooooooooooooooooooooop//

		btAppel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + textViewPhone.getText()));
				getActivity().startActivity(callIntent);
			}
		});
		textSiteWeb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent callIntent = new Intent(getActivity(), WebActivity.class);
				callIntent.putExtra("url", site);
				getActivity().startActivity(callIntent);
			}
		});

		btMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Fragment fragment = null;
				Bundle args = new Bundle();
				fragment = new FragmentMap();
				FragmentManager frgManager = getFragmentManager();
				args.putDouble("latitude", latitude);
				System.out.println("latitude" + latitude);
				args.putDouble("longitude", longitude);
				System.out.println("longitude" + longitude);
				args.putString("TraceRoute", "TRACE_ROUTE_OFF");
				args.putString("Nom", nom);
				// Log.i("args.putDouble", "" + info.getLatitude());
				frgManager.beginTransaction()
						.replace(R.id.content_frame, fragment)
						.addToBackStack(null).commit();
				fragment.setArguments(args);
			}
		});
		buttonRoute.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), MainActivity.class);
				i.putExtra("latitude", latitude);
				System.out.println("latitude" + latitude);
				i.putExtra("longitude", longitude);
				System.out.println("longitude" + longitude);
				i.putExtra("Nom", nom);
				i.putExtra("adresse", adresse);
				startActivity(i);
			}
		});

		btFavoris.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("Favoris");
				builder.setIcon(R.drawable.ic_action_star);
				builder.setMessage("Ajouter au favoris ");

				builder.setNeutralButton("Non",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				builder.setNegativeButton("OUI",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

								new taskLis1t().execute(idlieu);
								Toast.makeText(getActivity(),
										"Favorite ajouter avec success", 2000)
										.show();
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		buttonFacebook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				postToWall();
				// loginToFacebook();
				// TODO Auto-generated method stub

			}

		});
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	class GetPlacesDetail extends AsyncTask<String, String, String> {
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

			String idd = args[0];
			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			System.out.println("idimage" + idd);
			String urll = "entity.photo/idhopital/" + idd;
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ urll);

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
				System.out.println("linaimage" + result);
				JSONArray a = new JSONArray(result);
				AptJsonObject = new JSONObject();
				AptJsonObject.put("data", a);
				data = AptJsonObject.getJSONArray("data");

				for (int t = 0; t < data.length(); t++) {

					InformationValues List = new InformationValues();
					JSONObject JObject = data.getJSONObject(t);
					List.setImage(JObject.getString("img"));

					System.out.println("getilage" + List.getImage());
					mylist.add(List.getImage());

					System.out.println("size" + mylist.size());
				}

			}

			catch (Exception e) {

				Log.e("ItemList", e.getMessage());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.cafe_icon)
					.showImageForEmptyUri(R.drawable.cafe_icon)
					.showImageOnFail(R.drawable.cafe_icon).cacheInMemory(true)
					.cacheOnDisc(true)
					// .displayer(new RoundedBitmapDisplayer(5))
					.build();
			adapter = new ImageAdapter(getActivity(), mylist);
			carousell.setAdapter(adapter);
			carousell.setCenterPosition(0);
			Log.i("rating", "" + rating);

			dialog.dismiss();

		}
	}

	public class ImageAdapter extends BaseAdapter {
		private Context mContext;
		private ArrayList<String> url;

		public ImageAdapter(Context c, ArrayList<String> uri) {
			mContext = c;
			url = uri;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.carousel_item, parent, false);
				view.setLayoutParams(new Carousel.LayoutParams(250, 250));
				ViewHolder holder = new ViewHolder();
				holder.imageView = (ImageView) view
						.findViewById(R.id.itemImage);
				view.setTag(holder);
			}
			ViewHolder holder = (ViewHolder) view.getTag();
			imageLoader.displayImage(url.get(position), holder.imageView,
					options, animateFirstListener);
			return view;
		}

		private class ViewHolder {
			ImageView imageView;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return url.size();
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

	public void loginToFacebook() {

		mPrefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);

			Log.d("FB Sessions", "" + facebook.isSessionValid());
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(getActivity(), new String[] { "email",
					"publish_stream" }, new DialogListener() {

				@Override
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();

				}

				@Override
				public void onError(DialogError error) {
					// Function to handle error

				}

				@Override
				public void onFacebookError(FacebookError fberror) {
					// Function to handle Facebook errors

				}

			});
		}
	}

	public void postToWall() {
		// post on user's wall.
		facebook.dialog(getActivity(), "feed", new DialogListener() {

			@Override
			public void onFacebookError(FacebookError e) {
			}

			@Override
			public void onError(DialogError e) {
			}

			@Override
			public void onComplete(Bundle values) {
			}

			@Override
			public void onCancel() {
			}
		});

	}

	class taskLis1t extends AsyncTask<String, String, String> {

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
			String urll = "entity.favoris/favorisclinique/"
					+ ConstantValues.Email + "/" + id;
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ urll);

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				System.out.println("result" + result);
			} catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result.equals("yes")) {
				Context context = getActivity();
				CharSequence text = "à la favoris";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			} else {

				Context context = getActivity();
				CharSequence text = "Existe";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();

			}
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}

	class taskTOP extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		protected String doInBackground(String... args) {

			String id = args[0];
			String id1 = args[1];
			HttpClient httpClient = new DefaultHttpClient();

			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 100000);
			HttpConnectionParams.setSoTimeout(httpParams, 100000);
			String urll = "entity.top/topclinique/" + ConstantValues.Email
					+ "/" + id + "/" + id1;
			HttpGet httpGet = new HttpGet(ConstantValues.REST_SERVICE_URL + "/"
					+ urll);

			httpGet.setHeader("Content-Type", "application/json");
			try {
				HttpResponse response = httpClient.execute(httpGet);
				result = EntityUtils.toString(response.getEntity());
				System.out.println("result" + result);
			} catch (Exception e) {

				System.out.println("erreur" + e.getMessage());

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
