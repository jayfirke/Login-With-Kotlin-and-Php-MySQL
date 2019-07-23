<?php  
date_default_timezone_set("Asia/Calcutta");
include 'dbcon.php';
$output = array();
$input = array();
$response = array();

if($_GET['method']=="login"){
		$username= $_GET["username"];
	        $password= $_GET["password"];

    	$sql = "select * from teacher_entry where username='$username' and password='$password'";
		$result = $conn->query($sql);
		
		if($result->num_rows > 0){
			$output['response'] = "false";
			$output['userid'] = "no_record_found";
			$output['name'] = "no_record_found";
			$output['username'] = "no_record_found";

			$row = $result->fetch_assoc();
			$output["response"] = trim("true");

			$output["userid"] = trim($row["uid"]);

			$name = str_replace(" ","_",$row['name']);
			$output["name"] = trim($name);

			$username = str_replace(" ","_",$row['username']);
			$output["username"] = trim($username);
		}else{
			$output['response'] = "false";
			$output['userid'] = "no_record_found";
			$output['name'] = "no_record_found";
			$output['username'] = "no_record_found";
		}
	echo json_encode($output);
	}