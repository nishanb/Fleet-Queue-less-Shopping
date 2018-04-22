<?php

//http://localhost:63342/fleet/api/add_product.php?id=100&title=apasra&sub_title=200+pages&price=20

require ("config.php");
require ("functions.php");

if(isset($_GET['id'])) {

    $id = $_GET['id'];
    $title = $_GET['title'];
    $sub_title = $_GET['sub_title'];
    $price = $_GET['price'];

    add_product($id,$title,$sub_title,$price);
}else{
    echo("error");
}

