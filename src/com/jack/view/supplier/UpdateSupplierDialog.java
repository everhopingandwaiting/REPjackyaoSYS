/**
 * 
 */
package com.jack.view.supplier;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jack.dao.SupplierDao;
import com.jack.entity.Supplier;
import com.jack.view.MainFrame;

/**
 * ��ģʽ�ġ����¶Ի��򡱶Ի���
 * @author solo
 */
public class UpdateSupplierDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JTextField tfd_sn;
	private JTextField tfd_name;
	private JTextField tfd_contact;
	private JTextField tfd_address;
	private JTextField tfd_tel;
	private JTextField tfd_code;
	private JTextField tfd_email; 
	private JTextArea  ta_remark;
	
	private Supplier curr_sup;
	public UpdateSupplierDialog(Frame frame, Supplier sup){
		
		super(frame, "���¹�Ӧ����", true);
		
		curr_sup = sup;
		
		this.setSize(656,415);
		this.setResizable(true);          
		this.setLocationRelativeTo(MainFrame.getInstance()); //��������ڸ�����λ�õ�����
		
		JPanel cp = (JPanel)this.getContentPane(); //��ȡ�������
		cp.setLayout(new BorderLayout());
		
		//��
		this.createTitlePanel(cp);
		
		//�м�����
		this.createContentPanel(cp);
		
		//�²���
		this.createBtnPanel(cp);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void createTitlePanel(JPanel cp){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
		//panel.add(new JLabel("����"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		cp.add(centerPanel, BorderLayout.CENTER);
		
		/////////////////////////////////////
		
		
		JLabel snLbl = new JLabel("�ɹ����ݱ��");
		snLbl.setBounds(30, 20, 80, 22);
		centerPanel.add(snLbl);
		
		tfd_sn = new JTextField();
		tfd_sn.setBounds(110, 20, 140, 22);
		tfd_sn.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_sn);
		tfd_sn.setText(curr_sup.getSn());
		
		
		/////////////////////////
		JLabel nameLbl = new JLabel("����");
		nameLbl.setBounds(291, 20, 60, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(361, 20, 140, 22);
		tfd_name.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_name);
		tfd_name.setText(curr_sup.getName());
		
		
		
		
		/////////////////////////
		JLabel contactLbl = new JLabel("��ϵ��");
		contactLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(contactLbl);
		
		tfd_contact = new JTextField();
		tfd_contact.setBounds(110, 55, 140, 22);
		tfd_contact.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_contact);
		tfd_contact.setText(curr_sup.getContact());
		
		
		
		//////////////////////////
		JLabel addressLbl = new JLabel("��˾��ַ");
		addressLbl.setBounds(291, 55, 60, 22);
		centerPanel.add(addressLbl);
		
		tfd_address = new JTextField();
		tfd_address.setBounds(361, 56, 140, 22);
		centerPanel.add(tfd_address);
		tfd_address.setText(curr_sup.getAddress());
		
		
		
		////////////////////////
		JLabel telLbl = new JLabel("�绰");
		telLbl.setBounds(30, 90, 42, 22);
		centerPanel.add(telLbl);
		
		tfd_tel = new JTextField();
		tfd_tel.setBounds(110, 91, 140, 22);
		centerPanel.add(tfd_tel);
		tfd_tel.setText(curr_sup.getTel());
		
		
		
		/////////////////////////////////////
		JLabel codeLbl = new JLabel("�ʱ�");
		codeLbl.setBounds(291, 90, 60, 22);
		centerPanel.add(codeLbl);
		
		tfd_code = new JTextField();
		tfd_code.setBounds(361, 91, 140, 22);
		tfd_code.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_code);
		tfd_code.setText(curr_sup.getCode());
		
		

		///////////////////////
		JLabel emailLbl = new JLabel("����");
		emailLbl.setBounds(30, 125, 32, 22);
		centerPanel.add(emailLbl);
		
		tfd_email = new JTextField();
		tfd_email.setBounds(110, 126, 140, 22);
		centerPanel.add(tfd_email);
		tfd_email.setColumns(10);
		tfd_email.setText(curr_sup.getEmail());
		
		
		
		///////////////////////
		JLabel remarkLbl = new JLabel("��ע");
		remarkLbl.setBounds(30, 160, 60, 22);
		centerPanel.add(remarkLbl);
		
        ta_remark =  new JTextArea();
		
		JScrollPane pane = new JScrollPane(ta_remark, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(110, 159, 260, 88);
		
		centerPanel.add(pane);
		
		
		ta_remark.setText(curr_sup.getRemark());
	}
	
	public void createBtnPanel(JPanel cp){
		//�²���
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 15));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		JButton jbt2 = new JButton("����");
		jbt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfd_sn.setText("");   
				tfd_name.setText("");  
				tfd_contact.setText("");
				tfd_address.setText("");
				tfd_tel.setText("");    
				tfd_code.setText("");  
				tfd_email.setText("");
				ta_remark.setText("");
			}
		});
		btnPanel.add(jbt2);
		
		JButton jbt = new JButton("�ύ");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doUpdate();
			}
		});
		btnPanel.add(jbt);
	}
	
	private void doUpdate(){
		
		String sn = tfd_sn.getText();
		String name = tfd_name.getText();
		String contact= tfd_contact.getText();
		String address= tfd_address.getText();
		String tel= tfd_tel.getText();
		String code	= tfd_code.getText()    ;
		String email= tfd_email.getText()	;
		String remark = ta_remark.getText();
		
		
	
		curr_sup.setSn(sn);
		curr_sup.setName(name);
		curr_sup.setContact(contact);
		curr_sup.setAddress(address);
		curr_sup.setTel(tel);
		curr_sup.setCode(code);
		curr_sup.setEmail(email);
		curr_sup.setRemark(remark);
		
		SupplierDao dao = new SupplierDao();
		dao.update(curr_sup);
		
		//���±�������
		SupplierFrame.getInstance().model.setData(dao.findAll());
		this.dispose();
	}
	
}