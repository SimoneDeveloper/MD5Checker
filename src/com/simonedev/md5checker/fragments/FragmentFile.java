package com.simonedev.md5checker.fragments;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.simonedev.md5checker.MainActivity;
import com.simonedev.md5checker.R;
import com.simonedev.md5checker.tools.MD5;
import com.simonedev.md5checker.tools.PathSelector;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentFile extends Fragment {
	
	public static EditText editText, editText2, editText3;
	TextView textView, textView2;
	InterstitialAd interstitial;
	AdRequest adRequest;
	Activity activity;
	
	public interface OnDataPassed {		
		public void setData(String data);		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_file, container, false);
		
		textView = (TextView)view.findViewById(R.id.textView2);
		textView2 = (TextView)view.findViewById(R.id.textView5);
		editText2 = (EditText)view.findViewById(R.id.edit_text2);		
		editText3 = (EditText)view.findViewById(R.id.edit_text3);
		
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
				
				String path = editText.getText().toString();
				
				if(path != null) {
					if(path.length() > 0) {
						new MD5(activity, path);						
					} else {
						Toast.makeText(activity, activity.getString(R.string.no_file_selected), Toast.LENGTH_SHORT).show();
					}
				}
				
			}			
		});
		textView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String path = editText2.getText().toString();
				String md5 = editText3.getText().toString();
				if(path != null && md5 != null) {
					if(path.length() > 0 && md5.length() > 0) {
						if(path.equals(md5)) {
							Toast.makeText(activity, activity.getString(R.string.md5_match), Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(activity, activity.getString(R.string.md5_not_match), Toast.LENGTH_SHORT).show();
						}
					} 
				}
				
			}			
		});
		editText = (EditText)view.findViewById(R.id.edit_text);		
		editText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new PathSelector(activity);
			}			
		});
		editText.setKeyListener(null);		
		
		return view;
	}
	
}