package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Manager;

/**
 * ����Ա��JTable������ģ����
 * @author solo
 */
public class ManagerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"���", "��¼��", "����", "��ϵ�绰", "����", "״̬"};
	
	private List<Manager> list = new ArrayList<Manager>();
	
	
	//��������Ҫ��ʾ�����ݵķ���
	public void setData(List<Manager> data){
		
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
			return mgr.getStatus() == 0 ? "����" : "����";
		}
		return null;
	}
	
	//������ӵ÷���
	
	//��ȡָ���еĶ�������
	public Manager getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































