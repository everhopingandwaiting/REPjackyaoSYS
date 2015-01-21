/**
 * 
 */
package com.jack.view.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.jack.dao.ManagerDao;
import com.jack.entity.Manager;
import com.jack.view.MainFrame;

/**
 * ��ģʽ�ġ������Ի��򡱶Ի���
 * @author solo
 */
public class AddManagerDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JTextField tfd_name;
	private JPasswordField pfd_pwd;
	private JTextField tfd_mobile;
	private JTextField tfd_email;
	
	private JRadioButton radio_normal;
	private JRadioButton radio_locked;
	
	private static AddManagerDialog dialog = new AddManagerDialog(); 
	
	public static AddManagerDialog getInstance(){
		return dialog;
	}

	private AddManagerDialog() {
		super(MainFrame.getInstance(), "��������Ա", true);
		this.setSize(520, 240);
		this.setResizable(false); //���ò����������С
		this.setLocationRelativeTo(MainFrame.getInstance()); //��������ڸ�����λ�õ�����
		
		JPanel cp = (JPanel)this.getContentPane(); //��ȡ�������
		cp.setLayout(new BorderLayout());
		
		//��
		this.createTitlePanel(cp);
		
		//�м�����
		this.createContentPanel(cp);
		
		//�²���
		this.createBtnPanel(cp);
		
		this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		
	}
	
	public void createTitlePanel(JPanel cp){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
		//panel.add(new JLabel("��������Ա"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		
		/////////////////////////////////////
		JLabel nameLbl = new JLabel("��¼��");
		nameLbl.setBounds(30, 20, 80, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(110, 20, 140, 22);
		tfd_name.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_name);
		
		
		JLabel cateLbl = new JLabel("����");
		cateLbl.setBounds(270, 20, 60, 22);
		centerPanel.add(cateLbl);
		
		pfd_pwd = new JPasswordField();
		pfd_pwd.setBounds(330, 20, 140, 22);
		centerPanel.add(pfd_pwd);
		
		/////////////////////////////////////
		JLabel modelLbl = new JLabel("��ϵ�绰");
		modelLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(modelLbl);
		
		tfd_mobile = new JTextField();
		tfd_mobile.setBounds(110, 55, 140, 22);
		centerPanel.add(tfd_mobile);
		
		JLabel priceLbl = new JLabel("����");
		priceLbl.setBounds(270, 55, 60, 22);
		centerPanel.add(priceLbl);
		tfd_email = new JTextField();
		tfd_email.setBounds(330, 55, 140, 22);
		centerPanel.add(tfd_email);
		
		///////////////////////////////////////
		JLabel lbl_mobile = new JLabel("״̬");
		lbl_mobile.setBounds(30, 95, 80, 22);
		centerPanel.add(lbl_mobile);
		
		ButtonGroup group_status = new ButtonGroup();
		
		radio_normal = new JRadioButton("����");
		group_status.add(radio_normal);
		radio_normal.setSelected(true);
		radio_normal.setBackground(Color.WHITE);
		radio_normal.setBounds(110, 95, 60, 22);
		centerPanel.add(radio_normal);
		
		radio_locked = new JRadioButton("����");
		group_status.add(radio_locked);
		radio_locked.setBackground(Color.WHITE);
		radio_locked.setBounds(190, 95, 60, 22);
		centerPanel.add(radio_locked);
		
		cp.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void createBtnPanel(JPanel cp){
		//�²���
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 15));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		JButton jbt2 = new JButton("����");
		jbt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfd_name.setText("");
				pfd_pwd.setText("");
				tfd_mobile.setText("");
				tfd_email.setText("");
				radio_normal.setSelected(true);
			}
		});
		btnPanel.add(jbt2);
		
		JButton jbt = new JButton("����");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doAdd();
			}
		});
		btnPanel.add(jbt);
	}
	
	private void doAdd(){
		String lname = tfd_name.getText();
		@SuppressWarnings("deprecation")
		String pwd = pfd_pwd.getText();
		String mobile = tfd_mobile.getText();
		String email = tfd_email.getText();
		
		int status = 0;
		if(radio_normal.isSelected()){
			status = 0;
		}else{
			status = -1;
		}
		
		Manager mgr = new Manager();
		mgr.setLname(lname);
		mgr.setPwd(pwd);
		mgr.setMobile(mobile);
		mgr.setEmail(email);
		mgr.setStatus(status);
		
		ManagerDao dao = new ManagerDao();
		dao.save(mgr);
		
		//���±������
		ManagerFrame.getInstance().model.setData(dao.findAll());
		
		this.setVisible(false);
	}
}