package com.jack.view.sales;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jack.dao.SalesDao;
import com.jack.entity.Sales;
import com.jack.model.SalesTableModel;
import com.jack.view.MainFrame;

public class SalesFrame extends JInternalFrame
{
  private static final long serialVersionUID = 3641881158731346878L;
  private static SalesFrame frame = new SalesFrame();
  private JButton btn_add;
  private JButton btn_update;
  private JButton btn_delete;
  private JButton btn_opt;
  private JButton btn_opt2;
  private JTable table;
  public SalesTableModel model;
  private Sales selectedSale;
  private SalesDao dao = new SalesDao();

  public static SalesFrame getInstance() {
    return frame;
  }

  private SalesFrame() {
    setFrameIcon(null);
    setTitle("销售单管理");
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
    JPanel btnPanel = new JPanel();
    add(btnPanel, "North");

    btnPanel.setLayout(new FlowLayout(0, 20, 10));

    this.btn_add = new JButton("新增");
    btnPanel.add(this.btn_add);
    this.btn_add.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        AddSalesDialog.getInstance().setVisible(true);
      }
    });
    this.btn_update = new JButton("更新");
    btnPanel.add(this.btn_update);
    this.btn_update.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        if (SalesFrame.this.selectedSale != null)
          new UpdateSalesDialog(SalesFrame.this.selectedSale);
        else
          JOptionPane.showMessageDialog(MainFrame.getInstance(), 
            "请先选择要编辑的数据行", 
            "操作有误", 
            2);
      }
    });
    this.btn_delete = new JButton("删除");
    btnPanel.add(this.btn_delete);
    this.btn_delete.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        if (SalesFrame.this.selectedSale != null) {
          int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
            "不可恢复删除，您确定吗？", 
            "删除确认", 
            2);

          if (result == 0) {
            SalesFrame.this.dao.delete(SalesFrame.this.selectedSale.getId());
            SalesFrame.this.selectedSale = null;

            SalesFrame.this.model.setData(SalesFrame.this.dao.findAll());

            SalesFrame.this.repaint();
          }
        } else {
          JOptionPane.showMessageDialog(MainFrame.getInstance(), 
            "请先选择要删除的数据行", 
            "操作有误", 
            2);
        }
      }
    });
    this.btn_opt = new JButton("下单");
    btnPanel.add(this.btn_opt);
    this.btn_opt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        SalesFrame.this.selectedSale.setStatus(1);

        SalesFrame.this.dao.update(SalesFrame.this.selectedSale);
        SalesFrame.this.model.setData(SalesFrame.this.dao.findAll());
        SalesFrame.this.repaint();
      }
    });
    this.btn_opt2 = new JButton("出库");
    btnPanel.add(this.btn_opt2);
    this.btn_opt2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        SalesFrame.this.selectedSale.setStatus(2);

        SalesFrame.this.dao.update(SalesFrame.this.selectedSale);
        SalesFrame.this.model.setData(SalesFrame.this.dao.findAll());
        SalesFrame.this.repaint();
      }
    });
  }

  private void createMainPanel()
  {
    this.table = new JTable();

    this.table.setSelectionMode(0);

    this.model = new SalesTableModel();
    this.table.setModel(this.model);

    this.table.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent evt) {
        int row = SalesFrame.this.table.getSelectedRow();

        SalesFrame.this.selectedSale = SalesFrame.this.model.getValueAt(row);

        SalesFrame.this.repaint();
      }
    });
    JScrollPane scrollPane = new JScrollPane(this.table, 
      22, 
      30);
    add(scrollPane, "Center");

    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List list = SalesFrame.this.dao.findAll();
        SalesFrame.this.model.setData(list);

        SalesFrame.this.selectedSale = null;
        SalesFrame.this.repaint();
      }
    });
  }

  public void paintComponent(Graphics g)
  {
    if (this.selectedSale != null) {
      if (this.selectedSale.getStatus() == 0) {
        this.btn_update.setEnabled(true);
        this.btn_delete.setEnabled(true);
        this.btn_opt.setEnabled(true);
        this.btn_opt2.setEnabled(false);
      } else if (this.selectedSale.getStatus() == 1) {
        this.btn_update.setEnabled(false);
        this.btn_delete.setEnabled(false);
        this.btn_opt.setEnabled(false);
        this.btn_opt2.setEnabled(true);
      } else {
        this.btn_update.setEnabled(false);
        this.btn_delete.setEnabled(false);
        this.btn_opt.setEnabled(false);
        this.btn_opt2.setEnabled(false);
      }
    } else {
      this.btn_update.setEnabled(false);
      this.btn_delete.setEnabled(false);
      this.btn_opt.setEnabled(false);
      this.btn_opt2.setEnabled(false);
    }
  }
}