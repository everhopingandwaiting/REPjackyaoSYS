package com.jack.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Customer;

/**
 * ����Ա��JTable������ģ����
 * @author solo
 */
public class CustomerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"���","�ͻ����","�ͻ���","��ϵ��","��˾��ַ","�绰","�ʱ�","����","��ע"};
	
	private List<Customer> list = new ArrayList<Customer>();
	
	
	//��������Ҫ��ʾ�����ݵķ���
	public void setData(List<Customer> data){
		
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
	
	//������ӵ÷���
	
	//��ȡָ���еĶ�������
	public Customer getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































