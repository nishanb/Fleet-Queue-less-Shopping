package com.example.nishan.fleet.Model;

/**
 * Created by nishan on 10-03-2018.
 * Product Model added in cart
 */

public class Product {
    /*
    *varibales to store  data
    */
    private String mProductId, mName, mDescription;
    private int mQuantity, mPrice;

    //public Constructor
    public Product(String mProductId, String mName, String mDescription, int mQuantity, int mPrice) {
        this.mProductId = mProductId;
        this.mName = mName;
        this.mDescription = mDescription;
        this.mQuantity = mQuantity;
        this.mPrice = mPrice;
    }

    //getter and setter methods
    public String getProductId() {
        return this.mProductId;
    }

    public void setmProductId(String Productid) {
        this.mProductId = Productid;
    }


    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public int getmPrice() {
        return mPrice*mQuantity;
    }

    public void setmPrice(int mPrice) {
        this.mPrice = mPrice;
    }

    public void incProductQty() {
        this.mQuantity = this.mQuantity + 1;
    }

    public void decProductQty() {
        if (!(this.mQuantity < 2))
            this.mQuantity = this.mQuantity - 1;
    }

}
