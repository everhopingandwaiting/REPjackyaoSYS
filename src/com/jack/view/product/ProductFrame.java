package com.jack.view.product;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.jack.dao.ProductDao;
import com.jack.entity.Product;
import com.jack.model.ProductTableModel;
import com.jack.view.MainFrame;

/**
 * ��Ʒ��Ϣ
 * @author solo
 */
public class ProductFrame extends JInternalFrame {
	private static final long serialVersionUID = 3641881158731346878L;
	private static ProductFrame frame = new ProductFrame();
	
	private JButton btn_add;
	private JButton btn_update;
	private JButton btn_delete;
	
	private JTable table;
	public ProductTableModel model;
	private Product selectedPro;
	
	private ProductDao dao = new ProductDao();
	
	public static ProductFrame getInstance(){
		return frame;
	}
	
	private ProductFrame(){
		this.setFrameIcon(null); //���ñ������е�ͼ��Ϊnull
		this.setTitle("��Ʒ����");
		this.setSize(600, 400);
		
		this.setResizable(true);  //�Ƿ�ɵ�����С
		this.setIconifiable(true); //�Ƿ����С��
		this.setMaximizable(true);//�Ƿ�����
		this.setClosable(true); //�Ƿ�ɹر�
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //�رղ���Ϊ����
		
		createButtonPanel();
		
		createMainPanel();
	}
	
	private void createButtonPanel(){
		JPanel btnPanel = new JPanel();
		this.add(btnPanel, BorderLayout.NORTH);
		
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		
		this.btn_add = new JButton("����");
	    btnPanel.add(this.btn_add);
	    this.btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProductDialog.getInstance().setVisible(true);;
			}
		});
		
	    this.btn_update = new JButton("����");
	    btnPanel.add(this.btn_update);
	    this.btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedPro != null){
					new UpdateProductDialog((JFrame) MainFrame.getInstance(), selectedPro);
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"����ѡ��Ҫ�༭��������", 
							"��������", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
	    this.btn_delete = new JButton("ɾ��");
	    btnPanel.add(this.btn_delete);
	    this.btn_delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//System.out.println("ɾ��ѡ�еĹ�Ӧ��...");
				 if (ProductFrame.this.selectedPro != null){
					int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
							"���ɻָ�ɾ������ȷ����", 
							"ɾ��ȷ��", 
							JOptionPane.OK_CANCEL_OPTION);
					
					if(result == JOptionPane.OK_OPTION){
						 ProductFrame.this.dao.delete(ProductFrame.this.selectedPro.getId());
				         ProductFrame.this.selectedPro = null;
						
						//���¼�������
				            ProductFrame.this.model.setData(ProductFrame.this.dao.findAll());
					}
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"����ѡ��Ҫɾ����������", 
							"��������", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void createMainPanel(){
		
		//��ӱ�����
		this.table = new JTable();
		
		//����Ϊ��ѡģʽ
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		this.model = new ProductTableModel();
		this.table.setModel(model); //�������������ģ��
		
		
		
		//����¼�
        this.table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	// ȡ�õ��������
            	int row = ProductFrame.this.table.getSelectedRow();
            	 
            	
            	//���õ�ǰѡ�е�������
            	 ProductFrame.this.selectedPro = ProductFrame.this.model.getValueAt(row);
            	//System.out.println(selectedMgr);
            }
       });
		
		JScrollPane scrollPane = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		//��ʼ������
		List<Product> list = dao.findAll();
		model.setData(list);
	}
}