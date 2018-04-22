package com.example.nishan.fleetadmin;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    //API Base
    final static private String API_BASE_URL = "https://fleetspss.000webhostapp.com/api/add_product.php";

    //method api parameters
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