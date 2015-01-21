package com.jack.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.jack.entity.Product;

/**
 * ����Ա��JTable������ģ����
 * @author solo
 */
public class ProductTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -5235704976249134224L;
	
	private String[] titles = {"��Ʒ���", "ISBN����", "��Ʒ��","����ͺ�","ͼ�ĸ���","���۵���","��ȫ����","��λ","����������"};
	
	private List<Product> list = new ArrayList<Product>();
			
	private NumberFormat nf  = new DecimalFormat("��#,###.00Ԫ");
	
	//��������Ҫ��ʾ�����ݵķ���
	public void setData(List<Product> data){
		
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
	
	//������ӵ÷���
	
	//��ȡָ���еĶ�������
	public Product getValueAt(int row)
	{
		return list.get(row);		
	}
	
}
	





































































