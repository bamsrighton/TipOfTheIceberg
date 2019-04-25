<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
	$gig_id = $_POST["gig_id"];
	$user_id = $_POST["user_id"];
    $hours = $_POST["hours"];
	$tips = $_POST["tips"];
	$date = $_POST["date"];
	$netHourly = $_POST["netHourly"];
	$avgHourly = $_POST["avgHourly"];
	$dailyEarnings = $_POST["dailyEarnings"];
	$bestShiftDate = $_POST["bestShiftDate"];
	$bestShiftTotal = $_POST["bestShiftTotal"];
	$bestShiftHourly = $_POST["bestShiftHourly"];
	
	$state = mysqli_prepare($con, "UPDATE `Gig` SET `netHourly` = ?, `totalEarnings` = `totalEarnings` + ?, `totalHours` = `totalHours` + ?, 
		`bestShiftDate` = ?, `bestShiftTotal` = ?, `bestShiftHourly` = ?, `numberOfShifts` = `numberOfShifts` + 1 WHERE `Gig`.`gig_id` = ?");
		
    mysqli_stmt_bind_param($state, "dddsddi", $avgHourly, $dailyEarnings, $hours, $bestShiftDate, $bestShiftTotal, $bestShiftHourly, $gig_id);
    mysqli_stmt_execute($state);
	
	$statement2 = mysqli_prepare($con, "UPDATE `User` SET `totalHours` = `totalHours` + ?, 
		`totalEarnings` = `totalEarnings` + ?, `netHourly` = ? WHERE `User`.`user_id` = ?");
		
    mysqli_stmt_bind_param($statement2, "dddi",  $hours, $dailyEarnings, $avgHourly, $user_id);
    mysqli_stmt_execute($statement2);
	
    $statement = mysqli_prepare($con, "INSERT INTO Shift (gig_id, user_id, hours, tips, netHourly, date) 
		VALUES (?, ?, ?, ?, ?, ?)");
	
    mysqli_stmt_bind_param($statement, "iiddds", $gig_id, $user_id, $hours, $tips, $netHourly, $date);
    mysqli_stmt_execute($statement);
    
    $response = array();
    $response["success"] = true;  
    
    echo json_encode($response);
?>