package com.example.nishan.fleet.utils;

import android.net.ConnectivityManager;

/**
 * Created by nishan on 18-03-2018.
 */
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetWorkUtils {

    //API Base
    final static private String API_BASE_URL_GET_PRODUCT_DEATIL = "https://fleetspss.000webhostapp.com/api/get_product.php";

    final static private String PARAM_BARCODE_ID = "id";

    public static URL getProductDataUrl(String id) {

        Uri builtUri = Uri.parse(API_BASE_URL_GET_PRODUCT_DEATIL).buildUpon()
                .appendQueryParameter(PARAM_BARCODE_ID, id)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(NetWorkUtils.class.getSimpleName(),url.toString());
        return url;

    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}


   /* //method api parameters
    final static private String PARAM_BARCODE_ID = "id";
    final static private String PARAM_TITLE = "title";
    final static private String PARAM_SUBTITLE = "sub_title";
    final static private String PARAM_PRICE = "price";


    //method to from BuildAddProductQuery
    public static URL buildUrl(String barcodeId, String title, String subTitle, String price) {
        Uri builtUri = Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_BARCODE_ID, barcodeId)
                .appendQueryParameter(PARAM_TITLE, title)
                .appendQueryParameter(PARAM_SUBTITLE, subTitle)
                .appendQueryParameter(PARAM_PRICE, price)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }*/