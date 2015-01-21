package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Manager;

/**
 * 管理员的JTable的数据模型类
 * @author solo
 */
public class ManagerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"编号", "登录名", "密码", "联系电话", "邮箱", "状态"};
	
	private List<Manager> list = new ArrayList<Manager>();
	
	
	//用于设置要显示的数据的方法
	public void setData(List<Manager> data){
		
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
		Manager mgr = list.get(rowIndex);

		if(columnIndex == 0){
			return mgr.getId();
		}else if(columnIndex == 1){
			return mgr.getLname();
		}else if(columnIndex == 2){
			return "***";
		}else if(columnIndex == 3){
			return mgr.getMobile();
		}else if(columnIndex == 4){
			return mgr.getEmail();
		}else if(columnIndex == 5){
			return mgr.getStatus() == 0 ? "正常" : "锁定";
		}
		return null;
	}
	
	//自行添加得方法
	
	//获取指定行的对象数据
	public Manager getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































