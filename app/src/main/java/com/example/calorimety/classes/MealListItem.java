package com.example.calorimety.classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.calorimety.database.MealItem;

import java.util.ArrayList;
import java.util.List;

public class MealListItem implements Parcelable {
    private final String meal_name;
    private final float totalWeight;
    private final List<MealItem> productList;




    public MealListItem(String meal_name, float totalWeight, List<MealItem> productList) {
        this.meal_name = meal_name;
        this.totalWeight = totalWeight;
        this.productList = productList;
    }

    protected MealListItem(Parcel in) {
        productList = new ArrayList<>();
        meal_name = in.readString();
        totalWeight = in.readFloat();
        in.readList(productList, MealItem.class.getClassLoader());
    }

    public static final Creator<MealListItem> CREATOR = new Creator<MealListItem>() {
        @Override
        public MealListItem createFromParcel(Parcel in) {
            return new MealListItem(in);
        }

        @Override
        public MealListItem[] newArray(int size) {
            return new MealListItem[size];
        }
    };

    public String getMeal_name() {
        return meal_name;
    }

    public List<MealItem> getProductList() {
        return productList;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(meal_name);
        parcel.writeFloat(totalWeight);
        parcel.writeList(productList);
    }
}
