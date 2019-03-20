<?php
/**
 * Created by PhpStorm.
 * User: nishan
 * Date: 19-03-2018
 * Time: 06:56 PM
 */

//http://localhost/fleet/api/get_product.php?id=8906028930805

require ("functions.php");

    if(isset($_GET['id'])) {
        get_product_details($_GET['id']);
    }
    else{
        generate_error();
    }


