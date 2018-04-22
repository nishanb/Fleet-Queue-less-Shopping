<?php

$dbhost = 'localhost';
$dbuser = 'id5062867_nishan';
$dbpass = 'fleet123';

//connect to database
$connection = mysqli_connect($dbhost, $dbuser, $dbpass);

//check for connection
if (!$connection) {
    mysqli_close($connection);
    die('Could not connect: ');
}

//chose database
mysqli_select_db($connection, 'id5062867_fleet');

?>