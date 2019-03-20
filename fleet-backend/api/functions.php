<?php

require ("config.php");

function add_product($id,$title,$sub_title,$price){
    $sql="INSERT INTO `products`(`barcode_id`, `title`, `sub_title`, `price`) VALUES ('$id','$title','$sub_title','$price')";
    exec_sql_no_result($sql);
}

function get_product_details($barcodeId){
    $sql="SELECT `id`, `barcode_id`, `title`, `sub_title`, `price` FROM `products` WHERE `barcode_id`='$barcodeId'";
    $object=exec_sql_obj_result($sql);

    if($object==null){
        //generate_error();
        return;
    }
    echo(json_encode($object));
}

function exec_sql_no_result($sql){
    global $connection;
    mysqli_query($connection,$sql);

    if(mysqli_insert_id($connection)!=0){
        echo ("success");
    }
  
}

function exec_sql_obj_result($sql){
    global $connection;
    $result=mysqli_query($connection,$sql);
    $object=mysqli_fetch_object($result);
    return $object;
}

function generate_error(){
    echo(json_encode(array("status"=>"error")));
}

?>