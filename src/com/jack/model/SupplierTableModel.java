package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Supplier;

/**
 * ����Ա��JTable������ģ����
 * @author solo
 */
public class SupplierTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"���","�ɹ����ݱ��","����","��ϵ��","��˾��ַ","�绰","�ʱ�","����","��ע"};
	
	private List<Supplier> list = new ArrayList<Supplier>();
	
	
	//��������Ҫ��ʾ�����ݵķ���
	public void setData(List<Supplier> data){
		
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
		Supplier sup = list.get(rowIndex);

		if(columnIndex == 0){
			return sup.getId();
		}else if(columnIndex == 1){
			return sup.getSn();
		}else if(columnIndex == 2){
			return sup.getName();
		}else if(columnIndex == 3){
			return sup.getContact();
		}else if(columnIndex == 4){
			return sup.getAddress();
		}else if(columnIndex == 5){
			return sup.getTel();
		}else if(columnIndex == 6){
			return sup.getCode();
		}else if(columnIndex == 7){
			return sup.getEmail();
		}else if(columnIndex == 8){
			return sup.getRemark();
		}				
		return null;
	}
	
	//������ӵ÷���
	
	//��ȡָ���еĶ�������
	public Supplier getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































