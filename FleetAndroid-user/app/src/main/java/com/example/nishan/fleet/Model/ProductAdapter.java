package com.example.nishan.fleet.Model;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nishan.fleet.R;

import java.util.ArrayList;

/**
 * Created by nishan on 10-03-2018.
 */

public class ProductAdapter extends ArrayAdapter<Product> {

    //constructor
    public ProductAdapter(Activity Context, ArrayList<Product> item) {
        super(Context, 0, item);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem=convertView;

        //creating reusable layout
        if(listViewItem==null) {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.cart_item, parent, false);
        }

        Product currentProduct=getItem(position);

        //setting product name
        TextView productName=listViewItem.findViewById(R.id.tv_product_name);
        productName.setText(currentProduct.getmName());

        //Setting Description
        TextView productDescription=listViewItem.findViewById(R.id.tv_description);
        productDescription.setText(currentProduct.getmDescription());

        //Setting Price
        TextView productPrice=listViewItem.findViewById(R.id.tv_price);
        productPrice.setText(String.valueOf(currentProduct.getmPrice())+"Rs");

        //Setting Quantity
        TextView productQuantity=listViewItem.findViewById(R.id.tv_qty);
        productQuantity.setText("Qty :"+String.valueOf(currentProduct.getmQuantity()));


        return listViewItem;
    }

}
