package com.example.carikos;



import java.text.DecimalFormat;

import org.json.JSONObject;

import org.json.JSONException; 



import android.os.AsyncTask;
import android.os.Handler;

import android.os.Bundle;

import android.app.Activity;

import android.app.ProgressDialog;

import android.content.Intent;

import android.view.Menu;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import android.widget.EditText;

import android.widget.Toast;

public class Daftar extends Activity {

	EditText username;

	EditText password;

	EditText nama;

	EditText telepon;

	EditText alamat;

	EditText jmlKamar;

	EditText harga;

	EditText lattitude;

	EditText longitude;
	
	RadioGroup jenis;
	
	RadioGroup status;

    private ProgressDialog pDialog;
    
    String hasil;
    GpsService	gps;
    double getlatitude;//iki
    double getlongitude;//iki
    ProgressDialog progressBar; //iki
	int progressBarStatus=0; //iki
	Handler progressBarHandler = new Handler();//iki

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.daftar);


		username = (EditText)findViewById(R.id.txtUsername);

		password = (EditText)findViewById(R.id.txtPassword);

		nama = (EditText)findViewById(R.id.txtNama);

		telepon = (EditText)findViewById(R.id.txtTelp);

		alamat = (EditText)findViewById(R.id.txtAlamat);

		jmlKamar = (EditText)findViewById(R.id.txtJmlKamar);

		harga = (EditText)findViewById(R.id.txtharga);

		lattitude = (EditText)findViewById(R.id.txtLat);

		longitude = (EditText)findViewById(R.id.txtLong);
		
		jenis = (RadioGroup)findViewById(R.id.jenis);
		
		status = (RadioGroup)findViewById(R.id.status);
		
		Button daftar = (Button)findViewById(R.id.btDaftarUser);
		Button btnShowLocation = (Button) findViewById(R.id.btCariLongLat);
		
		username.requestFocus();
		
		btnShowLocation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// buat class object dari GpsService
				gps = new GpsService(Daftar.this);

				// dicek dulu apakah GPSnya idup
				if (gps.canGetLocation())
				{
					//mulai iki
					GetLocationTask AsyncGetLocation = new GetLocationTask();
					AsyncGetLocation.execute();
					//sampai iki
					
				} else
				{
					// jika GPS tidak aktif
					gps.showSettingAlert();
				}
			}
			
		});

		daftar.setOnClickListener(new OnClickListener() {
			@Override

			public void onClick(View arg0) {

				// TODO Auto-generated method stub
				if(nama.getText().toString().length() == 0){
					//Toast.makeText(Daftar.this, "ada data belum di isi", 1).show(); 
					nama.setError("Wajib Diisi");
					nama.requestFocus();
				}
				else if(username.getText().toString().length() == 0){
					username.setError("Wajib Diisi");
					username.requestFocus();
				}
				else if(password.getText().toString().length() == 0){
					password.setError("Wajib Diisi");
					password.requestFocus();
				}
				else if(telepon.getText().toString().length() == 0){
					alamat.setError("Wajib Diisi");
					alamat.requestFocus();
				}
				else if(jmlKamar.getText().toString().length() == 0){
					jmlKamar.setError("Wajib Diisi");
					jmlKamar.requestFocus();
				}
				else if(lattitude.getText().toString().length() == 0){
					lattitude.setError("Wajib Diisi");
					lattitude.requestFocus();
				}
				else if(longitude.getText().toString().length() == 0){
					longitude.setError("Wajib Diisi");
					longitude.requestFocus();
				}
				else{
					RegisterTask AsyncRegister = new RegisterTask();
					AsyncRegister.execute();
				}
			}
		});
	}
	
	//mulai iki
		private int getLocation()
		{
		    
		    getlatitude = gps.getLatitude();
		    getlongitude = gps.getLongitude();

		    if(getlongitude == 0 && getlatitude == 0)
		        return 0;
		    else
		        return 100;

		}
		//sampe iki

	public class RegisterTask extends AsyncTask<String, String, String> {

        @Override

        protected void onPreExecute() {

        	super.onPreExecute();

        	pDialog = new ProgressDialog(Daftar.this);

        	pDialog.setMessage("Mendaftarkan Kosan Anda...");

        	pDialog.setIndeterminate(false);

        	pDialog.setCancelable(true);
        	pDialog.show();
        	}

        protected String doInBackground(String... args) {

        	String user = username.getText().toString();

			String pass = password.getText().toString();

			String nm = nama.getText().toString();

			String telp = telepon.getText().toString();

			String almt = alamat.getText().toString();

			String jml = jmlKamar.getText().toString();

			String hrg = harga.getText().toString();

			String lat = lattitude.getText().toString();

			String longi = longitude.getText().toString();
			
			String jns;
			if(((RadioGroup)findViewById(R.id.jenis)).getCheckedRadioButtonId()==R.id.laki){
				jns="Laki-laki";
			}else{
				jns="Perempuan";
			}
			
			String stat;
			if(((RadioGroup)findViewById(R.id.status)).getCheckedRadioButtonId()==R.id.kosong){
				stat="Kosong";
			}else{
				stat="Penuh";
			}

			//buat object dari json class
			UserFunctions userFunction = new UserFunctions(); 
			 JSONObject json = userFunction.registerPemilik(user, pass, nm, telp, almt, jml, hrg, lat, longi, stat, jns);

			 try {

				if(json.getString("success")!= null){

					 hasil = json.getString("success"); 	

				}

			} catch (JSONException e) {

				// TODO: handle exception

				e.printStackTrace();
			}

            return null;
        }
        

        protected void onPostExecute(String file_url) {

        	pDialog.dismiss();

        	runOnUiThread(new Runnable() {

        	public void run() {

        		if(Integer.parseInt(hasil) == 1){ 

                    Toast.makeText(Daftar.this, "Registrasi Berhasil, anda dapat login untuk merubah data jika diperlukan", 1).show(); 
                    Intent i = new Intent(Daftar.this,Login.class);
    				startActivity(i);
              
                }else{ 

                    Toast.makeText(Daftar.this, "Registrasi Gagal", 1).show(); 

                } 
        	}

        	});

        	}

    }
	
	//mulai iki
		public class GetLocationTask extends AsyncTask<String, String, String> {

	        @Override
	        protected void onPreExecute() {
	        	super.onPreExecute();
	        	pDialog = new ProgressDialog(Daftar.this);
	        	pDialog.setMessage("Mendapatkan posisi anda...");
	        	pDialog.setIndeterminate(false);
	        	pDialog.setCancelable(true);
	        	pDialog.show();
	        	}
	        
	        protected String doInBackground(String... args) {
            while (progressBarStatus < 100) {

	              progressBarStatus = getLocation();

	              try {
	                Thread.sleep(3000);
	              } catch (InterruptedException e) {
	                e.printStackTrace();
	              }
	            }   
	            return null;
	        }
	        
	        protected void onPostExecute(String file_url) {
	        	pDialog.dismiss();
	        	runOnUiThread(new Runnable() {
	        	public void run() {
	        		
	        		
	        		if (progressBarStatus >= 100) {
						Toast.makeText(getApplicationContext(),
								"Lokasi mu latitude: " + getlatitude + " Longitude : " + getlongitude, Toast.LENGTH_LONG).show();
					DecimalFormat df = new DecimalFormat("#.######");
	            	lattitude.setText(String.valueOf(df.format(getlatitude)));
					longitude.setText(String.valueOf(df.format(getlongitude)));
	        		}
	        		else{
	        			Toast.makeText(getApplicationContext(),
								"Gagal Mendapatkan Posisi Anda", Toast.LENGTH_LONG).show();
	        		}
	        	}
	        	});
	        	 
	        	}   
	    }

}

