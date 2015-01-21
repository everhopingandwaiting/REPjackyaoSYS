package com.jack.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jvnet.substance.skin.SubstanceBusinessLookAndFeel;

import com.jack.dao.ManagerDao;
import com.jack.entity.Manager;

/**
 * @author solo
 */
public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 4601868167517461834L;
	
	private JTextField tfd_lname = new JTextField();
	private JPasswordField pfd_pwd = new JPasswordField();

	public LoginFrame(){
		
		//建立登陆窗体
		this.setIconImage(new ImageIcon("images/afe.jpg").getImage());//创建图标
		this.setTitle("我de管理系统");
		this.setSize(388, 320);
		
		//设置登陆窗体在屏幕中央位置出现
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//屏幕的尺寸
		this.setLocation((d.width - this.getWidth()) / 2, (d.height - this.getHeight())/2);
		
		initUI();
		
		//关闭窗体则直接退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initUI(){
		JLabel lbl_logo = new JLabel(new ImageIcon("images/bei2.jpg"));
		this.add(lbl_logo, BorderLayout.NORTH);
		
		//设置中央的面板
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEtchedBorder());
		this.add(panel, BorderLayout.CENTER);
		
		//设置为手动管理布局
		panel.setLayout(null);
		
		//设置用户名键
		JLabel lbl_lname = new JLabel("用户名：");
		lbl_lname.setBounds(85, 20, 50, 22);
		panel.add(lbl_lname);
		
		tfd_lname.setBounds(145, 20, 150, 22);
		panel.add(tfd_lname);//放在界面的位置
		tfd_lname.setText("jack");
		
		//设置密码键
		JLabel lbl_pwd = new JLabel("密  码：");
		lbl_pwd.setBounds(85, 55, 50, 22);
		panel.add(lbl_pwd);
		
		pfd_pwd.setBounds(145, 55, 150, 22);
		panel.add(pfd_pwd);//放在界面的位置
		pfd_pwd.setText("jack");
		
		//按钮
		JPanel pnl_south = new JPanel();
		pnl_south.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		this.add(pnl_south, BorderLayout.SOUTH);
		
		JButton btn_submit = new JButton("登录");
		pnl_south.add(btn_submit);
		
		
		//注册事件监听器
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		
		JButton btn_reset = new JButton("重置");
		btn_reset.setBounds(220, 100, 60, 22);
		pnl_south.add(btn_reset);
	}
	
	//执行登录时的事件处理方法
	private void doLogin(){
		
		//获取用户名和密码
		String lname = tfd_lname.getText();
		@SuppressWarnings("deprecation")
		String pwd = pfd_pwd.getText();
		
		
		//数据输入是否合法
		if(null == lname ||"".equals(lname))
		{
			JOptionPane.showMessageDialog(this, "用户名不能为空！", "输入有误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(null == pwd ||"".equals(pwd))
		{
			JOptionPane.showMessageDialog(this, "密码不能为空！", "输入有误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		//调用ManagerDao进行判断
		ManagerDao dao = new ManagerDao();
		
		
		Manager mgr = dao.findOne(lname);
		if(mgr != null && mgr.getPwd().equals(pwd))
		{
			if(mgr.getStatus() == 0)//登陆成功
			{
				MainFrame.getInstance(mgr);//进入主页面
				LoginFrame.this.dispose();//销毁登陆界面
			}
			else
			{
				JOptionPane.showMessageDialog(this, "用户被锁定", "登录失败", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "用户或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
		}
	}
	
		
		 
		
	
	public static void main(String[] args) throws Exception {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		//设置主题
		UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame();
			}
		});
	}
}