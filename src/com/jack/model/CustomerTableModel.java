package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Customer;

/**
 * 管理员的JTable的数据模型类
 * @author solo
 */
public class CustomerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"编号","客户编号","客户名","联系人","公司地址","电话","邮编","邮箱","备注"};
	
	private List<Customer> list = new ArrayList<Customer>();
	
	
	//用于设置要显示的数据的方法
	public void setData(List<Customer> data){
		
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
		Customer cus = list.get(rowIndex);

		if(columnIndex == 0){
			return cus.getId();
		}else if(columnIndex == 1){
			return cus.getSn();
		}else if(columnIndex == 2){
			return cus.getName();
		}else if(columnIndex == 3){
			return cus.getContact();
		}else if(columnIndex == 4){
			return cus.getAddress();
		}else if(columnIndex == 5){
			return cus.getTel();
		}else if(columnIndex == 6){
			return cus.getCode();
		}else if(columnIndex == 7){
			return cus.getEmail();
		}else if(columnIndex == 8){
			return cus.getRemark();
		}				
		return null;
	}
	
	//自行添加得方法
	
	//获取指定行的对象数据
	public Customer getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































