package com.jack.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * ��������
 * @author solo
 */
public class AboutUsFrame extends JInternalFrame{
	private static final long serialVersionUID = -8904550137272125966L;

	private static AboutUsFrame frame = new AboutUsFrame();
	public static AboutUsFrame getInstance(){
		return frame;
	}
	
	private AboutUsFrame(){
		
		this.setFrameIcon(null); //���ñ������е�ͼ��Ϊnull
		this.setTitle("��������");
		this.setSize(600, 400);
		
		this.setResizable(true);  //�Ƿ�ɵ�����С
		this.setIconifiable(true); //�Ƿ����С��
		this.setMaximizable(true);//�Ƿ�����
		this.setClosable(true); //�Ƿ�ɹر�
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //�رղ���Ϊ����
		
		
		createTitlePanel();//��
		createContentPanel();//��
		createBtnPanel();//��
	}
	
	private void createTitlePanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
		JLabel lbl = new JLabel("��������");
		lbl.setFont(new Font("΢���ź�", Font.BOLD, 16));
		panel.add(lbl);
		
		this.add(panel, BorderLayout.NORTH);
	}
	
	private void createContentPanel() {
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		
		JLabel label3 = new JLabel();
		centerPanel.add(label3, BorderLayout.CENTER);
		
		StringBuilder info = new StringBuilder();
		info.append("<html><body>");
		info.append("<p style='font-size:12px'>����׿Ѷ�Ƽ�ӵ��һ֧���ڿ��ء�����ս���۹�������г��ж������г�Ӫ�����飬һ����ʵ��ҵ��׷��׿Խ�����������˲ţ�һ��������ս��������տ������ḻ�Ŀ�������ѯ�Ŷӡ�</p><br/>");
		info.append("<p style='font-size:12px'>����׿Ѷ�Ƽ���ְ��ִ���ҵ�ƶȺ��г����ɰ��£�������Ӫ��ģ��ͬʱ��ע����ҵ����������������������Ʒ�з����н������Ŀ�������˺�ǿ�ľ�������ͬʱ����������ڴ�ͳ��ҵ����Ϣ�����죬�������ʻ���Ʒ�����ı�׼���淶����������̣�ͨ���������������������˲ŵļ������ʣ�������������Ʒ��Ŀǰ�Ѿ���������������֪ʶ��Ȩ�������̳���������ڻ����������ڵ�������ƽ̨��Ч�ܡ���Ч��Ĺ���ϵͳ��  </p><br/><br/>");
		info.append("Copyright &copy; 2015 json_jack All rights reserved.<br/>");
		info.append("��ַ�� <a href='http://blog.csdn.net/qjyong'>http://blog.csdn.net/qjyong</a><br/>");
		info.append("�绰��022-8718XXXX<br/>");
		info.append("</body></html>");
		label3.setText(info.toString());
		
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void createBtnPanel(){
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 10));
		
		JButton jbt = new JButton("�ر�");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutUsFrame.this.setVisible(false); //�رղ���Ϊ����
			}
		});
		btnPanel.add(jbt);
		
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}