package com.simonedev.md5checker.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.simonedev.md5checker.R;
import com.simonedev.md5checker.fragments.FragmentFile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


public class MD5 {

	ProgressDialog progressDialog;
	String path, output;
	Context context;
	Handler handler;
	Thread thread;
	
	public MD5(Context context, final String path) {
		this.context = context;
		this.path = path;
		
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(context.getString(R.string.dialog_title));
		progressDialog.setMessage(context.getString(R.string.dialog_text));
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					MessageDigest messageDigest = MessageDigest.getInstance("MD5");
					InputStream inputStream = new FileInputStream(new File(path));
					byte[] buffer = new byte[8192];
					int read = 0;
		            while ((read = inputStream.read(buffer)) > 0) {
		                messageDigest.update(buffer, 0, read);
		            }
		            byte[] md5sum = messageDigest.digest();
		            BigInteger bigInt = new BigInteger(1, md5sum);
		            output = bigInt.toString(16);
		            output = String.format("%32s", output).replace(' ', '0');
		            Message message = handler.obtainMessage();
		            Bundle bundle = new Bundle();
		            bundle.putString("MD5Output", output);
		            message.setData(bundle);
		            handler.sendMessage(message);
		            		            
		            inputStream.close();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		});
		
		handler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				output = message.getData().getString("MD5Output");
				FragmentFile.editText2.setText(output);
				progressDialog.cancel();
			}
		};
		thread.start();
		
	}
	
}