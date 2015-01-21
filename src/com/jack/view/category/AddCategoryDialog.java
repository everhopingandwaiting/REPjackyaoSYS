/**
 * 
 */
package com.jack.view.category;

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

import com.jack.dao.CategoryDao;
import com.jack.entity.Category;
import com.jack.view.category.AddCategoryDialog;
import com.jack.view.category.CategoryFrame;
import com.jack.view.MainFrame;

/**
 * ��ģʽ�ġ������Ի��򡱶Ի���
 * @author solo
 */
public class AddCategoryDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JTextField tfd_name;
	private JTextArea ta_summary;
	
	private static AddCategoryDialog dialog = new AddCategoryDialog(); 
	public static AddCategoryDialog getInstance(){
		return dialog;
	}

	private AddCategoryDialog() {
		super(MainFrame.getInstance(), "������Ʒ����", true);
		this.setSize(400,280);
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
		//panel.add(new JLabel("����"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		cp.add(centerPanel, BorderLayout.CENTER);
		
		
		/////////////////////////////////////
		JLabel nameLbl = new JLabel("������");
		nameLbl.setBounds(30, 20, 60, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(90, 20, 260, 22);
		tfd_name.requestFocusInWindow(); //��ȡ����
		centerPanel.add(tfd_name);
		
		
		JLabel cateLbl = new JLabel("����");
		cateLbl.setBounds(30, 55, 60, 22);
		centerPanel.add(cateLbl);
		
		ta_summary = new JTextArea();
		
		
		JScrollPane pane = new JScrollPane(ta_summary, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(90, 55, 260, 88);
		
		centerPanel.add(pane);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				tfd_name.setText("");
				ta_summary.setText("");
			}
		});
		
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
				ta_summary.setText("");
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
		String name = tfd_name.getText();
		String summary = ta_summary.getText();
		

		Category cate = new Category();
		cate.setName(name);
		cate.setSummary(summary);
		
		CategoryDao dao = new CategoryDao();
		dao.save(cate);
		
		//���±������
		CategoryFrame.getInstance().model.setData(dao.findAll());
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		new AddCategoryDialog();
	}
}