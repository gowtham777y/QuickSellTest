package com.qstest.Model;

public class listItem {
    private String productName;
    private String productDesc;
    private Long productPrice;
    private String productImage;

    public listItem(String productName, String productDesc, Long productPrice, String productImage) {
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Long getProductPrice() {
        return productPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
