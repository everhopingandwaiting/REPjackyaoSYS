package com.jack.model;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.PurchaseItem;

public class PurchaseItemTableModel extends AbstractTableModel
{
  private static final long serialVersionUID = -5235704976249134224L;
  private String[] titles = { "序号", "采购商品名", "规格", "单位", "采购单价", "采购数量", "小计金额" };

  private List<PurchaseItem> list = new ArrayList();
  private NumberFormat nf = new DecimalFormat("￥#,##0.00元");

  public void setData(List<PurchaseItem> data)
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
    PurchaseItem info = (PurchaseItem)this.list.get(rowIndex);

    if (columnIndex == 0)
      return Integer.valueOf(rowIndex + 1);
    if (columnIndex == 1)
      return info.getProdName();
    if (columnIndex == 2)
      return info.getProdModel();
    if (columnIndex == 3)
      return info.getProdUnit();
    if (columnIndex == 4)
      return this.nf.format(info.getPrice().doubleValue());
    if (columnIndex == 5)
      return Integer.valueOf(info.getNum());
    if (columnIndex == 6) {
      BigDecimal total = info.getPrice().multiply(new BigDecimal(info.getNum()));
      return this.nf.format(total.doubleValue());
    }
    return null;
  }

  public PurchaseItem getValueAt(int row)
  {
    return (PurchaseItem)this.list.get(row);
  }

  public void add(PurchaseItem entity)
  {
    int row = this.list.size();
    this.list.add(entity);

    fireTableRowsInserted(row, row);
  }

  public void remove(int rowIndex)
  {
    if ((rowIndex >= 0) && (rowIndex < this.list.size())) {
      this.list.remove(rowIndex);
      fireTableRowsDeleted(rowIndex, rowIndex);
    }
  }

  public void update(int rowIndex, PurchaseItem entity)
  {
    if ((rowIndex >= 0) && (rowIndex < this.list.size())) {
      this.list.remove(rowIndex);
      this.list.add(rowIndex, entity);
      fireTableRowsUpdated(rowIndex, rowIndex);
    }
  }

  public List<PurchaseItem> getList() {
    return this.list;
  }
}