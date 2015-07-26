package com.simonedev.md5checker;

import com.simonedev.md5checker.utils.PagerSlidingTabStrip;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

	public static final String ADS_ID = "ca-app-pub-3115066551709117/6242228981";
	
	PagerSlidingTabStrip pagerTabStrip;
	ViewPager viewPager;
	Toolbar toolbar;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.menu.menu, menu);
	    
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch(menuItem.getItemId()) {
		case R.id.action_share:
    		Intent intent = new Intent(Intent.ACTION_SEND);
    		intent.setType("text/plain");
    		intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.simonedev.md5checker");
    		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_text));
            startActivity(Intent.createChooser(intent, getString(R.string.share_with)));            
			break;
		case R.id.action_review:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.simonedev.md5checker")));		
			break;
		}		
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
		
		viewPager = (ViewPager)findViewById(R.id.view_pager);
		pagerTabStrip = (PagerSlidingTabStrip)findViewById(R.id.pager_tab_strip);
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), this));
		pagerTabStrip.setViewPager(viewPager);
		
	}
	
}