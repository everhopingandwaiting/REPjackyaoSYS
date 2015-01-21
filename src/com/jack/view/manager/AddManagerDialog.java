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
 * 有模式的“新增对话框”对话框
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
		super(MainFrame.getInstance(), "新增管理员", true);
		this.setSize(520, 240);
		this.setResizable(false); //设置不允许调整大小
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
		//panel.add(new JLabel("新增管理员"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		
		/////////////////////////////////////
		JLabel nameLbl = new JLabel("登录名");
		nameLbl.setBounds(30, 20, 80, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(110, 20, 140, 22);
		tfd_name.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_name);
		
		
		JLabel cateLbl = new JLabel("密码");
		cateLbl.setBounds(270, 20, 60, 22);
		centerPanel.add(cateLbl);
		
		pfd_pwd = new JPasswordField();
		pfd_pwd.setBounds(330, 20, 140, 22);
		centerPanel.add(pfd_pwd);
		
		/////////////////////////////////////
		JLabel modelLbl = new JLabel("联系电话");
		modelLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(modelLbl);
		
		tfd_mobile = new JTextField();
		tfd_mobile.setBounds(110, 55, 140, 22);
		centerPanel.add(tfd_mobile);
		
		JLabel priceLbl = new JLabel("邮箱");
		priceLbl.setBounds(270, 55, 60, 22);
		centerPanel.add(priceLbl);
		tfd_email = new JTextField();
		tfd_email.setBounds(330, 55, 140, 22);
		centerPanel.add(tfd_email);
		
		///////////////////////////////////////
		JLabel lbl_mobile = new JLabel("状态");
		lbl_mobile.setBounds(30, 95, 80, 22);
		centerPanel.add(lbl_mobile);
		
		ButtonGroup group_status = new ButtonGroup();
		
		radio_normal = new JRadioButton("正常");
		group_status.add(radio_normal);
		radio_normal.setSelected(true);
		radio_normal.setBackground(Color.WHITE);
		radio_normal.setBounds(110, 95, 60, 22);
		centerPanel.add(radio_normal);
		
		radio_locked = new JRadioButton("锁定");
		group_status.add(radio_locked);
		radio_locked.setBackground(Color.WHITE);
		radio_locked.setBounds(190, 95, 60, 22);
		centerPanel.add(radio_locked);
		
		cp.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void createBtnPanel(JPanel cp){
		//下部分
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 15));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		JButton jbt2 = new JButton("重置");
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
		
		JButton jbt = new JButton("新增");
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
		
		//更新表格数据
		ManagerFrame.getInstance().model.setData(dao.findAll());
		
		this.setVisible(false);
	}
}