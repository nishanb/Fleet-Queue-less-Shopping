package com.example.nishan.fleet.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nishan.fleet.Activity.MyCartActivity;
import com.example.nishan.fleet.R;

import mehdi.sakout.fancybuttons.FancyButton;


public class CartFragment extends Fragment {
    int state=1;
    FancyButton mStartShopButton;
    Intent mCartIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_cart, container, false);

       mStartShopButton=view.findViewById(R.id.btn_start_shopping);
       mCartIntent=new Intent(getActivity(),MyCartActivity.class);

       mStartShopButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(mCartIntent);
           }
       });

        return view;
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Fleet");
    }











    /*
*/


   //TODO 1.handle result event 2.listView With Adapter 3.loaders fix



}
