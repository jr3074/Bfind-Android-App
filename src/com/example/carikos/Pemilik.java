package com.example.carikos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Pemilik extends Activity{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pemilik);
		
		Button login = (Button)findViewById(R.id.btLogin);
		Button daftar = (Button)findViewById(R.id.btReg);
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Pemilik.this,Login.class);
				startActivity(i);
			}
		});
		
		daftar.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent i = new Intent(Pemilik.this,Daftar.class);
						startActivity(i);
					}
		});
		
	}
}
