package com.example.nishan.fleetadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText mBarcodeId, mProductName, mProductDescription, mPrice;
    Button mScanBtn, mSubmitBtn;
    IntentIntegrator integrator;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            getSupportActionBar().setTitle("Add Product");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //view reference
        mBarcodeId = findViewById(R.id.tv_barcode_id);
        mProductName = findViewById(R.id.tv_product_name);
        mProductDescription = findViewById(R.id.tv_product_description);
        mPrice = findViewById(R.id.tv_price);
        mScanBtn = findViewById(R.id.btn_scan);
        mSubmitBtn = findViewById(R.id.btn_submit);

        //object initializations
        progressDialog=new ProgressDialog(this);
        //progressDialog.setTitle("Adding");
        progressDialog.setMessage("Adding Product");
        progressDialog.setCancelable(false);


        //saner object properties
        integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Scan Product barcode");
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.setOrientationLocked(false);

        //Click Listeners
        mScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.initiateScan();
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                URL requestUrl = null;
                if (validate())
                {   //Build URL upon Success
                    requestUrl = NetworkUtils.buildUrl(mBarcodeId.getText().toString(),
                            mProductName.getText().toString(),
                            mProductDescription.getText().toString(),
                            mPrice.getText().toString());
                Log.v("NISHAN", requestUrl.toString());
                new AddProductTask().execute(requestUrl);
            }
            }

        });
    }


    // Get the BarCode Scan Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                mBarcodeId.setText(result.getContents());
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Error Reading BarCode", Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean validate() {
        Toast inputError = Toast.makeText(this, "Input Fields Cant be Empty", Toast.LENGTH_SHORT);

        if (mBarcodeId.getText().toString().isEmpty() || mProductName.getText().toString().isEmpty() || mProductDescription.getText().toString().isEmpty() || mPrice.getText().toString().isEmpty()) {
            inputError.show();
            return false;
        }
        return  true;
    }

    class AddProductTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL request=urls[0];
            String response=null;
            try {
                response=NetworkUtils.getResponseFromHttpUrl(request);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            clearForm();
            if(s!=null&&!s.equals("")){
                Toast.makeText(MainActivity.this,"Product Added",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"Error while Adding",Toast.LENGTH_SHORT).show();
            }


        }
    }

    public void clearForm(){
        mProductDescription.setText("");
        mBarcodeId.setText("");
        mProductName.setText("");
        mPrice.setText("");

    }

}
