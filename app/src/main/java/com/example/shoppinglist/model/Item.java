package com.example.shoppinglist.model;

public class Item {

    // region 1. Decl and Init

    private int imageResource;
    private String textName;
    private String textDescription;
    private int numberOfItems;

    // endregion

    // region 2. Constructor

    public Item() {
        this.imageResource = 0;
        this.textName = "No name";
        this.textDescription = "No description";
        this.numberOfItems = 0;
    }

    public Item(int imageResource, String textName, String textDescription, int numberOfItems) {
        this.imageResource = imageResource;
        this.textName = textName;
        this.textDescription = textDescription;
        this.numberOfItems = numberOfItems;
    }

    // endregion

    // region 3. Getters and Setters

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

}
