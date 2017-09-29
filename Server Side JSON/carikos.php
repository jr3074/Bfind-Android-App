<?php

/**
File ini untuk menghandle API Request
hasilnya di encoda dalam bentuk JSON
  /**
* check for POST request
*/
if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // Tag
    $tag = $_POST['tag'];

    // Database Handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();

    // Response--> data yang akan dikembalikan
    $response = array("tag" => $tag, "success" => 0);
   
    // Cek tipe tag
    if ($tag == 'login') {
        // Tangkap data yang dikirim dari android
        $username = $_POST['username'];
        $password = $_POST['password'];

        // Cek user
        $pemilik = $db->loginPemilik($username, $password);
        if ($pemilik != false) {
            // User ditemukan
            $response["success"] = 1;
            echo json_encode($response);
        } else {
            // User tidak ditemukan
            $response["success"] = 0;
            echo json_encode($response);
        }
    }
    else if ($tag == 'register') {
        // Tangkap data yang dikirim dari android
        $username = $_POST['username'];
        $password = $_POST['password'];
        $nama = $_POST['nama'];
        $telepon = $_POST['telepon'];
        $alamat = $_POST['alamat'];
        $jumlah_kamar = $_POST['jumlah_kamar'];
        $harga = $_POST['harga'];
        $latitude = $_POST['latitude'];
        $longitude = $_POST['longitude'];
        $status = $_POST['status'];
        $jenis = $_POST['jenis'];


        // cek user sudah ada atau belum
        if ($db->isPemilikExisted($username)) {
            // User sudah ada
            $response["success"] = 0;
            $response["error_msg"] = "User sudah ada";
            echo json_encode($response);
        } else {
            // Simpan user baru jika belum ada
            $pemilik = $db->registerPemilik($username, $password, $nama, $telepon, $alamat, $jumlah_kamar, $harga, $latitude, $longitude, $status, $jenis);
            if ($pemilik) {
                // Registrasi Berhasil
                $response["success"] = 1;
                echo json_encode($response);
            } else {
                // Registrasi Gagal
                $response["success"] = 0;
                echo json_encode($response);
            }
        }
    }
    
    
    else if ($tag == 'edit') {
        // Tangkap data yang dikirim dari android
        $username = $_POST['username'];
        $password = $_POST['password'];
        $nama = $_POST['nama'];
        $telepon = $_POST['telepon'];
        $alamat = $_POST['alamat'];
        $jumlah_kamar = $_POST['jumlah_kamar'];
        $harga = $_POST['harga'];
        $latitude = $_POST['latitude'];
        $longitude = $_POST['longitude'];
        $status = $_POST['status'];
        $jenis = $_POST['jenis'];
        

       
        // Simpan Perubahan
        $pemilik = $db->editDataPemilik($username, $password, $nama, $telepon, $alamat, $jumlah_kamar, $harga, $latitude, $longitude, $status, $jenis);
        if ($pemilik) {
            // Edit Berhasil
            $response["success"] = 1;
            echo json_encode($response);
        } else {
            // Edit Gagal
            $response["success"] = 0;
            echo json_encode($response);
        }
        
    }
    
    else if ($tag == 'getdatapemilik') {
        // Tangkap data yang dikirim dari android
        $username = $_POST['username'];
       
       
        // dapatkan data pemilik
        $result = mysql_query("select * FROM PEMILIK WHERE USERNAME = '$username'");
		if (!empty($result)) {
			if (mysql_num_rows($result) > 0) { 
 
				$res = mysql_fetch_array($result);            
				$product = array();
				$product["username"] = $res["USERNAME"];				
				$product["password"] = $res["PASSWORD"];
				$product["nama"] = $res["NAMA"];
				$product["telepon"] = $res["TELEPON"];
				$product["alamat"] = $res["ALAMAT"];
				$product["jumlah_kamar"] = $res["JUMLAH_KAMAR"];
				$product["harga"] = $res["HARGA"];
				$product["latitude"] = $res["LATITUDE"];
				$product["longitude"] = $res["LONGITUDE"];
				$product["status"] = $res["STATUS"];
				$product["jenis"] = $res["JENIS"];
				
				
				// success return
				$response["success"] = 1; 
				// data return
				$response["product"] = array();
 
				array_push($response["product"], $product);
 
				// echoing JSON response
				echo json_encode($response);
			} else {
            // no product found
            $response["success"] = 0;
            $response["message"] = "No product found";
 
            // echo no users JSON
            echo json_encode($response);
			}
		} else {
		// no product found
        $response["success"] = 0;
        $response["message"] = "No product found";
 
        // echo no users JSON
        echo json_encode($response);
		}
        
    }
       
    else if ($tag == 'getkos') {
        // Tangkap data yang dikirim dari android
        $latitude = $_POST['latitude'];
        $longitude = $_POST['longitude'];
        $jarak = $_POST['jarak'];
       
        // dapatkan data terdekat
        $result = $db->getKos($latitude, $longitude, $jarak);
        if (!empty($result)) {
        // check for empty result
			if (mysql_num_rows($result) > 0) {
			    $response["products"] = array();
 
				while ($row = mysql_fetch_array($result)) {
				$product = array();
				$product["nama"] = $row["NAMA"];
				$product["telepon"] = $row["TELEPON"];
				$product["alamat"] = $row["ALAMAT"];
				$product["jumlah_kamar"] = $row["JUMLAH_KAMAR"];
				$product["harga"] = $row["HARGA"];
				$product["latitude"] = $row["LATITUDE"];
				$product["longitude"] = $row["LONGITUDE"];
			        $product["jenis"] = $row["JENIS"];
				
				
				// push single product into final response array
			        array_push($response["products"], $product);
			        }
				
				// success return
				$response["success"] = 1; 

 
				// echoing JSON response
				echo json_encode($response);
			} else {
            // no product found
				$response["success"] = 0;
				$response["message"] = "No data found";
 
            // echo no users JSON
				echo json_encode($response);
			}
		} else {
        // no product found
        $response["success"] = 0;
        $response["message"] = "No data found";
 
        // echo no users JSON
        echo json_encode($response);
		}
        
    }
   
    else {
        echo "Invalid Request";
    }
    
	
	
} else {
    echo "Access Denied";
}
?>