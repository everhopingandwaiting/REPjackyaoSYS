package com.jack.entity;



import java.math.BigDecimal;

public class SalesItem
{
  private int id;
  private int sales_id;
  private int product_id;
  private int num;
  private BigDecimal price;
  private String prodName;
  private String prodModel;
  private String prodUnit;

  public int getId()
  {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getSales_id() {
    return this.sales_id;
  }
  public void setSales_id(int sales_id) {
    this.sales_id = sales_id;
  }
  public int getProduct_id() {
    return this.product_id;
  }
  public void setProduct_id(int product_id) {
    this.product_id = product_id;
  }
  public int getNum() {
    return this.num;
  }
  public void setNum(int num) {
    this.num = num;
  }
  public BigDecimal getPrice() {
    return this.price;
  }
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
  public String getProdName() {
    return this.prodName;
  }
  public void setProdName(String prodName) {
    this.prodName = prodName;
  }
  public String getProdModel() {
    return this.prodModel;
  }
  public void setProdModel(String prodModel) {
    this.prodModel = prodModel;
  }
  public String getProdUnit() {
    return this.prodUnit;
  }
  public void setProdUnit(String prodUnit) {
    this.prodUnit = prodUnit;
  }

  public String toString() {
    return "SalesItem [id=" + this.id + ", sales_id=" + this.sales_id + 
      ", product_id=" + this.product_id + ", num=" + this.num + ", price=" + 
      this.price + "]";
  }
}