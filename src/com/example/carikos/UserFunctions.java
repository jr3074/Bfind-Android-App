package com.example.carikos;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class UserFunctions {
	   
    private JSONParser jsonParser;
   
    private static String loginURL = "http://android.muhammadhernawan.com/carikos.php";
   
    private static String login_tag = "login";
    private static String register_tag = "register";
	private static String edit_tag = "edit";
	private static String getdatapemilik_tag = "getdatapemilik";
	private static String getkos_tag = "getkos";
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
	
	/**
     * function make Login Request
     * @param tag
     * @param username
     * @param password
     * */
   
    public JSONObject loginPemilik(String username, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

        return json;
    }
   
    /**
     * function create Register
     * @param tag
     * @param username
     * @param dan lain-lain
     * */
    public JSONObject registerPemilik(String username, String password, String nama, String telepon, String alamat, String jumlah_kamar, String harga, String latitude, String longitude, String status, String jenis){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("nama", nama));
		params.add(new BasicNameValuePair("telepon", telepon));
        params.add(new BasicNameValuePair("alamat", alamat));
		params.add(new BasicNameValuePair("jumlah_kamar", jumlah_kamar));
        params.add(new BasicNameValuePair("harga", harga));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("jenis", jenis));
		
       
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
	
	/**
     * function edit data pemilik
     * @param tag
     * @param password
     * @param dan lain-lain
     * */
    public JSONObject editPemilik(String username, String password, String nama, String telepon, String alamat, String jumlah_kamar, String harga, String latitude, String longitude, String status, String jenis){
        // Building Parameters
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", edit_tag));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("nama", nama));
		params.add(new BasicNameValuePair("telepon", telepon));
        params.add(new BasicNameValuePair("alamat", alamat));
		params.add(new BasicNameValuePair("jumlah_kamar", jumlah_kamar));
        params.add(new BasicNameValuePair("harga", harga));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("status", status));
        params.add(new BasicNameValuePair("jenis", jenis));
		
       
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
	
	/**
     * function get data pemilik by username
     * @param tag
     * @param username
     * */
    public JSONObject getPemilik(String username){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", getdatapemilik_tag));
        params.add(new BasicNameValuePair("username", username));
       
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
    
    /**
     * function get KOS by latitude
     *
     * */
    public JSONObject getKos(String latitude, String longitude, String jarak){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", getkos_tag));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("jarak", jarak));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        // return json
        return json;
    }
    
}