package com.jack.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Product;

/**
 * 管理员的JTable的数据模型类
 * @author solo
 */
public class ProductTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"商品编号", "ISBN编码", "商品名","规格型号","图文概述","销售单价","安全存量","单位","所属分类编号"};
	
	private List<Product> list = new ArrayList<Product>();
			
	private NumberFormat nf  = new DecimalFormat("￥#,###.00元");
	
	//用于设置要显示的数据的方法
	public void setData(List<Product> data){
		
		this.list.clear();
		this.list = data;//设置新的数据
		fireTableRowsInserted(0, this.list.size()-1);//通知监听器去刷新新界面
	}
	
	@Override//获取指定列的标题名-->手动重写的方法
	public String getColumnName(int column) { 
		return titles[column];
	}

	public int getRowCount() { 
		return list.size();
	}

	public int getColumnCount() { 
		return titles.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) { 
		Product pro = list.get(rowIndex);

		if(columnIndex == 0){
			return pro.getId();
		}else if(columnIndex == 1){
			return pro.getIsbn();
		}else if(columnIndex == 2){
			return pro.getName();
		}else if(columnIndex == 3){
			return pro.getModel();
		}else if(columnIndex == 4){
			return pro.getSummary();
		}else if(columnIndex == 5){
			return nf.format(pro.getSale_price().doubleValue());
		}else if(columnIndex == 6){
			return pro.getSecurity_num();
		}else if(columnIndex == 7){
			return pro.getUnit();
		}else if(columnIndex == 8){
			return pro.getCate_id();
		}
		return null;
	}
	
	//自行添加得方法
	
	//获取指定行的对象数据
	public Product getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































