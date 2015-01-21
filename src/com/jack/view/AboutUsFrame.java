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
 * 关于我们
 * @author solo
 */
public class AboutUsFrame extends JInternalFrame{
	private static final long serialVersionUID = -8904550137272125966L;

	private static AboutUsFrame frame = new AboutUsFrame();
	public static AboutUsFrame getInstance(){
		return frame;
	}
	
	private AboutUsFrame(){
		
		this.setFrameIcon(null); //设置标题栏中的图标为null
		this.setTitle("关于我们");
		this.setSize(600, 400);
		
		this.setResizable(true);  //是否可调整大小
		this.setIconifiable(true); //是否可最小化
		this.setMaximizable(true);//是否可最大化
		this.setClosable(true); //是否可关闭
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //关闭操作为隐藏
		
		
		createTitlePanel();//上
		createContentPanel();//中
		createBtnPanel();//下
	}
	
	private void createTitlePanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 15));
		JLabel lbl = new JLabel("关于我们");
		lbl.setFont(new Font("微软雅黑", Font.BOLD, 16));
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
		info.append("<p style='font-size:12px'>　　卓讯科技拥有一支勇于开拓、具有战略眼光和敏锐市场判断力的市场营销队伍，一批求实敬业、追求卓越的行政管理人才，一个能征善战、技术精湛、经验丰富的开发和咨询团队。</p><br/>");
		info.append("<p style='font-size:12px'>　　卓讯科技坚持按现代企业制度和市场规律办事，在扩大经营规模的同时，注重企业经济运行质量，在自主产品研发及承接软件项目方面获得了很强的竞争力。同时积极参与国内传统企业的信息化改造，引进国际化产品开发的标准，规范软件开发流程，通过提升各层面的软件开发人才的技术素质，打造国产软件精品，目前已经开发出具有自主知识产权的网络商城软件，还在积极开发基于电子商务平台高效能、高效益的管理系统。  </p><br/><br/>");
		info.append("Copyright &copy; 2015 json_jack All rights reserved.<br/>");
		info.append("网址： <a href='http://blog.csdn.net/qjyong'>http://blog.csdn.net/qjyong</a><br/>");
		info.append("电话：022-8718XXXX<br/>");
		info.append("</body></html>");
		label3.setText(info.toString());
		
		this.add(centerPanel, BorderLayout.CENTER);
	}
	
	private void createBtnPanel(){
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 10));
		
		JButton jbt = new JButton("关闭");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutUsFrame.this.setVisible(false); //关闭操作为隐藏
			}
		});
		btnPanel.add(jbt);
		
		this.add(btnPanel, BorderLayout.SOUTH);
	}
}