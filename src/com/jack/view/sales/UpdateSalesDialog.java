package com.jack.view.sales;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jack.dao.CustomerDao;
import com.jack.dao.SalesDao;
import com.jack.dao.SalesItemDao;
import com.jack.entity.Customer;
import com.jack.entity.PurchaseItem;
import com.jack.entity.Sales;
import com.jack.entity.SalesItem;
import com.jack.model.SalesItemTableModel;
import com.jack.view.MainFrame;
import com.jack.view.purchase.UpdatePurchaseItemEditDialog;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class UpdateSalesDialog extends JDialog
{
  private static final long serialVersionUID = -7091891216420430839L;
  private DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
  private NumberFormat nf = new DecimalFormat("￥#,##0.00元");
  private CustomerDao suppDao = new CustomerDao();
  private SalesDao purcDao = new SalesDao();
  private SalesItemDao itemDao = new SalesItemDao();
  private Sales selectedSales;
  private JComboBox cbx_supp;
  private DefaultComboBoxModel cbx_model;
  private static JTextField tfd_mgr;
  private JTextField tfd_date;
  private JRadioButton rdo_all;
  private JRadioButton rdo_2;
  private JTextArea ta_summary;
  private JTable table;
  public static SalesItemTableModel model;
  private int selectedRow = -1;

  public UpdateSalesDialog(Sales purc)
  {
    super(MainFrame.getInstance(), "编辑销售单", true);

    this.selectedSales = purc;

    setSize(520, 500);
    setResizable(false);
    setLocationRelativeTo(MainFrame.getInstance());

    JPanel cp = (JPanel)getContentPane();
    cp.setLayout(new BorderLayout());

    createTitlePanel(cp);

    createContentPanel(cp);

    createBtnPanel(cp);

    setDefaultCloseOperation(2);
    setVisible(true);
  }

  public void createTitlePanel(JPanel cp) {
    JPanel panel = new JPanel(new FlowLayout(0, 12, 15));
    cp.add(panel, "North");
  }

  public void createContentPanel(JPanel cp)
  {
    JPanel centerPanel = new JPanel(null);
    centerPanel.setBackground(Color.WHITE);
    cp.add(centerPanel, "Center");

    JLabel cateLbl = new JLabel("所属客户");
    cateLbl.setBounds(30, 20, 80, 22);
    centerPanel.add(cateLbl);

    this.cbx_supp = new JComboBox();
    this.cbx_supp.setBounds(110, 20, 140, 22);
    this.cbx_supp.requestFocusInWindow();
    centerPanel.add(this.cbx_supp);

    JLabel isbnLbl = new JLabel("下单员");
    isbnLbl.setBounds(270, 20, 60, 22);
    centerPanel.add(isbnLbl);

    tfd_mgr = new JTextField();
    tfd_mgr.setBounds(330, 20, 140, 22);
    centerPanel.add(tfd_mgr);
    tfd_mgr.setEditable(false);

    JLabel nameLbl = new JLabel("销售日期");
    nameLbl.setBounds(30, 55, 80, 22);
    centerPanel.add(nameLbl);

    this.tfd_date = new JTextField();
    this.tfd_date.setBounds(110, 55, 140, 22);
    centerPanel.add(this.tfd_date);

    JLabel modelLbl = new JLabel("付款方式");
    modelLbl.setBounds(270, 55, 60, 22);
    centerPanel.add(modelLbl);

    ButtonGroup group_status = new ButtonGroup();

    this.rdo_all = new JRadioButton("全款");
    group_status.add(this.rdo_all);
    this.rdo_all.setSelected(true);
    this.rdo_all.setBackground(Color.WHITE);
    this.rdo_all.setBounds(330, 55, 60, 22);
    centerPanel.add(this.rdo_all);

    this.rdo_2 = new JRadioButton("欠款");
    group_status.add(this.rdo_2);
    this.rdo_2.setBackground(Color.WHITE);
    this.rdo_2.setBounds(400, 55, 60, 22);
    centerPanel.add(this.rdo_2);

    JLabel lbl_remark = new JLabel("备注");
    lbl_remark.setBounds(30, 90, 80, 22);
    centerPanel.add(lbl_remark);

    this.ta_summary = new JTextArea();
    JScrollPane pane = new JScrollPane(this.ta_summary, 
      22, 
      30);
    pane.setBounds(110, 90, 360, 66);
    centerPanel.add(pane);

    JPanel pane2 = new JPanel();
    pane2.setBorder(BorderFactory.createTitledBorder("销售明细"));
    pane2.setBounds(25, 170, 450, 200);
    pane2.setBackground(Color.WHITE);
    centerPanel.add(pane2);
    pane2.setLayout(new BorderLayout());

    JPanel btnPane = new JPanel(new FlowLayout(0, 15, 10));
    btnPane.setBackground(Color.WHITE);
    pane2.add(btnPane, "North");
    JButton jbt2 = new JButton("增加明细");
    jbt2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        UpdateSalesItemAddDialog.getInstance(UpdateSalesDialog.this.model).setVisible(true);
      }
    });
    btnPane.add(jbt2);

    JButton jbt3 = new JButton("编辑明细");
    jbt3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
    	  SalesItem item=new SalesItem(); 
            item=model.getValueAt(selectedRow);
      
      	
      	  if (selectedRow > -1)
      	  new  UpdateSalesItemEditDialog(item,table.getSelectedRow());
      }
    });
    btnPane.add(jbt3);

    JButton jbt4 = new JButton("删除明细");
    jbt4.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        if (UpdateSalesDialog.this.selectedRow > -1)
          UpdateSalesDialog.this.model.remove(UpdateSalesDialog.this.selectedRow);
      }
    });
    btnPane.add(jbt4);

    this.table = new JTable();
    JScrollPane pane3 = new JScrollPane(this.table, 
      22, 
      30);
    pane3.setBackground(Color.WHITE);
    this.table.setBackground(Color.WHITE);
    pane2.add(pane3, "Center");

    this.table.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent evt) {
        int row = UpdateSalesDialog.this.table.getSelectedRow();
        UpdateSalesDialog.this.selectedRow = row;
      }
    });
    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List list = UpdateSalesDialog.this.suppDao.findAll();
        UpdateSalesDialog.this.cbx_model = new DefaultComboBoxModel(new Vector(list));
        UpdateSalesDialog.this.cbx_supp.setModel(UpdateSalesDialog.this.cbx_model);

        for (int i = 0; i < UpdateSalesDialog.this.cbx_model.getSize(); i++) {
          Customer temp = (Customer)UpdateSalesDialog.this.cbx_model.getElementAt(i);
          if (UpdateSalesDialog.this.selectedSales.getCustomer_id() == temp.getId()) {
            UpdateSalesDialog.this.cbx_supp.setSelectedIndex(i);
            break;
          }
        }

        UpdateSalesDialog.tfd_mgr.setText(UpdateSalesDialog.this.selectedSales.getMgrName());
        UpdateSalesDialog.this.tfd_date.setText(UpdateSalesDialog.this.df.format(new Date(UpdateSalesDialog.this.selectedSales.getSale_date())));
        UpdateSalesDialog.this.ta_summary.setText(UpdateSalesDialog.this.selectedSales.getRemark());

        if (UpdateSalesDialog.this.selectedSales.getPay_type() == 0)
          UpdateSalesDialog.this.rdo_all.setSelected(true);
        else {
          UpdateSalesDialog.this.rdo_2.setSelected(true);
        }

        UpdateSalesDialog.this.model = new SalesItemTableModel();
        UpdateSalesDialog.this.table.setModel(UpdateSalesDialog.this.model);
        UpdateSalesDialog.this.model.setData(UpdateSalesDialog.this.itemDao.findBySalesId(UpdateSalesDialog.this.selectedSales.getId()));
      }
    });
  }

  public void createBtnPanel(JPanel cp)
  {
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new FlowLayout(2, 12, 15));
    cp.add(btnPanel, "South");

    JButton jbt2 = new JButton("取消");
    jbt2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        UpdateSalesDialog.this.dispose();
      }
    });
    btnPanel.add(jbt2);

    JButton jbt = new JButton("确定");
    jbt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        UpdateSalesDialog.this.doUpdate();
      }
    });
    btnPanel.add(jbt);
  }

  private void doUpdate()
  {
    Customer supp = (Customer)this.cbx_supp.getSelectedItem();
    this.selectedSales.setCustomer_id(supp.getId());
    this.selectedSales.setManager_id(MainFrame.getInstance().curr_mgr.getId());
    this.selectedSales.setSale_date(new Date().getTime());
    this.selectedSales.setRemark(this.ta_summary.getText());

    int pay_type = 0;
    if (this.rdo_all.isSelected())
      pay_type = 0;
    else {
      pay_type = -1;
    }
    this.selectedSales.setPay_type(pay_type);
    this.selectedSales.setStatus(0);

    BigDecimal total = new BigDecimal(0);

    List<SalesItem> list = this.model.getList();
    SalesItem info;
    for (int i = 0; i < list.size(); i++) {
      info = (SalesItem)list.get(i);
      total = total.add(info.getPrice().multiply(new BigDecimal(info.getNum())));
    }
    this.selectedSales.setCost(total);

    this.purcDao.update(this.selectedSales);

    this.itemDao.deleteBySalesId(this.selectedSales.getId());
    for (SalesItem item : list) {
      item.setSales_id(this.selectedSales.getId());
      System.out.println("save---->" + item);
      this.itemDao.save(item);
    }

    SalesFrame.getInstance().model.setData(this.purcDao.findAll());
    dispose();
  }
}