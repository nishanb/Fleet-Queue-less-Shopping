package com.example.nishan.fleet.Fragment;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.nishan.fleet.Model.Product;
import com.example.nishan.fleet.Model.ProductAdapter;
import com.example.nishan.fleet.R;

import java.util.ArrayList;

/**
 * Created by nishan on 13-03-2018.
 */

@SuppressLint("ValidFragment")
public class ProductDetailFragment extends DialogFragment {
    ProductAdapter productAdapter;
    Product currentProduct;
    Button btnInc, btnDec,btnDelete;
    TextView tvQty;
    ArrayList<Product> products;
    int mPosition;


    @SuppressLint("ValidFragment")
    public ProductDetailFragment(Product product, ProductAdapter productAdapter, ArrayList<Product> products,int mPosition) {
        this.currentProduct = product;
        this.productAdapter = productAdapter;
        this.products=products;
        this.mPosition=mPosition;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_detail_fragment, container, false);
        getDialog().setTitle(currentProduct.getmName());

        btnInc = rootView.findViewById(R.id.btn_frag_inc);
        btnDec = rootView.findViewById(R.id.btn_frag_dec);
        tvQty = rootView.findViewById(R.id.tv_frag_qty);
        btnDelete=rootView.findViewById(R.id.btn_frag_remove);
        tvQty.setText(String.valueOf(currentProduct.getmQuantity()));


        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentProduct.incProductQty();
                productAdapter.notifyDataSetChanged();
                tvQty.setText(String.valueOf(currentProduct.getmQuantity()));

            }
        });

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentProduct.decProductQty();
                productAdapter.notifyDataSetChanged();
                tvQty.setText(String.valueOf(currentProduct.getmQuantity()));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.remove(mPosition);
                getDialog().dismiss();
                productAdapter.notifyDataSetChanged();


            }
        });


        return rootView;
    }

    /*@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                .setIcon(R.drawable.logo)
                .setView(R.layout.product_detail_fragment)
                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                }).create();

    }*/
}
