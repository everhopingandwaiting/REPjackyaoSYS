package com.jack.view.purchase;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jack.dao.ProductDao;
import com.jack.entity.Product;
import com.jack.entity.PurchaseItem;
import com.jack.model.PurchaseItemTableModel;
import com.jack.view.MainFrame;

public class UpdatePurchaseItemAddDialog extends JDialog
{
  private static final long serialVersionUID = 7859453711941554643L;
  private JComboBox cbx_prod;
  private DefaultComboBoxModel prod_model;
  private JTextField tfd_num;
  private JTextField tfd_price;
  private NumberFormat nf = new DecimalFormat("￥#,##0.00元");

  private ProductDao prodDao = new ProductDao();
  private static PurchaseItemTableModel model;
  private static UpdatePurchaseItemAddDialog dialog = new UpdatePurchaseItemAddDialog();

  public static UpdatePurchaseItemAddDialog getInstance(PurchaseItemTableModel model) {
	 model = model;
    return dialog; }

  private UpdatePurchaseItemAddDialog() {
    super(MainFrame.getInstance(), "新增采购明细", true);

    setSize(320, 240);
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

  public void createContentPanel(JPanel cp) {
    JPanel centerPanel = new JPanel(null);
    centerPanel.setBackground(Color.WHITE);
    cp.add(centerPanel, "Center");

    JLabel cateLbl = new JLabel("采购商品名");
    cateLbl.setBounds(30, 20, 80, 22);
    centerPanel.add(cateLbl);

    this.cbx_prod = new JComboBox();
    this.cbx_prod.setBounds(110, 20, 160, 22);
    this.cbx_prod.requestFocusInWindow();
    centerPanel.add(this.cbx_prod);

    JLabel isbnLbl = new JLabel("采购数量");
    isbnLbl.setBounds(30, 55, 80, 22);
    centerPanel.add(isbnLbl);

    this.tfd_num = new JTextField();
    this.tfd_num.setBounds(110, 55, 160, 22);
    centerPanel.add(this.tfd_num);
    this.tfd_num.setText("1");

    JLabel priceLbl = new JLabel("采购价格");
    priceLbl.setBounds(30, 90, 80, 22);
    centerPanel.add(priceLbl);

    this.tfd_price = new JTextField();
    this.tfd_price.setBounds(110, 90, 160, 22);
    centerPanel.add(this.tfd_price);

    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e) {
        List<Product> list = prodDao.findAll();
        		prod_model = new DefaultComboBoxModel(new Vector(list));
        		cbx_prod.setModel(prod_model);

        if (cbx_prod.getItemCount() >= 0) {
          cbx_prod.setSelectedIndex(0);

          Product prod = (Product)cbx_prod.getSelectedItem();

          tfd_price.setText(nf.format(prod.getSale_price().doubleValue()));
        }

        cbx_prod.addItemListener(new ItemListener()
        {
          public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == 1) {
              Product prod = (Product)cbx_prod.getSelectedItem();
              tfd_price.setText(nf.format(prod.getSale_price().doubleValue()));
            }
          }
        });
      }
    });
  }

  public void createBtnPanel(JPanel cp)
  {
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new FlowLayout(2, 12, 15));
    cp.add(btnPanel, "South");

    JButton jbt2 = new JButton("重置");
    jbt2.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        if (cbx_prod.getItemCount() >= 0) {
          cbx_prod.setSelectedIndex(0);
        }
        tfd_num.setText("1");
      }
    });
    btnPanel.add(jbt2);

    JButton jbt = new JButton("新增");
    jbt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e) {
        doAdd();
      }
    });
    btnPanel.add(jbt);
  }

  private void doAdd() {
    Product prod = (Product)cbx_prod.getSelectedItem();
    int num = Integer.parseInt(tfd_num.getText());

    BigDecimal price = null;
    try {
      Number n = nf.parse(tfd_price.getText());
      price = new BigDecimal(n.doubleValue());
    } catch (ParseException e) {
      e.printStackTrace();
    }

    PurchaseItem info = new PurchaseItem();
    info.setNum(num);
    info.setPrice(price);
    info.setProduct_id(prod.getId());
    info.setProdName(prod.getName());
    info.setProdModel(prod.getModel());
    info.setProdUnit(prod.getUnit());
    	System.out.println(info);
    		UpdatePurchaseDialog.model.add(info);
    setVisible(false);
  }
}