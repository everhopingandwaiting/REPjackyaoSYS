/**
 * 
 */
package com.jack.view.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
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

/**
 * ��ģʽ�ġ����¶Ի��򡱶Ի���
 * @author solo
 */
public class UpdateManagerDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JTextField tfd_name;
	private JPasswordField pfd_pwd;
	private JTextField tfd_mobile;
	private JTextField tfd_email;
	
	private JRadioButton radio_normal;
	private JRadioButton radio_locked;
	
	//
	private Manager curr_mgr;

	public UpdateManagerDialog(Frame frame, Manager mgr) {
		super(frame, "���¹���Ա", true);
		curr_mgr = mgr;
		
		this.setSize(520, 240);
		this.setResizable(false); //���ò����������С
		this.setLocationRelativeTo(frame); //��������ڸ�����λ�õ�����
		
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
		//panel.add(new JLabel("���¹���Ա"));
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
		tfd_name.setText(curr_mgr.getLname());
		tfd_name.setEditable(false);
		
		JLabel cateLbl = new JLabel("����");
		cateLbl.setBounds(270, 20, 60, 22);
		centerPanel.add(cateLbl);
		
		pfd_pwd = new JPasswordField();
		pfd_pwd.setBounds(330, 20, 140, 22);
		centerPanel.add(pfd_pwd);
		pfd_pwd.setText(curr_mgr.getPwd());
		/////////////////////////////////////
		JLabel modelLbl = new JLabel("��ϵ�绰");
		modelLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(modelLbl);
		
		tfd_mobile = new JTextField();
		tfd_mobile.setBounds(110, 55, 140, 22);
		centerPanel.add(tfd_mobile);
		tfd_mobile.setText(curr_mgr.getMobile());
		JLabel priceLbl = new JLabel("����");
		priceLbl.setBounds(270, 55, 60, 22);
		centerPanel.add(priceLbl);
		tfd_email = new JTextField();
		tfd_email.setBounds(330, 55, 140, 22);
		centerPanel.add(tfd_email);
		tfd_email.setText(curr_mgr.getEmail());
		///////////////////////////////////////
		JLabel lbl_mobile = new JLabel("״̬");
		lbl_mobile.setBounds(30, 95, 80, 22);
		centerPanel.add(lbl_mobile);
		
		ButtonGroup group_status = new ButtonGroup();
		
		radio_normal = new JRadioButton("����");
		group_status.add(radio_normal);
		if(curr_mgr.getStatus() == 0){
			radio_normal.setSelected(true);
		}
		radio_normal.setBackground(Color.WHITE);
		radio_normal.setBounds(110, 95, 60, 22);
		centerPanel.add(radio_normal);
		
		radio_locked = new JRadioButton("����");
		group_status.add(radio_locked);
		if(curr_mgr.getStatus() != 0){
			radio_locked.setSelected(true);
		}
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
				pfd_pwd.setText(curr_mgr.getPwd());
				tfd_mobile.setText(curr_mgr.getMobile());
				
				tfd_email.setText(curr_mgr.getEmail());
				
				if(curr_mgr.getStatus() == 0){
					radio_normal.setSelected(true);
				}else{
					radio_locked.setSelected(true);
				}
			}
		});
		btnPanel.add(jbt2);
		
		JButton jbt = new JButton("����");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doUpdate();
			}
		});
		btnPanel.add(jbt);
	}
	
	private void doUpdate(){
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
		
		curr_mgr.setPwd(pwd);
		curr_mgr.setMobile(mobile);
		curr_mgr.setEmail(email);
		curr_mgr.setStatus(status);
		
		ManagerDao dao = new ManagerDao();
		dao.update(curr_mgr);
		
		//���±������
		ManagerFrame.getInstance().model.setData(dao.findAll());
		
		this.dispose();
	}
}
