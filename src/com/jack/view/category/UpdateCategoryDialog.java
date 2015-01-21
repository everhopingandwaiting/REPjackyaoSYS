/**
 * 
 */
package com.jack.view.category;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jack.dao.CategoryDao;
import com.jack.entity.Category;
import com.jack.view.MainFrame;

/**
 * 有模式的“更新对话框”对话框
 * @author solo
 */
public class UpdateCategoryDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JTextField tfd_name;
	private JTextArea ta_summary;
	
	private Category curr_cate;
	public UpdateCategoryDialog(JFrame owner, Category cate){
		super(owner, "更新商品分类", true);
		
		curr_cate = cate;
		
		this.setSize(400, 280);
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
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
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
		tfd_name.setText(curr_cate.getName());
		
		/////////////////////////////////////
		JLabel modelLbl = new JLabel("描述");
		modelLbl.setBounds(30, 55, 60, 22);
		centerPanel.add(modelLbl);
		
		ta_summary = new JTextArea();
		
		JScrollPane pane = new JScrollPane(ta_summary, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(90, 55, 260, 88);
		centerPanel.add(pane);
		ta_summary.setText(curr_cate.getSummary());
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
		
		JButton jbt = new JButton("提交");
		jbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doUpdate();
			}
		});
		btnPanel.add(jbt);
	}
	
	private void doUpdate(){
		String name = tfd_name.getText();
		String summary = ta_summary.getText();
		
		
		curr_cate.setName(name);
		curr_cate.setSummary(summary);
		
		CategoryDao dao = new CategoryDao();
		dao.update(curr_cate);
		
		//更新表格数据
		CategoryFrame.getInstance().model.setData(dao.findAll());
		this.dispose();
	}
	
}