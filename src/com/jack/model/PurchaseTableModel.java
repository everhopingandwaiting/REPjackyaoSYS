package com.jack.model;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Purchase;

public class PurchaseTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = -5235704976249134224L;
  private String[] titles = { "编号", "所属供应商", "下单员", "采购日期", "总金额", "付款方式", "状态" };

  private List<Purchase> list = new ArrayList();
  private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  private NumberFormat nf = new DecimalFormat("￥#,##0.00元");

  public void setData(List<Purchase> data)
  {
    this.list = data;
    fireTableRowsInserted(0, this.list.size() - 1);
  }

  public String getColumnName(int column)
  {
    return this.titles[column];
  }

  public int getRowCount()
  {
    return this.list.size();
  }

  public int getColumnCount()
  {
    return this.titles.length;
  }

  public Object getValueAt(int rowIndex, int columnIndex)
  {
    Purchase supp = (Purchase)this.list.get(rowIndex);

    if (columnIndex == 0)
      return supp.getSn();
    if (columnIndex == 1)
      return supp.getSuppName();
    if (columnIndex == 2)
      return supp.getMgrName();
    if (columnIndex == 3)
      return this.df.format(new Date(supp.getPur_date()));
    if (columnIndex == 4)
      return this.nf.format(supp.getCost().doubleValue());
    if (columnIndex == 5)
      return supp.getPay_type() == 0 ? "全款" : "欠款";
    if (columnIndex == 6) {
      return supp.getStatusString();
    }
    return null;
  }

  public Purchase getValueAt(int row)
  {
    return (Purchase)this.list.get(row);
  }
}