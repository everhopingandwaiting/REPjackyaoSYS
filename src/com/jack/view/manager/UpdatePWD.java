package com.jack.view.manager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.jack.dao.ManagerDao;
import com.jack.entity.Manager;
import com.jack.view.MainFrame;

public class UpdatePWD extends JInternalFrame {
		/**
	 * 
	 */
	
	private static final long serialVersionUID = -5550617380264750179L;

		public    Manager cpwdmanager=null;
		
	  private  static  UpdatePWD updatePWD=new UpdatePWD();
	  private  JPasswordField oldPWD;
	  private JPasswordField newPWD;
	  private JPasswordField secondPWD;
	  private JLabel  notice;
	  public static  UpdatePWD  getInstance(){
		  
		  return updatePWD;
	  }
	  
	
	  private  UpdatePWD(){
//		  System.out.println(MainFrame.curr_mgr);
		  
		 
		   this.setFrameIcon(null);
		 // this.setTitle("现在正在修改密码的是"+cpwdmanager.getLname());
		   this.setSize(600, 400);

			this.setResizable(true);  //是否可调整大小
			this.setIconifiable(true); //是否可最小化
			this.setMaximizable(true);//是否可最大化
			this.setClosable(true); //是否可关闭
			this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //关闭操作为隐藏
			
			//上
			createContentPanel();//中
			//下
			
	  }
	  private void createTitlePanel(){
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 15));
			JLabel lbl = new JLabel("欢迎"+cpwdmanager.getLname()+"修改密码");
			lbl.setFont(new Font("宋体", Font.BOLD, 16));
			panel.add(lbl);
			
			getContentPane().add(panel, BorderLayout.NORTH);
		}
	  
	   private void createContentPanel(){
		          
		     JPanel centerpanel=new JPanel();
		     addComponentListener(new ComponentAdapter() {
		    	 @Override
		    	public void componentShown(ComponentEvent e) {
		    		// TODO Auto-generated method stub
		    		super.componentShown(e);
		    		 cpwdmanager=MainFrame.getInstance().curr_mgr;
		   		  System.out.println(cpwdmanager);
		   		UpdatePWD.this.setTitle("现在正在修改密码的是"+cpwdmanager.getLname());
		   		createTitlePanel();
		   		createBtnPanel();
		    	}
			});
		     centerpanel.setBackground(Color.WHITE);
		     JLabel oldlabel=new     JLabel("原密码  ");
		     oldlabel.setBounds(214, 106, 48, 15);
		     JLabel newlabel=new     JLabel("新密码  ");
		     newlabel.setBounds(214, 149, 48, 15);
		     JLabel secondlabel=new  JLabel("确认密码");
		     secondlabel.setBounds(214, 184, 48, 15);
		     oldPWD=new JPasswordField();
		     oldPWD.setBounds(272, 103, 78, 21);
		     newPWD=new JPasswordField();
		     newPWD.setBounds(272, 146, 78, 21);
		     secondPWD=new JPasswordField();
		     secondPWD.setBounds(272, 181, 78, 21);
		     centerpanel.setLayout(null);
		     centerpanel.add(oldlabel);
		     centerpanel.add(oldPWD);
		     centerpanel.add(newlabel);
		     centerpanel.add(newPWD);
		     centerpanel.add(secondlabel);
		     centerpanel.add(secondPWD);
		     notice=new JLabel();
		     notice.setBounds(272, 220, 178, 30);
		    notice.setForeground(Color.RED);
		     centerpanel.add(notice);
		       getContentPane().add(centerpanel,BorderLayout.CENTER);    
		       
	   }
	   private void  createBtnPanel(){
		    JPanel cpPWDPanel=new JPanel();
		    cpPWDPanel.setLayout(new  FlowLayout(FlowLayout.RIGHT,12,10));
		     JButton btn_submit=new JButton("提交");
		     JButton btn_reset=new JButton("重置");
		     JButton btn_close=new JButton("关闭");
		      cpPWDPanel.add(btn_submit);
		      cpPWDPanel.add(btn_reset);
		      cpPWDPanel.add(btn_close);
		        btn_submit.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						 if (!String.valueOf(oldPWD.getPassword()).equals(cpwdmanager.getPwd())&&
								 String.valueOf(newPWD.getPassword()).equals(String.valueOf(secondPWD.getPassword()))) {
							ManagerDao dao=new ManagerDao();
							Manager manager=new Manager();
							manager.setPwd(String.valueOf(newPWD.getPassword()));
							manager.setId(cpwdmanager.getId());
							if(dao.updatePWD(manager))
							{
								notice.setText("密码更改成功");
							}
							else notice.setText("密码更改失败！");
						}
						 else 
							 notice.setText("旧密码输入不正确，或两次密码输入不一致");
						
					}
				});
		                 btn_reset.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								oldPWD.setText("");
								newPWD.setText("");
								secondPWD.setText("");
							}
						});
		                 btn_close.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								UpdatePWD.this.setVisible(false);
							}
						});
		      
		        getContentPane().add(cpPWDPanel,BorderLayout.SOUTH);
	   }
	   
	   
}
