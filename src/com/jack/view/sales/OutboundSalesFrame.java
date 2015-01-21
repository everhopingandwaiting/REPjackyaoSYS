package com.jack.view.sales;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import com.jack.dao.SalesDao;
import com.jack.model.SalesTableModel;
import com.jack.view.DateChooser;

public class OutboundSalesFrame extends JInternalFrame
{
  private static final long serialVersionUID = 3641881158731346878L;
  private static OutboundSalesFrame frame = new OutboundSalesFrame();

  private String datePattern = "yyyy-MM-dd";
  private DateFormat df = new SimpleDateFormat(this.datePattern);
  private JTextField tfd_start_date;
  private JTextField tfd_end_date;
  private JButton btn_search;
  private JTable table;
  public SalesTableModel model;
  private SalesDao dao = new SalesDao();

  public static OutboundSalesFrame getInstance() {
    return frame;
  }

  private OutboundSalesFrame() {
    setFrameIcon(null);
    setTitle("出库采购单管理");
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

    JLabel lbl_date = new JLabel("销售日期：从");
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
          OutboundSalesFrame.this.doSearch();
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

    this.model = new SalesTableModel();
    this.table.setModel(this.model);

    JScrollPane scrollPane = new JScrollPane(this.table, 
      22, 
      30);
    add(scrollPane, "Center");

    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List data = OutboundSalesFrame.this.dao.findByCondition(2, 0L, 0L);
        OutboundSalesFrame.this.model.setData(data);
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
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      c.add(5, 1);

      end_date = c.getTimeInMillis();
    }

    List data = this.dao.findByCondition(2, start_date, end_date);
    this.model.setData(data);
  }
}