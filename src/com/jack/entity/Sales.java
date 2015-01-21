package com.jack.entity;



import java.math.BigDecimal;

public class Sales
{
  private int id;
  private String sn;
  private int customer_id;
  private int manager_id;
  private int pay_type;
  private long sale_date;
  private BigDecimal cost;
  private String remark;
  private int status;
  private String custName;
  private String mgrName;

  public int getId()
  {
    return this.id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public String getSn()
  {
    return this.sn;
  }

  public void setSn(String sn)
  {
    this.sn = sn;
  }

  public int getCustomer_id()
  {
    return this.customer_id;
  }

  public void setCustomer_id(int customer_id)
  {
    this.customer_id = customer_id;
  }

  public int getManager_id()
  {
    return this.manager_id;
  }

  public void setManager_id(int manager_id)
  {
    this.manager_id = manager_id;
  }

  public int getPay_type()
  {
    return this.pay_type;
  }

  public void setPay_type(int pay_type)
  {
    this.pay_type = pay_type;
  }

  public long getSale_date()
  {
    return this.sale_date;
  }

  public void setSale_date(long sale_date)
  {
    this.sale_date = sale_date;
  }

  public BigDecimal getCost()
  {
    return this.cost;
  }

  public void setCost(BigDecimal cost)
  {
    this.cost = cost;
  }

  public String getRemark()
  {
    return this.remark;
  }

  public void setRemark(String remark)
  {
    this.remark = remark;
  }

  public int getStatus()
  {
    return this.status;
  }

  public void setStatus(int status)
  {
    this.status = status;
  }

  public String getCustName()
  {
    return this.custName;
  }

  public void setCustName(String custName)
  {
    this.custName = custName;
  }

  public String getMgrName()
  {
    return this.mgrName;
  }

  public void setMgrName(String mgrName)
  {
    this.mgrName = mgrName;
  }

  public String getStatusString()
  {
    if (this.status == 0)
      return "草稿";
    if (this.status == 1)
      return "已下单";
    if (this.status == 2)
      return "已出库";
    if (this.status == 3) {
      return "已退货";
    }
    return "";
  }

  public String toString()
  {
    return "Sales [id=" + this.id + ", sn=" + this.sn + ", customer_id=" + 
      this.customer_id + ", manager_id=" + this.manager_id + ", pay_type=" + 
      this.pay_type + ", sale_date=" + this.sale_date + ", cost=" + this.cost + 
      ", remark=" + this.remark + ", status=" + this.status + ", custName=" + 
      this.custName + ", mgrName=" + this.mgrName + "]";
  }
}