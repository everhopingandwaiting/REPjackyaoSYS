package com.jack.view.customer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.jack.dao.CustomerDao;
import com.jack.entity.Customer;
import com.jack.model.CustomerTableModel;
import com.jack.view.MainFrame;

/**
 * �Թ���Ա���й����Ĵ���
 * @author solo
 */
public class CustomerFrame extends JInternalFrame {
	private static final long serialVersionUID = 3641881158731346878L;
	private static CustomerFrame frame = new CustomerFrame();
	
	private JButton btn_add;
	private JButton btn_update;
	private JButton btn_delete;
	
	private JTable table;
	public CustomerTableModel model;
	private Customer selectedSup;
	
	private CustomerDao dao = new CustomerDao();
	
	public static CustomerFrame getInstance(){
		return frame;
	}
	
	private CustomerFrame(){
		this.setFrameIcon(null); //���ñ������е�ͼ��Ϊnull
		this.setTitle("�ͻ�����");
		this.setSize(600, 400);
		
		this.setResizable(true);  //�Ƿ�ɵ�����С
		this.setIconifiable(true); //�Ƿ����С��
		this.setMaximizable(true);//�Ƿ�����
		this.setClosable(true); //�Ƿ�ɹر�
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //�رղ���Ϊ����
		
		createButtonPanel();
		
		createMainPanel();
	}
	
	private void createButtonPanel(){
		JPanel btnPanel = new JPanel();
		this.add(btnPanel, BorderLayout.NORTH);
		
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		
		btn_add = new JButton("����");
		btnPanel.add(btn_add);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCustomerDialog.getInstance().setVisible(true);;
			}
		});
		
		btn_update = new JButton("����");
		btnPanel.add(btn_update);
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedSup != null){
					new UpdateCustomerDialog((JFrame) MainFrame.getInstance(), selectedSup);
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"����ѡ��Ҫ�༭��������", 
							"��������", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btn_delete = new JButton("ɾ��");
		btnPanel.add(btn_delete);
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("ɾ��ѡ�еĿͻ�...");
				if(selectedSup != null){
					int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
							"���ɻָ�ɾ������ȷ����", 
							"ɾ��ȷ��", 
							JOptionPane.OK_CANCEL_OPTION);
					
					if(result == JOptionPane.OK_OPTION){
						dao.delete(selectedSup.getId());
						selectedSup = null;
						
						//���¼�������
						model.setData(dao.findAll());
					}
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"����ѡ��Ҫɾ����������", 
							"��������", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void createMainPanel(){
		
		//���ӱ������
		table = new JTable();
		
		//����Ϊ��ѡģʽ
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		model = new CustomerTableModel();
		table.setModel(model); //��������������ģ��
		
		
		
		//�����¼�
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	// ȡ�õ��������
            	int row = table.getSelectedRow();
            	 
            	
            	//���õ�ǰѡ�е�������
            	selectedSup = model.getValueAt(row);
            	//System.out.println(selectedMgr);
            }
       });
		
		JScrollPane scrollPane = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		//��ʼ������
		List<Customer> list = dao.findAll();
		model.setData(list);
	}
}