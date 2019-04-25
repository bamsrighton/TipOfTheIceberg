<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
    $user_id = $_POST["user_id"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM User WHERE user_id = ?");
    mysqli_stmt_bind_param($statement, "i", $user_id);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $username, $password, $totalHours, $totalEarnings, 
		$netHourly, $numberOfGigs);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
		$response["user_id"] = $user_id;
        $response["username"] = $username;
        $response["password"] = $password;
		$response["totalHours"] = $totalHours;
		$response["totalEarnings"] = $totalEarnings;
		$response["netHourly"] = $netHourly;
		$response["numberOfGigs"] = $numberOfGigs;
    }
    
    echo json_encode($response);
?>