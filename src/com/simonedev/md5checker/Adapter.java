package com.simonedev.md5checker;
  
import java.util.ArrayList;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

  
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
   
    ArrayList<FileInfo> arrayList;
    ViewHolder viewHolder;
    FileInfo fileInfo;
    View view;
    
    public class ViewHolder extends RecyclerView.ViewHolder {
                
    	ImageView imageView;
    	TextView textView; 
        FileInfo fileInfo;
        Context context;
        int id, position;
        
        public ViewHolder(View view, int viewType) {             
            super(view);
  
            switch(viewType) {
            case FileInfo.ADAPTER_PATH:
            	textView = (TextView)view.findViewById(R.id.textView);
            	imageView = (ImageView)view.findViewById(R.id.imageView);
            	id = FileInfo.ADAPTER_PATH;
            	
            	break;
            }
  
        }
         
    }
   
    public Adapter(ArrayList<FileInfo> arrayList){ 
        this.arrayList = arrayList;
    }
  
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
          
    	if(viewType == FileInfo.ADAPTER_PATH) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_path_selector, parent, false);
            viewHolder = new ViewHolder(view, viewType);
   
            return viewHolder;
        }
          
        return null;
    }
  
    @Override
    public void onBindViewHolder(final Adapter.ViewHolder holder, int position) {
    	fileInfo = arrayList.get(position);
    	
        if(holder.id == FileInfo.ADAPTER_PATH) {
        	holder.textView.setText(fileInfo.fileName);
        	if(fileInfo.isFolder) {
        		holder.imageView.setImageResource(R.drawable.ic_action_folder);
        	} else {
        		holder.imageView.setImageResource(R.drawable.ic_action_file);
        	}
        }
        
    }
   
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
  
    @Override
    public int getItemViewType(int position) { 
        return arrayList.get(position).adapter;
    }
   
}