package com.example.nishan.fleet.Activity;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nishan.fleet.Model.Product;
import com.example.nishan.fleet.Model.ProductAdapter;
import com.example.nishan.fleet.Fragment.ProductDetailFragment;
import com.example.nishan.fleet.R;
import com.example.nishan.fleet.utils.NetWorkUtils;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {

    public ArrayList<Product> products;
    public FloatingActionButton fabAddToCart;
    public IntentIntegrator barCodeHelper;
    public LinearLayout filledCartLayout;
    public RelativeLayout emptyCartLayout;
    //public LinearLayout mCheckOutBtn;
    ListView listView;
    ProductAdapter productAdapter;
    Button mCheckOut, mSubTotal;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        //Setting up title of APP_BAR
        try {
            //getActionBar().setTitle(R.string.title_activity_cart);
            getSupportActionBar().setTitle("My Cart");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get  view references here
        filledCartLayout = findViewById(R.id.layout_filled);
        emptyCartLayout = findViewById(R.id.layout_empty);
        fabAddToCart = findViewById(R.id.fab_add_to_cart);

        mCheckOut = findViewById(R.id.btn_checkout);
        mSubTotal = findViewById(R.id.tv_subtotal);

        mCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(),"i am going to checkout",Toast.LENGTH_SHORT).show();
                Intent i=new Intent(MyCartActivity.this,PaymentActivity.class);
                startActivity(i);
            }
        });

        showEmptyCart();

        //productList = new ArrayList<>();

        //set properties of barcode scanner
        barCodeHelper = new IntentIntegrator(this);
        barCodeHelper.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        barCodeHelper.setPrompt("SCAN PRODUCT BARCODE");
        barCodeHelper.setBeepEnabled(true);
        barCodeHelper.setBarcodeImageEnabled(true);

        //progress Dialog initialization
        progressDialog = new ProgressDialog(this);
        //progressDialog.setTitle("Adding");
        progressDialog.setMessage("Adding Product to cart");
        progressDialog.setCancelable(false);

        Log.v(MyCartActivity.class.getSimpleName(), "OnCreate ->MyCart Activity");

        //  onclick Listeners-
        fabAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barCodeHelper.initiateScan();
            }
        });


        //Product list
        products = new ArrayList<>();

        productAdapter = new ProductAdapter(this, products);

        listView = findViewById(R.id.lv_product_list);

        listView.setAdapter(productAdapter);

        listView.setItemsCanFocus(true);

        Log.v(MyCartActivity.class.getSimpleName(), "Finished Setting Adapter");

        //listener to set view
        productAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                showPrice();
                decideView();
            }
        });

        //listView data adapter onclick listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.v(MyCartActivity.class.getSimpleName(), "item click listener inside view");

                //Current Product
                Product clickedProduct = products.get(i);
                FragmentManager fm = getFragmentManager();
                ProductDetailFragment productDetailFragment = new ProductDetailFragment(clickedProduct, productAdapter, products, i);
                productDetailFragment.show(fm, "Details");

            }
        });


    }

    //get the result back from barcode reader
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //on error
            } else {
                //on success
                URL apiUrl = NetWorkUtils.getProductDataUrl(result.getContents());
                new addProductAsyncTask().execute(apiUrl);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to add product to cart
    private void addProduct(String JSONText) {

        String productId, name, description, price;

        try {
            JSONObject jsonObject = new JSONObject(JSONText);

            productId = jsonObject.getString("barcode_id");
            name = jsonObject.getString("title");
            description = jsonObject.getString("sub_title");
            price = jsonObject.getString("price");

            products.add(new Product(productId, name, description, 1, Integer.valueOf(price)));


            Log.v(MyCartActivity.class.getSimpleName(), "New Product Added :upc->" + productId);

        } catch (Exception e) {
            //Log.v(MyCartActivity.class.getSimpleName(), "Error while adding Product");
            //e.printStackTrace();
        }

    }

    //show empty cart when array is null
    public void showEmptyCart() {
        emptyCartLayout.setVisibility(RelativeLayout.VISIBLE);
        filledCartLayout.setVisibility(LinearLayout.INVISIBLE);
    }

    //show filled cart when item is present
    public void showFilledCart() {
        emptyCartLayout.setVisibility(RelativeLayout.INVISIBLE);
        filledCartLayout.setVisibility(LinearLayout.VISIBLE);
    }

    //show filled cart or empty cart
    public void decideView() {

        if (products.size() <= 0) {
            showEmptyCart();
        } else {
            showFilledCart();
        }
    }

    //method to calculate price
    public int calculateTOtalPrice() {
        int total = 0;
        for (Product product : products) {
            total += product.getmPrice();
        }
        return total;
    }

    //method to set the price
    public void showPrice() {

        mSubTotal.setText("TOTAL " + String.valueOf(calculateTOtalPrice()));
    }

    //fetches the data from api and create a new product object
    class addProductAsyncTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = NetWorkUtils.getResponseFromHttpUrl(urls[0]);

            } catch (IOException e) {
                e.printStackTrace();
            }
            addProduct(response);

            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            productAdapter.notifyDataSetChanged();
            if(s==null)
                Toast.makeText(getApplicationContext(),"Product Not Listed",Toast.LENGTH_SHORT).show();

        }
    }

}




