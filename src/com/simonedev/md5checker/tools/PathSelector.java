package com.simonedev.md5checker.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.simonedev.md5checker.Adapter;
import com.simonedev.md5checker.FileInfo;
import com.simonedev.md5checker.R;
import com.simonedev.md5checker.fragments.FragmentFile;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PathSelector extends RelativeLayout {

	GestureDetector gestureDetector;
	AlertDialog.Builder builder;
	RecyclerView recyclerView;
	ArrayList<FileInfo> list;
	String path, mPath;
	AlertDialog alert;
	TextView textView;
	Adapter adapter;
	Context context;
	File[] mFile;
	
	public PathSelector(final Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
				
		View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_dialog, this);

		textView = (TextView)view.findViewById(R.id.textView);
		textView.setVisibility(View.VISIBLE);
						
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		
		view.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				if(arg2.getAction() == KeyEvent.ACTION_DOWN) {
					if(arg1 == KeyEvent.KEYCODE_BACK) {
						String text = textView.getText().toString();
						
						if(text.contains("/")) {
							String newPath = text.substring(0, text.lastIndexOf("/"));
							
							if(newPath.length() > 0) {
								new LoadValues(new File(newPath)).execute();
							} else if(text.equals("/sys")) {
								alert.cancel();
							}
							
						}
						
						return true;
					}
				}
				return false;
			}
		});
		
		recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
				
		gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
       	 @Override public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
       
       recyclerView.addOnItemTouchListener(new OnItemTouchListener() {
			@Override
			public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
				// TODO Auto-generated method stub
				View view = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
				
				if(view != null && gestureDetector.onTouchEvent(motionEvent)) {
					int position = recyclerView.getChildAdapterPosition(view);
					
					FragmentFile.editText.setText(null);
					FragmentFile.editText2.setText(null);
					FragmentFile.editText3.setText(null);
					
					path = list.get(position).path;

					if(new File(path).isDirectory()) {
						new LoadValues(new File(path)).execute();						
					} else {	
						FragmentFile.editText.setText(path);
						alert.cancel();
					}
				}				
				
				return false;
			}

			@Override
			public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
				// TODO Auto-generated method stub				
			}

			@Override
			public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
				// TODO Auto-generated method stub				
			}        	
       });
	       
       new LoadValues(Environment.getExternalStorageDirectory()).execute();
    
	   builder = new AlertDialog.Builder(context);
       builder.setView(view);
       builder.setPositiveButton(context.getString(R.string.close), null);
       alert = builder.create();
       alert.show();
       
	}

	private class LoadValues extends AsyncTask<Void, Void, Void> {
		
		File file;
		
		private LoadValues(File file) {
			this.file = file;
		}
		
		@Override
		public void onPreExecute() {
			super.onPreExecute();
			if(file != null) {
				if(file.exists()) {
					textView.setText(file.getPath());								
				}
			}
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mFile = file.listFiles();
								
			list = new ArrayList<FileInfo>();
			if(mFile != null) {
				for(int i=0;i<mFile.length;i++) {
					FileInfo fileInfo = new FileInfo();
					fileInfo.fileName = mFile[i].getName().replace("/", "").replace("null", "");
					fileInfo.isFolder = mFile[i].isDirectory();
					fileInfo.adapter = FileInfo.ADAPTER_PATH;
					fileInfo.path = mFile[i].getPath();
					list.add(fileInfo);
				}

		        Collections.sort(list, new Comparator<FileInfo>() {
					@Override
					public int compare(FileInfo fileInfo, FileInfo fileInfo2) {
						// TODO Auto-generated method stub
						return Boolean.compare(fileInfo2.isFolder, fileInfo.isFolder);
					}
		        });	
			}
	        
			return null;
		}
		
		@Override
		public void onPostExecute(Void result) {
			super.onPostExecute(result);
	        adapter = new Adapter(list);
	        recyclerView.setAdapter(adapter);     
	        recyclerView.setLayoutManager(new LinearLayoutManager(context));
		}
		
	}
	
}