package com.jack.view.purchase;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.jack.dao.PurchaseDao;
import com.jack.model.PurchaseTableModel;
import com.jack.view.DateChooser;

public class StoragePurchaseFrame extends JInternalFrame
{
  private static final long serialVersionUID = 3641881158731346878L;
  private static StoragePurchaseFrame frame = new StoragePurchaseFrame();

  private String datePattern = "yyyy-MM-dd";
  private DateFormat df = new SimpleDateFormat(this.datePattern);
  private JTextField tfd_start_date;
  private JTextField tfd_end_date;
  private JButton btn_search;
  private JTable table;
  public PurchaseTableModel model;
  private PurchaseDao dao = new PurchaseDao();

  public static StoragePurchaseFrame getInstance() {
    return frame;
  }

  private StoragePurchaseFrame() {
    setFrameIcon(null);
    setTitle("入库采购单管理");
    setSize(550, 350);

    setResizable(true);
    setIconifiable(true);
    setMaximizable(true);
    setClosable(true);
    setDefaultCloseOperation(1);

    createButtonPanel();

    createMainPanel();
  }

  private void createButtonPanel() {
    JPanel north_panel = new JPanel();
    Border border = BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), 
      "组合查询条件", 
      2, 
      2);

    north_panel.setBorder(border);
    north_panel.setLayout(new BorderLayout());
    add(north_panel, "North");

    JPanel searchPanel = new JPanel(new FlowLayout(0, 15, 10));
    north_panel.add(searchPanel, "Center");

    JLabel lbl_date = new JLabel("采购日期：从");
    searchPanel.add(lbl_date);

    this.tfd_start_date = new JTextField(20);
    DateChooser dateChooser1 = DateChooser.getInstance(this.datePattern);
    dateChooser1.register(this.tfd_start_date);
    searchPanel.add(this.tfd_start_date);

    JLabel lbl_to = new JLabel("到");
    searchPanel.add(lbl_to);

    this.tfd_end_date = new JTextField(20);
    DateChooser dateChooser2 = DateChooser.getInstance(this.datePattern);
    dateChooser2.register(this.tfd_end_date);
    searchPanel.add(this.tfd_end_date);

    JPanel btnPanel = new JPanel(new FlowLayout(2, 15, 5));
    north_panel.add(btnPanel, "South");

    this.btn_search = new JButton("查询");
    btnPanel.add(this.btn_search);
    this.btn_search.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        try {
          StoragePurchaseFrame.this.doSearch();
        } catch (ParseException e1) {
          e1.printStackTrace();
        }
      }
    });
  }

  private void createMainPanel()
  {
    this.table = new JTable();

    this.table.setSelectionMode(0);

    this.model = new PurchaseTableModel();
    this.table.setModel(this.model);

    JScrollPane scrollPane = new JScrollPane(this.table, 
      22, 
      30);
    add(scrollPane, "Center");

    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List data = StoragePurchaseFrame.this.dao.findByCondition(2, 0L, 0L);
        StoragePurchaseFrame.this.model.setData(data);
      }
    });
  }

  private void doSearch() throws ParseException {
    String start = this.tfd_start_date.getText();
    long start_date = 0L;
    if ((start != null) && (!"".equals(start.trim()))) {
      start_date = this.df.parse(start.trim()).getTime();
    }

    String end = this.tfd_end_date.getText();
    long end_date = 0L;
    if ((end != null) && (!"".equals(end.trim())))
    {
      Date date = this.df.parse(end.trim());

      end_date = date.getTime() + 86400000L;
    }

    List data = this.dao.findByCondition(2, start_date, end_date);
    this.model.setData(data);
  }
}