/**
 * 
 */
package com.jack.view.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jack.dao.CategoryDao;
import com.jack.dao.ProductDao;
import com.jack.entity.Category;
import com.jack.entity.Product;
import com.jack.view.MainFrame;

/**
 * ��ģʽ�ġ������Ի��򡱶Ի���
 * @author solo
 */
public class AddProductDialog extends JDialog {
	private static final long serialVersionUID = -3019483616331100043L;
	private JComboBox<Category> cbx_cate;
	private JTextField tfd_isbn;
	private JTextField tfd_name;
	private JTextField tfd_model;
	private JTextField tfd_unit;
	private JTextField tfd_price;
	private JTextField tfd_security;
	private JTextArea ta_summary;
	
	private DefaultComboBoxModel<Category> model;
	private CategoryDao cateDao = new CategoryDao();

	
	private static AddProductDialog dialog = new AddProductDialog(); 
	public static AddProductDialog getInstance(){
		return dialog;
	}
	private AddProductDialog(){
		super(MainFrame.getInstance(), "������Ʒ", true);
		
		this.setSize(520, 400);
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
		JLabel cateLbl = new JLabel("��������");
		cateLbl.setBounds(30, 20, 80, 22);
		centerPanel.add(cateLbl);
		
		cbx_cate = new JComboBox<Category>();
		cbx_cate.setBounds(110, 20, 140, 22);
		cbx_cate.requestFocusInWindow(); //��ȡ����
		centerPanel.add(cbx_cate);
		
		JLabel isbnLbl = new JLabel("ISBN����");
		isbnLbl.setBounds(270, 20, 60, 22);
		centerPanel.add(isbnLbl);
		
		tfd_isbn = new JTextField();
		tfd_isbn.setBounds(330, 20, 140, 22);
		centerPanel.add(tfd_isbn);
		
		/////////////////////////////////////
		JLabel nameLbl = new JLabel("��Ʒ��");
		nameLbl.setBounds(30, 55, 80, 22);
		centerPanel.add(nameLbl);
		
		tfd_name = new JTextField();
		tfd_name.setBounds(110, 55, 140, 22);
		centerPanel.add(tfd_name);
		
		
		JLabel modelLbl = new JLabel("���");
		modelLbl.setBounds(270, 55, 60, 22);
		centerPanel.add(modelLbl);
		
		tfd_model = new JTextField();
		tfd_model.setBounds(330, 55, 140, 22);
		centerPanel.add(tfd_model);
		
		///////////////////////////////////////
		JLabel lbl_unit = new JLabel("��λ");
		lbl_unit.setBounds(30, 95, 80, 22);
		centerPanel.add(lbl_unit);
		
		tfd_unit = new JTextField();
		tfd_unit.setBounds(110, 95, 140, 22);
		centerPanel.add(tfd_unit);
		
		JLabel lbl_price = new JLabel("���۵���");
		lbl_price.setBounds(270, 95, 80, 22);
		centerPanel.add(lbl_price);
		
		tfd_price = new JTextField();
		tfd_price.setBounds(330, 95, 140, 22);
		centerPanel.add(tfd_price);
		
		///////////////////////////////////////
		JLabel lbl_secuirty = new JLabel("��ȫ����");
		lbl_secuirty.setBounds(30, 135, 80, 22);
		centerPanel.add(lbl_secuirty);
		
		tfd_security = new JTextField();
		tfd_security.setBounds(110, 135, 140, 22);
		centerPanel.add(tfd_security);
		
		
		///////////////////////////////////////
		JLabel lbl_remark = new JLabel("��ע");
		lbl_remark.setBounds(30, 175, 80, 22);
		centerPanel.add(lbl_remark);
		
		ta_summary = new JTextArea();
		JScrollPane pane = new JScrollPane(ta_summary, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		pane.setBounds(110, 175, 360, 88);
		centerPanel.add(pane);
		
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				
				
				//���ط����б�����
				List<Category> cates = cateDao.findAll();
				
				model = new DefaultComboBoxModel<Category>(new Vector<Category>(cates));
				cbx_cate.setModel(model);
				
				if(cbx_cate.getItemCount() >= 0){
					cbx_cate.setSelectedIndex(0);
				}
				tfd_isbn.setText("");
				tfd_name.setText("");
				tfd_model.setText("");
				tfd_unit.setText("");
				tfd_price.setText("");
				tfd_security.setText("");
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
				if(cbx_cate.getItemCount() >= 0){
					cbx_cate.setSelectedIndex(0);
				}
				tfd_isbn.setText("");
				tfd_name.setText("");
				tfd_model.setText("");
				tfd_unit.setText("");
				tfd_price.setText("");
				tfd_security.setText("");
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
		Product prod = new Product();
		
		Category c = (Category)cbx_cate.getSelectedItem();
		prod.setCate_id(c.getId());
		
		prod.setIsbn(tfd_isbn.getText());
		prod.setName(tfd_name.getText());
		prod.setModel(tfd_model.getText());
		prod.setUnit(tfd_unit.getText());
		prod.setSale_price(new BigDecimal(tfd_price.getText()));
		prod.setSecurity_num(Integer.parseInt(tfd_security.getText()));
		prod.setSummary(ta_summary.getText());
		
		ProductDao dao = new ProductDao();
		dao.save(prod);
		
		//���±������
		ProductFrame.getInstance().model.setData(dao.findAll());
		this.setVisible(false);
	}
}