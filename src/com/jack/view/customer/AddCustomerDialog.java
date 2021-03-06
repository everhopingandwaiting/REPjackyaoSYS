/**
 * 
 */
package com.jack.view.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jack.dao.CustomerDao;
import com.jack.entity.Customer;
import com.jack.view.customer.CustomerFrame;
import com.jack.view.customer.AddCustomerDialog;
import com.jack.view.MainFrame;

/**
 * 有模式的“新增对话框”对话框
 * @author solo
 */
public class AddCustomerDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	
	private JTextField tfd_sn;
	private JTextField tfd_name;
	private JTextField tfd_contact;
	private JTextField tfd_address;
	private JTextField tfd_tel;
	private JTextField tfd_code;
	private JTextField tfd_email; 
	private JTextArea  ta_remark;
	
	private static AddCustomerDialog dialog = new AddCustomerDialog(); 
	

	public static AddCustomerDialog getInstance(){
		return dialog;
	}

	private AddCustomerDialog() {
		super(MainFrame.getInstance(), "新增客户类", true);
		this.setSize(656,415);
		this.setResizable(true);          
		this.setLocationRelativeTo(MainFrame.getInstance()); //设置相对于父窗体位置的中央
		
		JPanel cp = (JPanel)this.getContentPane(); //获取内容面板
		cp.setLayout(new BorderLayout());
		
		//上
		this.createTitlePanel(cp);
		
		//中间内容
		this.createContentPanel(cp);
		
		//下部分
		this.createBtnPanel(cp);
		
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
	}
	
	public void createTitlePanel(JPanel cp){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
		//panel.add(new JLabel("新增"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		cp.add(centerPanel, BorderLayout.CENTER);
		
		
		/////////////////////////////////////
		JLabel snLbl = new JLabel("客户编号");
		snLbl.setBounds(30, 20, 80, 22);
		centerPanel.add(snLbl);
		
		tfd_sn = new JTextField();
		tfd_sn.setBounds(110, 20, 140, 22);
		tfd_sn.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_sn);
		
		
		JLabel nameLbl = new JLabel("客户名");
		nameLbl.setBounds(291, 20, 60, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(361, 20, 140, 22);
		tfd_name.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_name);
		
		JLabel contactLbl = new JLabel("联系人");
		contactLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(contactLbl);
		
		tfd_contact = new JTextField();
		tfd_contact.setBounds(110, 55, 140, 22);
		tfd_contact.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_contact);
		
		JLabel addressLbl = new JLabel("公司地址");
		addressLbl.setBounds(291, 55, 60, 22);
		centerPanel.add(addressLbl);
		
		tfd_address = new JTextField();
		tfd_address.setBounds(361, 56, 140, 22);
		centerPanel.add(tfd_address);
		
		
		JLabel telLbl = new JLabel("电话");
		telLbl.setBounds(30, 90, 42, 22);
		centerPanel.add(telLbl);
		
		tfd_tel = new JTextField();
		tfd_tel.setBounds(110, 91, 140, 22);
		centerPanel.add(tfd_tel);
		
		
		JLabel codeLbl = new JLabel("邮编");
		codeLbl.setBounds(291, 90, 60, 22);
		centerPanel.add(codeLbl);
		
		tfd_code = new JTextField();
		tfd_code.setBounds(361, 91, 140, 22);
		tfd_code.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_code);
		
		JLabel emailLbl = new JLabel("邮箱");
		emailLbl.setBounds(30, 125, 32, 22);
		centerPanel.add(emailLbl);
		
		tfd_email = new JTextField();
		tfd_email.setBounds(110, 126, 140, 22);
		centerPanel.add(tfd_email);
		tfd_email.setColumns(10);
		
		
		
		JLabel remarkLbl = new JLabel("备注");
		remarkLbl.setBounds(30, 160, 60, 22);
		centerPanel.add(remarkLbl);
		
        ta_remark =  new JTextArea();
		
		JScrollPane pane = new JScrollPane(ta_remark, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(110, 159, 260, 88);
		
		centerPanel.add(pane);
			
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
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
		
	}
	
	public void createBtnPanel(JPanel cp){
		//下部分
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 15));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		JButton jbt2 = new JButton("重置");
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
		
		JButton jbt = new JButton("新增");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAdd();
			}
		});
		btnPanel.add(jbt);
	}
	
	private void doAdd(){
		String sn = tfd_sn.getText();
		String name = tfd_name.getText();
		String contact= tfd_contact.getText();
		String address= tfd_address.getText();
		String tel= tfd_tel.getText();
		String code	= tfd_code.getText()    ;
		String email= tfd_email.getText()	;
		String remark = ta_remark.getText();

		Customer cus = new Customer();
		cus.setSn(sn);
		cus.setName(name);
		cus.setContact(contact);
		cus.setAddress(address);
		cus.setTel(tel);
		cus.setCode(code);
		cus.setEmail(email);
		cus.setRemark(remark);
		
		
		
		CustomerDao dao = new CustomerDao();
		dao.save(cus);
		
		//更新表格数据
		CustomerFrame.getInstance().model.setData(dao.findAll());
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		new AddCustomerDialog();
	}
}