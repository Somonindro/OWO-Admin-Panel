package com.owoSuperAdmin.ordersManagement.entity;

import java.io.Serializable;

public class ShopKeeperOrderedProducts implements Serializable {
    private long id;
    private long product_id;
    private String product_name;
    private String product_category;
    private double product_price;
    private double product_discount;
    private int product_quantity;
    private String product_description;
    private String product_creation_date;
    private String product_creation_time;
    private String product_sub_category;
    private String product_brand;
    private String product_image;

    private Shop_keeper_orders shop_keeper_orders;

    public ShopKeeperOrderedProducts(long id, long product_id, String product_name,
                                     String product_category, double product_price,
                                     double product_discount, int product_quantity,
                                     String product_description, String product_creation_date,
                                     String product_creation_time, String product_sub_category,
                                     String product_brand, String product_image, Shop_keeper_orders shop_keeper_orders) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_price = product_price;
        this.product_discount = product_discount;
        this.product_quantity = product_quantity;
        this.product_description = product_description;
        this.product_creation_date = product_creation_date;
        this.product_creation_time = product_creation_time;
        this.product_sub_category = product_sub_category;
        this.product_brand = product_brand;
        this.product_image = product_image;
        this.shop_keeper_orders = shop_keeper_orders;
    }

    public ShopKeeperOrderedProducts() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public double getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(double product_discount) {
        this.product_discount = product_discount;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_creation_date() {
        return product_creation_date;
    }

    public void setProduct_creation_date(String product_creation_date) {
        this.product_creation_date = product_creation_date;
    }

    public String getProduct_creation_time() {
        return product_creation_time;
    }

    public void setProduct_creation_time(String product_creation_time) {
        this.product_creation_time = product_creation_time;
    }

    public String getProduct_sub_category() {
        return product_sub_category;
    }

    public void setProduct_sub_category(String product_sub_category) {
        this.product_sub_category = product_sub_category;
    }

    public String getProduct_brand() {
        return product_brand;
    }

    public void setProduct_brand(String product_brand) {
        this.product_brand = product_brand;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public Shop_keeper_orders getShop_keeper_orders() {
        return shop_keeper_orders;
    }

    public void setShop_keeper_orders(Shop_keeper_orders shop_keeper_orders) {
        this.shop_keeper_orders = shop_keeper_orders;
    }
}
