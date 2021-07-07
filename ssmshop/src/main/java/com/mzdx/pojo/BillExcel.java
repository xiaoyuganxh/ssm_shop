package com.mzdx.pojo;

public class BillExcel {
     private  String billCode;
     private  String productName;
     private  String providerName;
     private  String totalPrice;
     private  String isPayment;
     private  String creationDate;

    public BillExcel() {
    }

    public BillExcel( String billCode, String productName, String providerName, String totalPrice, String isPayment, String creationDate) {
        this.billCode = billCode;
        this.productName = productName;
        this.providerName = providerName;
        this.totalPrice = totalPrice;
        this.isPayment = isPayment;
        this.creationDate = creationDate;
    }



    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIsPayment() {
        return isPayment;
    }

    public void setIsPayment(String isPayment) {
        this.isPayment = isPayment;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "BillExcel{" +
                "billCode='" + billCode + '\'' +
                ", productName='" + productName + '\'' +
                ", providerName='" + providerName + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", isPayment='" + isPayment + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
