package com.jack.entity;



import java.math.BigDecimal;

public class Purchase
{
  private int id;
  private String sn;
  private int supplier_id;
  private int manager_id;
  private int pay_type;
  private long pur_date;
  private BigDecimal cost;
  private String remark;
  private int status;
  private String suppName;
  private String mgrName;

  public int getId()
  {
    return this.id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getSn() {
    return this.sn;
  }
  public void setSn(String sn) {
    this.sn = sn;
  }
  public int getSupplier_id() {
    return this.supplier_id;
  }
  public void setSupplier_id(int supplier_id) {
    this.supplier_id = supplier_id;
  }
  public int getManager_id() {
    return this.manager_id;
  }
  public void setManager_id(int manager_id) {
    this.manager_id = manager_id;
  }
  public int getPay_type() {
    return this.pay_type;
  }
  public void setPay_type(int pay_type) {
    this.pay_type = pay_type;
  }
  public long getPur_date() {
    return this.pur_date;
  }
  public void setPur_date(long pur_date) {
    this.pur_date = pur_date;
  }
  public BigDecimal getCost() {
    return this.cost;
  }
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
  public String getRemark() {
    return this.remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public int getStatus() {
    return this.status;
  }
  public void setStatus(int status) {
    this.status = status;
  }

  public String getStatusString() {
    if (this.status == 0)
      return "草稿";
    if (this.status == 1)
      return "已下单";
    if (this.status == 2)
      return "已入库";
    if (this.status == 3) {
      return "已退货";
    }
    return "";
  }

  public String getSuppName() {
    return this.suppName;
  }
  public void setSuppName(String suppName) {
    this.suppName = suppName;
  }
  public String getMgrName() {
    return this.mgrName;
  }
  public void setMgrName(String mgrName) {
    this.mgrName = mgrName;
  }

  public String toString() {
    return "Purchase [id=" + this.id + ", sn=" + this.sn + ", supplier_id=" + 
      this.supplier_id + ", manager_id=" + this.manager_id + ", pay_type=" + 
      this.pay_type + ", pur_date=" + this.pur_date + ", cost=" + this.cost + 
      ", remark=" + this.remark + ", status=" + this.status + "]";
  }
}