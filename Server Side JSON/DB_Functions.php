<?php

class DB_Functions {

    private $db;
    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // Koneksi ke database
        $this->db = new DB_Connect();
        $this->db->connect();
    }

    // destructor
    function __destruct() {
       
    }
    
    public function to_array($data){
		$result = array();
		while($hasil = mysql_fetch_array($data)){
			$result[] = $hasil;
		}
		return $result;
	}
   
    public function registerPemilik($username, $password, $nama, $telepon, $alamat, $jumlah_kamar, $harga, $latitude, $longitude, $status, $jenis) {
        $result = mysql_query("INSERT INTO PEMILIK(USERNAME, PASSWORD, NAMA, TELEPON, ALAMAT, JUMLAH_KAMAR, HARGA, LATITUDE, LONGITUDE, STATUS, JENIS) VALUES('$username', '$password', '$nama', '$telepon', '$alamat', '$jumlah_kamar', '$harga', '$latitude', '$longitude', '$status', '$jenis')");
        // Cek registrasi berhasil
        if ($result) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cek user di database
     */
    public function loginPemilik($username, $password) {
        $result = mysql_query("SELECT * FROM PEMILIK WHERE USERNAME = '$username' AND PASSWORD = '$password'") or die(mysql_error());
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
         return true;
        } else {
            // User tidak ditemukan
            return false;
        }
    }

    /**
     * Cek user exist atau tidak
     */
    public function isPemilikExisted($username) {
        $result = mysql_query("SELECT * from PEMILIK WHERE USERNAME = '$username'");
        $no_of_rows = mysql_num_rows($result);
        if ($no_of_rows > 0) {
            // user existed
            return true;
        } else {
            // user not existed
            return false;
        }
    }
    
    public function getDataPemilik($username) {
        $result = mysql_query("select * FROM PEMILIK WHERE USERNAME = '$username'");
        return $result;
    }
    
    public function editDataPemilik($username, $password, $nama, $telepon, $alamat, $jumlah_kamar, $harga, $latitude, $longitude, $status, $jenis) {
        $result = mysql_query("update PEMILIK set PASSWORD = '$password', NAMA = '$nama', TELEPON = '$telepon', ALAMAT = '$alamat', JUMLAH_KAMAR = '$jumlah_kamar', HARGA = '$harga', LATITUDE = '$latitude', LONGITUDE = '$longitude', STATUS = '$status', JENIS = '$jenis' where USERNAME = '$username'");
        return $result;
    }
    
    public function getKos($latitude, $longitude, $jarak) {
    	$kmtomiles = 0.621371;
    	$fixedjarak = floatval($jarak);
    	$miles = $fixedjarak*$kmtomiles;
        $result = mysql_query("select NAMA, TELEPON, ALAMAT, JUMLAH_KAMAR, HARGA, LATITUDE, LONGITUDE, JENIS, ( 3959 * acos( cos( radians('$latitude') ) * cos( radians( LATITUDE ) ) * cos( radians( LONGITUDE ) - radians('$longitude') ) + sin( radians('$latitude') ) * sin( radians( LATITUDE ) ) ) ) AS DISTANCE
FROM PEMILIK
WHERE latitude IS NOT NULL
AND longitude IS NOT NULL
AND STATUS = 'Kosong'
HAVING DISTANCE < $miles
ORDER BY DISTANCE ASC");
        return $result;
    }
    
    public function getAll(){
		$data = mysql_query("select * from PEMILIK;") or die ("query error");
		return $data;
	}
	
	
      
}
?>