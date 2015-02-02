package com.example.locationsexplorer;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Fragment;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment
{

	public HomeFragment()
	{
		
	}
	Button b1,b2,b3,b4;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		

		View view = inflater.inflate(R.layout.activity_home_fragment, container,
				false);

		b1 = (Button) view.findViewById(R.id.imageButton1);
		b2 = (Button) view.findViewById(R.id.imageButton2);
		b3 = (Button) view.findViewById(R.id.imageButton3);
		b4 = (Button) view.findViewById(R.id.imageButton4);
		
		
		return view;
	}
}
