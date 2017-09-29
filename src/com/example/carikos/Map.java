package com.example.carikos;

import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.carikos.Daftar.GetLocationTask;
import com.example.carikos.Dasboard.GetDataTask;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.MyLocationOverlay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.ProgramVertexFixedFunction.Constants;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Map extends Activity {
	JSONArray product = null;
	
	Double[] latitude = new Double[100];
	Double[] longitude = new Double[100];
	String[] nama = new String[100];
	String[] telepon = new String[100];
	String[] alamat = new String[100];
	String[] jumlah_kamar = new String[100];
	String[] harga = new String[100];
	String[] jenis = new String[100];
	
	
	int counter; //untuk kounter
	String lat,longi;
	EditText radius;
	String jarakRadius;
    // Google Map
    private GoogleMap googleMap;
 // latitude and longitude
	
	GpsService	gps;
	private ProgressDialog pDialog;
	double getlatitude;//iki
    double getlongitude;//iki
    ProgressDialog progressBar; //iki
	int progressBarStatus=0; //iki
	Handler progressBarHandler = new Handler();//iki
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
 
        try {
            // Loading map
            initilizeMap();          
            dapatPosisi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //button cari posisi sekarang
       
        
       //ambil radius
       
        //set nilai radius ke tipe data string
       
       //button cari kos2-an--------------------------------------------------------- !
       Button cariKos = (Button)findViewById(R.id.btCariKosAnda);
       cariKos.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			radius = (EditText)findViewById(R.id.txtRadius);
			jarakRadius = radius.getText().toString();
			
			googleMap.clear();
			
			 GetCariTask AsyncCariKos = new GetCariTask();
			 AsyncCariKos.execute();
			 
		}
       	});    
    }
 
    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
        	
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            
            googleMap.setMyLocationEnabled(true); //untuk menentukan lokasi sekarang
         
            
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Maaf Google Map Belum Tersedia", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
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
    
  //mulai iki
  		public class GetLocationTask extends AsyncTask<String, String, String> {

  	        @Override
  	        protected void onPreExecute() {
  	        	super.onPreExecute();
  	        	pDialog = new ProgressDialog(Map.this);
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
  	            		
  					//----------------
  					LatLng latlong = new LatLng(getlatitude, getlongitude);
  					googleMap.setMyLocationEnabled(true); //untuk menentukan lokasi sekarang
  					CameraPosition cameraPosition = new CameraPosition.Builder().target(
  			                new LatLng(getlatitude, getlongitude)).zoom(15).build();
  					
  					googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
  					
  	        		}
  	        		else{
  	        			Toast.makeText(getApplicationContext(),
  								"Gagal Mendapatkan Posisi Anda", Toast.LENGTH_LONG).show();
  	        		}
  	        	}
  	        	});
  	        	 
  	        	}   
  	    }
  		
  		//asyntask untuk cari kosan---------------------------------------------------
  		public class GetCariTask extends AsyncTask<String, String, String> {
  	        @Override
  	        protected void onPreExecute() {
  	        	super.onPreExecute();
  	        	pDialog = new ProgressDialog(Map.this);
  	        	pDialog.setMessage("Sedang Menampilkan Kos-Kosan...");
  	        	pDialog.setIndeterminate(false);
  	        	pDialog.setCancelable(true);
  	        	pDialog.show();
  	        	}

  	        protected String doInBackground(String... args) {
  				//buat object dari json class
  				UserFunctions userFunction = new UserFunctions(); 
  				
  				 lat = String.valueOf(getlatitude);
  				 longi = String.valueOf(getlongitude);
  				
  				 JSONObject json = userFunction.getKos(lat, longi, jarakRadius);
  				 try {
  						counter = 0;
  		                 product = json.getJSONArray("products");
  		                for (int i = 0; i < product.length(); i++) {
  		                	
  		                	JSONObject c = product.getJSONObject(i);
  		                	// Storing each json item in variable
  	                        
  	                        nama[i] = c.getString("nama");
  	                        telepon[i] = c.getString("telepon");
  	                        alamat[i] = c.getString("alamat");
  	                        jumlah_kamar[i] = c.getString("jumlah_kamar");
  	                        harga[i] = c.getString("harga");
  	                        latitude[i] = Double.parseDouble(c.getString("latitude"));
	                        longitude[i] = Double.parseDouble(c.getString("longitude"));
	                        jenis[i] = c.getString("jenis");
  	                        counter++;
  	                        
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
	  	        		
	  	        		for(int i= 0 ; i<counter ; i++){
		  	        			String data="Alamat : " + alamat[i];
				  	            data+=";Telepon: " + telepon[i];
				  	            data+=";Jumlah Kamar : " + jumlah_kamar[i];
				  	            data+=";Harga : " + harga[i];
				  	          data+=";Untuk : " + jenis[i];
		  	                   
				  	            googleMap.setMyLocationEnabled(true); //untuk menentukan lokasi sekarang
			  	                // create marker
			  	               	MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude[i], longitude[i])).title(nama[i]).snippet(data); 	 
			  	               	// adding marker
			  	               	googleMap.addMarker(marker);
			  	               	
			  	               	//batas --------------------------------
			  	               	
			  	               	googleMap.setInfoWindowAdapter(iwa);
			  	              
			  	               	//batas --------------------------------
	  	        		}
	  	        		if(counter==0){
	  	        			Toast.makeText(getApplicationContext(),
	  								"Maaf, tidak ada kos yang tersedia dalam data kami", Toast.LENGTH_LONG).show();
	  	        		}else{
	  	        		Toast.makeText(getApplicationContext(),
  								" "+counter+" Kos Ditemukan", Toast.LENGTH_LONG).show();
	  	        		}
	  	        	}
  	        	});
  	        	
  	        }
  	    }
  		
  		//methode dapat kos
  		public void dapatPosisi(){
  			gps = new GpsService(Map.this);

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
  		
  		//info window
  		InfoWindowAdapter iwa=new InfoWindowAdapter() {
  			@Override
  			public View getInfoWindow(Marker arg0) {
  			return null;
  			}
  			@Override
  			public View getInfoContents(Marker arg0) {
  			View view=getLayoutInflater().inflate(R.layout.info_window, null);
  			final String[] hasil=arg0.getSnippet().split(";");
  			TextView nama=(TextView) view.findViewById(R.id.nama);
  			TextView alamat=(TextView) view.findViewById(R.id.alamat);
  			TextView telepon=(TextView) view.findViewById(R.id.telepon);
  			TextView jumlah_kamar=(TextView) view.findViewById(R.id.jumlah_kamar);
  			TextView harga=(TextView) view.findViewById(R.id.harga);
  			TextView jeniss=(TextView) view.findViewById(R.id.jenis);
  			
  			nama.setText(arg0.getTitle());
  			alamat.setText(hasil[0]);
  			telepon.setText(hasil[1]);
  			jumlah_kamar.setText(hasil[2]);
  			harga.setText(hasil[3]);
  			jeniss.setText(hasil[4]);
  			return view;
  			}
  			};
}