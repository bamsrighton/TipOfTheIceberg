<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
    $user_id = $_POST["user_id"];
	$name = $_POST["name"];
	$position = $_POST["position"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM Gig WHERE user_id = ? AND name = ? AND position = ?");
    mysqli_stmt_bind_param($statement, "i", $user_id, $name, $position);
    mysqli_stmt_execute($statement);
    
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $gig_id, $user_id, $name, $position, $grossHourly,
		$netHourly, $totalEarnings, $totalHours, $bestShiftDate, $bestShiftTotal, $bestShiftHourly, 
		$numberOfShifts);
    
    $response = array();
    $response["success"] = false;  
    
    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
		$response["gig_id"] = $gig_id;
    }
    
    echo json_encode($response);
?>