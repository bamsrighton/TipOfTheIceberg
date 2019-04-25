<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
    $username = $_POST["username"];
    $password = $_POST["password"];
    $statement = mysqli_prepare($con, "INSERT INTO User (username, password, totalHours, totalEarnings, 
		netHourly, numberOfGigs) VALUES (?, ?, 0, 0, 0, 0)");
	
    mysqli_stmt_bind_param($statement, "ss", $username, $password);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>