<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
    $user_id = $_POST["user_id"];
    
    $statement = mysqli_prepare($con, "UPDATE `User` SET `numberOfGigs` = `numberOfGigs` + 1 
		WHERE `User`.`user_id` = ?");
		
    mysqli_stmt_bind_param($statement, "i", $user_id);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $numberOfGigs);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
    }
    
    echo json_encode($response);
?>