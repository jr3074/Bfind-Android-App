package com.example.carikos;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.carikos.Daftar.RegisterTask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity{
	 EditText user;
	 EditText pass;
	 String hasil;
	 ProgressDialog pDialog;
	 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		user = (EditText)findViewById(R.id.User);
		pass = (EditText)findViewById(R.id.Pass);
		Button login = (Button)findViewById(R.id.loginBt);
		
		//untuk focus
		user.requestFocus();
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(user.getText().toString().length() == 0){
					user.setError(" Wajib Diisi");
					user.requestFocus();
				}else if(pass.getText().toString().length() == 0){
					pass.setError("Wajib diisi");
					pass.requestFocus();
				}
				else{
					//loginPemilik(user.getText().toString(), pass.getText().toString());
					LoginTask AsyncLogin = new LoginTask();
					AsyncLogin.execute();
					
				}
			}
		});
	}//end oncreate

	public class LoginTask extends AsyncTask<String, String, String> {

        @Override

        protected void onPreExecute() {

        	super.onPreExecute();

        	pDialog = new ProgressDialog(Login.this);

        	pDialog.setMessage("Sign in ke akun anda...");

        	pDialog.setIndeterminate(false);

        	pDialog.setCancelable(true);
        	pDialog.show();
        	}

        protected String doInBackground(String... args) {

        	String username = user.getText().toString();

			String password = pass.getText().toString();
			
			//buat object dari json class
			UserFunctions userFunction = new UserFunctions(); 
			 JSONObject json = userFunction.loginPemilik(username, password);

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

        			Intent i = new Intent(Login.this, Dasboard.class);
                    i.putExtra("username", user.getText().toString());
                    i.putExtra("password", pass.getText().toString());
                    startActivity(i);
              
                }else{ 

                    Toast.makeText(Login.this, "Login gagal, pastikan user dan password anda benar", 1).show(); 

                } 
        	}

        	});

        	}

    }
}