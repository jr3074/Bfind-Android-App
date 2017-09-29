package com.example.carikos;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//untuk menghubungkan button ke class
		ImageButton pemilik = (ImageButton)findViewById(R.id.btPemilikKos);
		
		pemilik.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, Pemilik.class);
				startActivity(i);
			}
		});
		
		//button cari kos
		ImageButton cariKos = (ImageButton)findViewById(R.id.btCariKos);
		cariKos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, Map.class);
				startActivity(i);
			}
		});
	}

}
