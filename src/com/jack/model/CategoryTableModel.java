package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Category;

/**
 * ����Ա��JTable������ģ����
 * @author solo
 */
public class CategoryTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"���", "������", "����"};
	
	private List<Category> list = new ArrayList<Category>();
	
	
	//��������Ҫ��ʾ�����ݵķ���
	public void setData(List<Category> data){
		
		this.list.clear();
		this.list = data;//�����µ�����
		fireTableRowsInserted(0, this.list.size()-1);//֪ͨ������ȥˢ���½���
	}
	
	@Override//��ȡָ���еı�����-->�ֶ���д�ķ���
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
	
	//������ӵ÷���
	
	//��ȡָ���еĶ�������
	public Category getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































