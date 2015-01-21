package com.jack.view.customer;

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

import com.jack.dao.CustomerDao;
import com.jack.entity.Customer;
import com.jack.model.CustomerTableModel;
import com.jack.view.MainFrame;

/**
 * 对管理员进行管理的窗体
 * @author solo
 */
public class CustomerFrame extends JInternalFrame {
	private static final long serialVersionUID = 3641881158731346878L;
	private static CustomerFrame frame = new CustomerFrame();
	
	private JButton btn_add;
	private JButton btn_update;
	private JButton btn_delete;
	
	private JTable table;
	public CustomerTableModel model;
	private Customer selectedSup;
	
	private CustomerDao dao = new CustomerDao();
	
	public static CustomerFrame getInstance(){
		return frame;
	}
	
	private CustomerFrame(){
		this.setFrameIcon(null); //设置标题栏中的图标为null
		this.setTitle("客户管理");
		this.setSize(600, 400);
		
		this.setResizable(true);  //是否可调整大小
		this.setIconifiable(true); //是否可最小化
		this.setMaximizable(true);//是否可最大化
		this.setClosable(true); //是否可关闭
		this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE); //关闭操作为隐藏
		
		createButtonPanel();
		
		createMainPanel();
	}
	
	private void createButtonPanel(){
		JPanel btnPanel = new JPanel();
		this.add(btnPanel, BorderLayout.NORTH);
		
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		
		btn_add = new JButton("新增");
		btnPanel.add(btn_add);
		btn_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddCustomerDialog.getInstance().setVisible(true);;
			}
		});
		
		btn_update = new JButton("更新");
		btnPanel.add(btn_update);
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selectedSup != null){
					new UpdateCustomerDialog((JFrame) MainFrame.getInstance(), selectedSup);
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"请先选择要编辑的数据行", 
							"操作有误", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		btn_delete = new JButton("删除");
		btnPanel.add(btn_delete);
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("删除选中的客户...");
				if(selectedSup != null){
					int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
							"不可恢复删除，您确定吗？", 
							"删除确认", 
							JOptionPane.OK_CANCEL_OPTION);
					
					if(result == JOptionPane.OK_OPTION){
						dao.delete(selectedSup.getId());
						selectedSup = null;
						
						//重新加载数据
						model.setData(dao.findAll());
					}
				}else{
					JOptionPane.showMessageDialog(MainFrame.getInstance(), 
							"请先选择要删除的数据行", 
							"操作有误", 
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private void createMainPanel(){
		
		//添加表格组件
		table = new JTable();
		
		//设置为单选模式
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		model = new CustomerTableModel();
		table.setModel(model); //给表格设置数据模型
		
		
		
		//添加事件
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
            	// 取得点击所在行
            	int row = table.getSelectedRow();
            	 
            	
            	//设置当前选中的行数据
            	selectedSup = model.getValueAt(row);
            	//System.out.println(selectedMgr);
            }
       });
		
		JScrollPane scrollPane = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane, BorderLayout.CENTER);
		
		//初始化数据
		List<Customer> list = dao.findAll();
		model.setData(list);
	}
}