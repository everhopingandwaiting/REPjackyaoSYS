package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Category;

/**
 * 管理员的JTable的数据模型类
 * @author solo
 */
public class CategoryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"编号", "分类名", "描述"};
	
	private List<Category> list = new ArrayList<Category>();
	
	
	//用于设置要显示的数据的方法
	public void setData(List<Category> data){
		
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
		Category cate = list.get(rowIndex);

		if(columnIndex == 0){
			return cate.getId();
		}else if(columnIndex == 1){
			return cate.getName();
		}else if(columnIndex == 2){
			return cate.getSummary();
		}
		return null;
	}
	
	//自行添加得方法
	
	//获取指定行的对象数据
	public Category getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































