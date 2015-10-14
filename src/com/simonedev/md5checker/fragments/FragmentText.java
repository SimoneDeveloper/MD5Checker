package com.simonedev.md5checker.fragments;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.simonedev.md5checker.MainActivity;
import com.simonedev.md5checker.R;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentText extends Fragment {

	TextView textView, textView2, textView3;
	ClipboardManager clipBoardManager;
	EditText editText, editText2;
	InterstitialAd interstitial;
	AdRequest adRequest;
	Activity activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_text, container, false);
				
		editText = (EditText)view.findViewById(R.id.edit_text);
		editText2 = (EditText)view.findViewById(R.id.edit_text2);
		
		textView = (TextView)view.findViewById(R.id.textView2);
		textView2 = (TextView)view.findViewById(R.id.textView4);
		textView3 = (TextView)view.findViewById(R.id.textView5);
		
		clipBoardManager = (ClipboardManager)activity.getSystemService(Context.CLIPBOARD_SERVICE);
		
	    interstitial = new InterstitialAd(activity);
	    interstitial.setAdUnitId(MainActivity.ADS_ID);
	    adRequest = new AdRequest.Builder().build();
	    interstitial.loadAd(adRequest);
		
		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(interstitial.isLoaded()) {
					interstitial.show();
				}
				
				String text = editText.getText().toString();
				if(text != null) {
					if(text.length() > 0) {
						editText2.setText(calculateMD5(text));
					} else {
						Toast.makeText(activity, activity.getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
					}
				}
			}			
		});
		
		textView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String text = editText2.getText().toString();
				if(text != null) {
					if(text.length() > 0) {
						ClipData clipData = ClipData.newPlainText("HashMD5", text);
						clipBoardManager.setPrimaryClip(clipData);		
						Toast.makeText(activity, activity.getString(R.string.clipboard), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(activity, activity.getString(R.string.empty_field), Toast.LENGTH_SHORT).show();
					}
				}
			}			
		});
		
		textView3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				editText.setText(null);
				editText2.setText(null);
			}			
		});
		
		return view;
	}
	
	public String calculateMD5(String text) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
		    digest.update(text.getBytes());
		    byte messageDigest[] = digest.digest();

		    StringBuffer stringBuffer = new StringBuffer();
		    for(int i=0; i<messageDigest.length; i++) {
		    	stringBuffer.append(Integer.toString((messageDigest[i] & 0xff) + 0x100, 16).substring(1));
		    }
		    
		    return stringBuffer.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
}
