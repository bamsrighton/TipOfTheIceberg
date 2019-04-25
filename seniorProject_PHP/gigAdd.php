<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
	$user_id = $_POST["user_id"];
    $name = $_POST["name"];
    $position = $_POST["position"];
	$grossHourly = $_POST["grossHourly"];
	
	$statement = mysqli_prepare($con, "INSERT INTO Gig (user_id, name, position, bestShiftDate, grossHourly,
		netHourly, totalEarnings, totalHours, bestShiftTotal, bestShiftHourly, numberOfShifts) 
		VALUES (?, ?, ?, 1/1/2000, ?, ?, 0, 0, 0, 0, 0)");
	
    mysqli_stmt_bind_param($statement, "issdd", $user_id, $name, $position, $grossHourly, $grossHourly);
    mysqli_stmt_execute($statement);
	
	$statement1 = mysqli_prepare($con, "UPDATE `User` SET `numberOfGigs` = `numberOfGigs` + 1 
		WHERE `User`.`user_id` = ?");
		
    mysqli_stmt_bind_param($statement1, "i", $user_id);
    mysqli_stmt_execute($statement1);
		
    $response = array();
    $response["success"] = true; 
    
    echo json_encode($response);
?>