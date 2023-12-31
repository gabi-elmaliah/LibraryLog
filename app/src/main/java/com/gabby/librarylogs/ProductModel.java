package com.gabby.librarylogs;

public class ProductModel {
    public ProductModel() {

    }


    int product_id, price,review;
    int quantity = 0;
    String product_name, url, product_desc;

    public ProductModel(int product_id, int price, int quantity, String product_name, String url, String product_desc,int review) {
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.product_name = product_name;
        this.url = url;
        this.product_desc = product_desc;
        this.review = review;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
