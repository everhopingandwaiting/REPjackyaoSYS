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
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DateFormat;
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

import com.jack.dao.PurchaseDao;
import com.jack.dao.PurchaseItemDao;
import com.jack.dao.SupplierDao;
import com.jack.entity.Purchase;
import com.jack.entity.PurchaseItem;
import com.jack.entity.Supplier;
import com.jack.model.PurchaseItemTableModel;
import com.jack.view.MainFrame;

public class UpdatePurchaseDialog extends JDialog
{
  private static final long serialVersionUID = -7091891216420430839L;
  private DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");

  private SupplierDao suppDao = new SupplierDao();
  private PurchaseDao purcDao = new PurchaseDao();
  private PurchaseItemDao itemDao = new PurchaseItemDao();
  private Purchase selectedPurc;
  private JComboBox cbx_supp;
  private DefaultComboBoxModel cbx_model;
  private static JTextField tfd_mgr;
  private JTextField tfd_date;
  private JRadioButton rdo_all;
  private JRadioButton rdo_2;
  private JTextArea ta_summary;
  private JTable table;
  public static PurchaseItemTableModel model;
  private int selectedRow = -1;

  public UpdatePurchaseDialog(Purchase purc)
  {
    super(MainFrame.getInstance(), "编辑采购单", true);

    this.selectedPurc = purc;

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
    pane2.setBorder(BorderFactory.createTitledBorder("采购明细"));
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
        UpdatePurchaseItemAddDialog.getInstance(model).setVisible(true);
      }
    });
    btnPane.add(jbt2);

    JButton jbt3 = new JButton("编辑明细");
    jbt3.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {    PurchaseItem item=new PurchaseItem(); 
    	  item =model.getValueAt(table.getSelectedRow());
    	  if (selectedRow > -1)
    	  new  UpdatePurchaseItemEditDialog(item,table.getSelectedRow());
      }
    });
    btnPane.add(jbt3);

    JButton jbt4 = new JButton("删除明细");
    jbt4.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
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
        List list = suppDao.findAll();
        cbx_model = new DefaultComboBoxModel(new Vector(list));
        cbx_supp.setModel(cbx_model);

        for (int i = 0; i < cbx_model.getSize(); i++) {
          Supplier temp = (Supplier)cbx_model.getElementAt(i);
          if (selectedPurc.getSupplier_id() == temp.getId()) {
            cbx_supp.setSelectedIndex(i);
            break;
          }
        }

        UpdatePurchaseDialog.tfd_mgr.setText(selectedPurc.getMgrName());
        tfd_date.setText(df.format(new Date(selectedPurc.getPur_date())));
        ta_summary.setText(selectedPurc.getRemark());

        if (selectedPurc.getPay_type() == 0)
          rdo_all.setSelected(true);
        else {
          rdo_2.setSelected(true);
        }

        model = new PurchaseItemTableModel();
        table.setModel(model);
        model.setData(itemDao.findByPurchaseId(selectedPurc.getId()));
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
        UpdatePurchaseDialog.this.dispose();
      }
    });
    btnPanel.add(jbt2);

    JButton jbt = new JButton("确定");
    jbt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        doUpdate();
      }
    });
    btnPanel.add(jbt);
  }

  private void doUpdate()
  {
    Supplier supp = (Supplier)this.cbx_supp.getSelectedItem();
    this.selectedPurc.setSupplier_id(supp.getId());
    this.selectedPurc.setManager_id(MainFrame.getInstance().curr_mgr.getId());
    this.selectedPurc.setPur_date(new Date().getTime());
    this.selectedPurc.setRemark(this.ta_summary.getText());

    int pay_type = 0;
    if (this.rdo_all.isSelected())
      pay_type = 0;
    else {
      pay_type = -1;
    }
    this.selectedPurc.setPay_type(pay_type);
    this.selectedPurc.setStatus(0);

    BigDecimal total = new BigDecimal(0);
   // 获取明细列表
    List<PurchaseItem> list = this.model.getList();
    PurchaseItem info;
    for (int i = 0; i < list.size(); i++) {
      info = (PurchaseItem)list.get(i);
      total = total.add(info.getPrice().multiply(new BigDecimal(info.getNum())));
    }  // 计算总金额
    this.selectedPurc.setCost(total);

    this.purcDao.update(this.selectedPurc);

    this.itemDao.deleteByPurchaseId(this.selectedPurc.getId());
    for (PurchaseItem item : list) {
      item.setPurchase_id(this.selectedPurc.getId());
       // 保存每个明细到数据库 
      this.itemDao.save(item);
    }

    PurchaseFrame.getInstance().model.setData(this.purcDao.findAll());
    dispose();
  }
}