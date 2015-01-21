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
		
		//������½����
		this.setIconImage(new ImageIcon("images/afe.jpg").getImage());//����ͼ��
		this.setTitle("��de����ϵͳ");
		this.setSize(388, 320);
		
		//���õ�½��������Ļ����λ�ó���
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();//��Ļ�ĳߴ�
		this.setLocation((d.width - this.getWidth()) / 2, (d.height - this.getHeight())/2);
		
		initUI();
		
		//�رմ�����ֱ���˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void initUI(){
		JLabel lbl_logo = new JLabel(new ImageIcon("images/bei2.jpg"));
		this.add(lbl_logo, BorderLayout.NORTH);
		
		//������������
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createEtchedBorder());
		this.add(panel, BorderLayout.CENTER);
		
		//����Ϊ�ֶ�������
		panel.setLayout(null);
		
		//�����û�����
		JLabel lbl_lname = new JLabel("�û�����");
		lbl_lname.setBounds(85, 20, 50, 22);
		panel.add(lbl_lname);
		
		tfd_lname.setBounds(145, 20, 150, 22);
		panel.add(tfd_lname);//���ڽ����λ��
		tfd_lname.setText("jack");
		
		//���������
		JLabel lbl_pwd = new JLabel("��  �룺");
		lbl_pwd.setBounds(85, 55, 50, 22);
		panel.add(lbl_pwd);
		
		pfd_pwd.setBounds(145, 55, 150, 22);
		panel.add(pfd_pwd);//���ڽ����λ��
		pfd_pwd.setText("jack");
		
		//��ť
		JPanel pnl_south = new JPanel();
		pnl_south.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		this.add(pnl_south, BorderLayout.SOUTH);
		
		JButton btn_submit = new JButton("��¼");
		pnl_south.add(btn_submit);
		
		
		//ע���¼�������
		btn_submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		
		JButton btn_reset = new JButton("����");
		btn_reset.setBounds(220, 100, 60, 22);
		pnl_south.add(btn_reset);
	}
	
	//ִ�е�¼ʱ���¼�������
	private void doLogin(){
		
		//��ȡ�û���������
		String lname = tfd_lname.getText();
		@SuppressWarnings("deprecation")
		String pwd = pfd_pwd.getText();
		
		
		//���������Ƿ�Ϸ�
		if(null == lname ||"".equals(lname))
		{
			JOptionPane.showMessageDialog(this, "�û�������Ϊ�գ�", "��������", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if(null == pwd ||"".equals(pwd))
		{
			JOptionPane.showMessageDialog(this, "���벻��Ϊ�գ�", "��������", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		
		//����ManagerDao�����ж�
		ManagerDao dao = new ManagerDao();
		
		
		Manager mgr = dao.findOne(lname);
		if(mgr != null && mgr.getPwd().equals(pwd))
		{
			if(mgr.getStatus() == 0)//��½�ɹ�
			{
				MainFrame.getInstance(mgr);//������ҳ��
				LoginFrame.this.dispose();//���ٵ�½����
			}
			else
			{
				JOptionPane.showMessageDialog(this, "�û�������", "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(this, "�û����������", "��¼ʧ��", JOptionPane.ERROR_MESSAGE);
		}
	}
	
		
		 
		
	
	public static void main(String[] args) throws Exception {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		//��������
		UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LoginFrame();
			}
		});
	}
}