<?php
    $con = mysqli_connect("mysql.hostinger.com", "u189235535_bam", "Br1ghton", "u189235535_tip");
    
    $user_id = $_POST["user_id"];
	$num = $_POST["num"];
    
    $statement = mysqli_prepare($con, "SELECT * FROM Gig WHERE user_id = ? LIMIT ?,1");
    mysqli_stmt_bind_param($statement, "ii", $user_id, $num);
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
		$response["user_id"] = $user_id; 
		$response["name"] = $name;
		$response["position"] = $position;
		$response["grossHourly"] = $grossHourly;
		$response["netHourly"] = $netHourly;
		$response["totalEarnings"] = $totalEarnings;
		$response["totalHours"] = $totalHours;
		$response["bestShiftDate"] = $bestShiftDate; 
		$response["bestShiftTotal"] = $bestShiftTotal;
		$response["bestShiftHourly"] = $bestShiftHourly; 
		$response["numberOfShifts"] = $numberOfShifts;
    }
    
    echo json_encode($response);
?>