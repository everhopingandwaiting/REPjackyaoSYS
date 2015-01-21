package com.jack.view.purchase;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
import javax.swing.border.Border;

import com.jack.dao.PurchaseDao;
import com.jack.dao.PurchaseItemDao;
import com.jack.dao.SupplierDao;
import com.jack.entity.Purchase;
import com.jack.entity.PurchaseItem;
import com.jack.entity.Supplier;
import com.jack.model.PurchaseItemTableModel;
import com.jack.view.MainFrame;

public class AddPurchaseDialog extends JDialog
{
  private static final long serialVersionUID = -7091891216420430839L;
  private static AddPurchaseDialog dialog = new AddPurchaseDialog();
  private DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
  private NumberFormat nf = new DecimalFormat("￥#,##0.00元");
  private SupplierDao suppDao = new SupplierDao();
  private PurchaseDao purcDao = new PurchaseDao();
  private PurchaseItemDao itemDao = new PurchaseItemDao();
  private JComboBox cbx_supp;
  private DefaultComboBoxModel cbx_model;
  private static JTextField tfd_mgr;
  private JTextField tfd_date;
  private JRadioButton rdo_all;
  private JRadioButton rdo_2;
  private JTextArea ta_summary;
  private JTable table;
  public PurchaseItemTableModel model;
  private int selectedRow = -1;
  public static AddPurchaseDialog getInstance()
  {
    return dialog;
  }

  private AddPurchaseDialog() {
    super(MainFrame.getInstance(), "新增采购单", true);

    setSize(520, 500);
    setResizable(false);
    setLocationRelativeTo(MainFrame.getInstance());

    JPanel cp = (JPanel)getContentPane();
    cp.setLayout(new BorderLayout());

    createTitlePanel(cp);

    createContentPanel(cp);

    createBtnPanel(cp);

    setDefaultCloseOperation(1);
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

    JLabel cateLbl = new JLabel("所属供应商");
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

    JLabel nameLbl = new JLabel("采购日期");
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
    Border border = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "采购明细");

    pane2.setBorder(border);
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
        AddPurchaseItemDialog.getInstance().setVisible(true);
      }
    });
    btnPane.add(jbt2);

    JButton jbt3 = new JButton("编辑明细");
    jbt3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {		 PurchaseItem item=new PurchaseItem(); 
      		
      	item =model.getValueAt(table.getSelectedRow());
    	  if (selectedRow > -1)
    	  new  AddPurchaseItemEditDialog(item,table.getSelectedRow());
      }
    });
    btnPane.add(jbt3);

    JButton jbt4 = new JButton("删除明细");
    jbt4.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
    	  if (selectedRow > -1)
              model.remove(selectedRow);
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
        int row = table.getSelectedRow();
        selectedRow = row;
      }
    });
    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List list = AddPurchaseDialog.this.suppDao.findAll();
        AddPurchaseDialog.this.cbx_model = new DefaultComboBoxModel(new Vector(list));
        AddPurchaseDialog.this.cbx_supp.setModel(AddPurchaseDialog.this.cbx_model);
        if (AddPurchaseDialog.this.cbx_supp.getItemCount() >= 0) {
          AddPurchaseDialog.this.cbx_supp.setSelectedIndex(0);
        }
        AddPurchaseDialog.tfd_mgr.setText(MainFrame.getInstance().curr_mgr.getLname());

        AddPurchaseDialog.this.tfd_date.setText(AddPurchaseDialog.this.df.format(new Date()));

        AddPurchaseDialog.this.model = new PurchaseItemTableModel();
        AddPurchaseDialog.this.table.setModel(AddPurchaseDialog.this.model);
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
        AddPurchaseDialog.this.setVisible(false);
      }
    });
    btnPanel.add(jbt2);

    JButton jbt = new JButton("新增");
    jbt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        AddPurchaseDialog.this.doAdd();
      }
    });
    btnPanel.add(jbt);
  }

  private void doAdd()
  {
    Purchase purc = new Purchase();
    purc.setSn(UUID.randomUUID().toString().replaceAll("-", ""));
    Supplier supp = (Supplier)this.cbx_supp.getSelectedItem();
    purc.setSupplier_id(supp.getId());
    purc.setManager_id(MainFrame.getInstance().curr_mgr.getId());
    purc.setPur_date(new Date().getTime());
    purc.setRemark(this.ta_summary.getText());

    int pay_type = 0;
    if (this.rdo_all.isSelected())
      pay_type = 0;
    else {
      pay_type = -1;
    }
    purc.setPay_type(pay_type);
    purc.setStatus(0);

    BigDecimal total = new BigDecimal(0);

    List<PurchaseItem> list = this.model.getList();
    PurchaseItem item;
    for (int i = 0; i < list.size(); i++) {
      item = (PurchaseItem)list.get(i);
      total = total.add(item.getPrice().multiply(new BigDecimal(item.getNum())));
    }
    purc.setCost(total);

    this.purcDao.save(purc);

    for (PurchaseItem item1 : list) {
      item1.setPurchase_id(purc.getId());
      this.itemDao.save(item1);
    }

    PurchaseFrame.getInstance().model.setData(this.purcDao.findAll());
    setVisible(false);
  }
}