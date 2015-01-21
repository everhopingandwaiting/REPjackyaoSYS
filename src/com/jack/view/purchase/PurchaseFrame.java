package com.jack.view.purchase;


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

import com.jack.dao.PurchaseDao;
import com.jack.entity.Purchase;
import com.jack.model.PurchaseTableModel;
import com.jack.view.MainFrame;

public class PurchaseFrame extends JInternalFrame
{
  private static final long serialVersionUID = 3641881158731346878L;
  private static PurchaseFrame frame = new PurchaseFrame();
  private JButton btn_add;
  private JButton btn_update;
  private JButton btn_delete;
  private JButton btn_opt;
  private JButton btn_opt2;
  private JTable table;
  public PurchaseTableModel model;
  private Purchase selectedPurc;
  private PurchaseDao dao = new PurchaseDao();

  public static PurchaseFrame getInstance() {
    return frame;
  }

  private PurchaseFrame() {
    setFrameIcon(null);
    setTitle("采购单管理");
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
        AddPurchaseDialog.getInstance().setVisible(true);
      }
    });
    this.btn_update = new JButton("更新");
    btnPanel.add(this.btn_update);
    this.btn_update.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        if (PurchaseFrame.this.selectedPurc != null)
          new UpdatePurchaseDialog(PurchaseFrame.this.selectedPurc);
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
        if (PurchaseFrame.this.selectedPurc != null) {
          int result = JOptionPane.showConfirmDialog(MainFrame.getInstance(), 
            "不可恢复删除，您确定吗？", 
            "删除确认", 
            2);

          if (result == 0) {
            PurchaseFrame.this.dao.delete(PurchaseFrame.this.selectedPurc.getId());
            PurchaseFrame.this.selectedPurc = null;

            PurchaseFrame.this.model.setData(PurchaseFrame.this.dao.findAll());

            PurchaseFrame.this.repaint();
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
        PurchaseFrame.this.selectedPurc.setStatus(1);

        PurchaseFrame.this.dao.update(PurchaseFrame.this.selectedPurc);
        PurchaseFrame.this.model.setData(PurchaseFrame.this.dao.findAll());
        PurchaseFrame.this.repaint();
      }
    });
    this.btn_opt2 = new JButton("入库");
    btnPanel.add(this.btn_opt2);
    this.btn_opt2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        PurchaseFrame.this.selectedPurc.setStatus(2);

        PurchaseFrame.this.dao.update(PurchaseFrame.this.selectedPurc);
        PurchaseFrame.this.model.setData(PurchaseFrame.this.dao.findAll());
        PurchaseFrame.this.repaint();
      }
    });
  }

  private void createMainPanel()
  {
    this.table = new JTable();

    this.table.setSelectionMode(0);

    this.model = new PurchaseTableModel();
    this.table.setModel(this.model);

    this.table.addMouseListener(new MouseAdapter()
    {
      public void mousePressed(MouseEvent evt) {
        int row = PurchaseFrame.this.table.getSelectedRow();

        PurchaseFrame.this.selectedPurc = PurchaseFrame.this.model.getValueAt(row);

        PurchaseFrame.this.repaint();
      }
    });
    JScrollPane scrollPane = new JScrollPane(this.table, 
      22, 
      30);
    add(scrollPane, "Center");

    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List list = PurchaseFrame.this.dao.findAll();
        PurchaseFrame.this.model.setData(list);

        PurchaseFrame.this.selectedPurc = null;
        PurchaseFrame.this.repaint();
      }
    });
  }

  public void paintComponent(Graphics g)
  {
    if (this.selectedPurc != null) {
      if (this.selectedPurc.getStatus() == 0) {
        this.btn_update.setEnabled(true);
        this.btn_delete.setEnabled(true);
        this.btn_opt.setEnabled(true);
        this.btn_opt2.setEnabled(false);
      } else if (this.selectedPurc.getStatus() == 1) {
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