package com.example.shoppinglist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {

    // region 0. Constants

    private static final int DEFAULT_IMAGE_RESOURCE = 0;
    private static final String DEFAULT_NAME = "__NAME";
    private static final String DEFAULT_DESCRIPTION = "__DESCRIPTION";
    private static final int DEFAULT_NUMBER_OF_ITEMS = 0;

    // endregion

    // region 1. Decl and Init

    private int imageResource;
    private String textName;
    private String textDescription;
    private int numberOfItems;

    // endregion

    // region 2. Constructor

    public Item() {
        this.imageResource = DEFAULT_IMAGE_RESOURCE;
        this.textName = DEFAULT_NAME;
        this.textDescription = DEFAULT_DESCRIPTION;
        this.numberOfItems = DEFAULT_NUMBER_OF_ITEMS;
    }

    public Item(int imageResource, String textName, String textDescription, int numberOfItems) {
        this.imageResource = imageResource;
        this.textName = textName;
        this.textDescription = textDescription;
        this.numberOfItems = numberOfItems;
    }

    protected Item(Parcel parcel){
        this.imageResource = parcel.readInt();
        this.textName = parcel.readString();
        this.textDescription = parcel.readString();
        this.numberOfItems = parcel.readInt();
    }

    // endregion

    // region 3. Getters and Setters

    public boolean isDefault(){
        return (this.imageResource == DEFAULT_IMAGE_RESOURCE
                && this.textName.equals(DEFAULT_NAME)
                && this.textDescription.equals(DEFAULT_DESCRIPTION)
                && this.numberOfItems == DEFAULT_NUMBER_OF_ITEMS);
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTextName() {
        return textName;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setTextName(String textName) {
        this.textName = textName;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    // endregion

    // region 4. Implemented methods

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.imageResource);
        parcel.writeString(this.textName);
        parcel.writeString(this.textDescription);
        parcel.writeInt(this.numberOfItems);

    }

    // endregion

}
