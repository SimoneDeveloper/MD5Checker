package com.simonedev.md5checker;

import com.simonedev.md5checker.fragments.FragmentFile;
import com.simonedev.md5checker.fragments.FragmentText;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

	String[] tabNames;
	
	public ViewPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		// TODO Auto-generated constructor stub
		tabNames = new String[]{context.getString(R.string.file), context.getString(R.string.text)};
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		if(position == 0) {
			return new FragmentFile();
		} else {
			return new FragmentText();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
    @Override
    public CharSequence getPageTitle(int position) {
        return tabNames[position];
    }

}