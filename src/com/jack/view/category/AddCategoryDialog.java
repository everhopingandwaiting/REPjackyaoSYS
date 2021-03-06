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
 * 有模式的“新增对话框”对话框
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
		super(MainFrame.getInstance(), "新增商品分类", true);
		this.setSize(400,280);
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
		//panel.add(new JLabel("新增"));
		cp.add(panel, BorderLayout.NORTH);
	}
	
	public void createContentPanel(JPanel cp){
		JPanel centerPanel = new JPanel(null);
		centerPanel.setBackground(Color.WHITE);
		cp.add(centerPanel, BorderLayout.CENTER);
		
		
		/////////////////////////////////////
		JLabel nameLbl = new JLabel("分类名");
		nameLbl.setBounds(30, 20, 60, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(90, 20, 260, 22);
		tfd_name.requestFocusInWindow(); //获取焦点
		centerPanel.add(tfd_name);
		
		
		JLabel cateLbl = new JLabel("描述");
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
		//下部分
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 15));
		cp.add(btnPanel, BorderLayout.SOUTH);
		
		JButton jbt2 = new JButton("重置");
		jbt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfd_name.setText("");
				ta_summary.setText("");
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
		String name = tfd_name.getText();
		String summary = ta_summary.getText();
		

		Category cate = new Category();
		cate.setName(name);
		cate.setSummary(summary);
		
		CategoryDao dao = new CategoryDao();
		dao.save(cate);
		
		//更新表格数据
		CategoryFrame.getInstance().model.setData(dao.findAll());
		this.setVisible(false);
	}
	
	public static void main(String[] args) {
		new AddCategoryDialog();
	}
}