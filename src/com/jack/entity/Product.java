package com.jack.entity;

import java.math.BigDecimal;

public class Product
{
  private int id;
  private String isbn;
  private String name;
  private String model;
  private String summary;
  private BigDecimal sale_price;
  private int security_num;
  private String unit;
  private int cate_id;
  private String cate_name;

  public int getId()
  {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getModel() {
    return this.model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getSummary() {
    return this.summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public BigDecimal getSale_price() {
    return this.sale_price;
  }

  public void setSale_price(BigDecimal sale_price) {
    this.sale_price = sale_price;
  }
  public int getSecurity_num() {
    return this.security_num;
  }
  public void setSecurity_num(int security) {
    this.security_num = security;
  }
  public String getUnit() {
    return this.unit;
  }
  public void setUnit(String unit) {
    this.unit = unit;
  }
  public int getCate_id() {
    return this.cate_id;
  }

  public void setCate_id(int cate_id) {
    this.cate_id = cate_id;
  }

  public void setCate_name(String cate_name)
  {
    this.cate_name = cate_name;
  }

  public String getCate_name() {
    return this.cate_name;
  }
  @Override
  public String toString()
  {
    return this.name;
  }
}