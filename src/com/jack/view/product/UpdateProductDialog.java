package com.jack.view.product;

import com.jack.dao.CategoryDao;
import com.jack.dao.ProductDao;
import com.jack.entity.Category;
import com.jack.entity.Product;
import com.jack.view.MainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.BigDecimal;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UpdateProductDialog
  extends JDialog
{
  private static final long serialVersionUID = -3019483616331100043L;
  private JComboBox<Category> cbx_cate;
  private JTextField tfd_isbn;
  private JTextField tfd_name;
  private JTextField tfd_model;
  private JTextField tfd_unit;
  private JTextField tfd_price;
  private JTextField tfd_security;
  private JTextArea ta_summary;
  private DefaultComboBoxModel<Category> model;
  private CategoryDao cateDao = new CategoryDao();
  private Product curr_prod;
  
  public UpdateProductDialog(JFrame owner, Product prod)
  {
    super(owner, "更新商品信息", true);
    
    this.curr_prod = prod;
    
    setSize(520, 400);
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
  
  public void createTitlePanel(JPanel cp)
  {
    JPanel panel = new JPanel(new FlowLayout(0, 12, 15));
    
    cp.add(panel, "North");
  }
  
  public void createContentPanel(JPanel cp)
  {
    JPanel centerPanel = new JPanel(null);
    centerPanel.setBackground(Color.WHITE);
    cp.add(centerPanel, "Center");
    

    JLabel cateLbl = new JLabel("所属分类");
    cateLbl.setBounds(30, 20, 80, 22);
    centerPanel.add(cateLbl);
    
    this.cbx_cate = new JComboBox<Category>();
    this.cbx_cate.setBounds(110, 20, 140, 22);
    this.cbx_cate.requestFocusInWindow();
    centerPanel.add(this.cbx_cate);
    
    JLabel isbnLbl = new JLabel("ISBN编码");
    isbnLbl.setBounds(270, 20, 60, 22);
    centerPanel.add(isbnLbl);
    
    this.tfd_isbn = new JTextField();
    this.tfd_isbn.setBounds(330, 20, 140, 22);
    centerPanel.add(this.tfd_isbn);
    

    JLabel nameLbl = new JLabel("商品名");
    nameLbl.setBounds(30, 55, 80, 22);
    centerPanel.add(nameLbl);
    
    this.tfd_name = new JTextField();
    this.tfd_name.setBounds(110, 55, 140, 22);
    centerPanel.add(this.tfd_name);
    

    JLabel modelLbl = new JLabel("规格");
    modelLbl.setBounds(270, 55, 60, 22);
    centerPanel.add(modelLbl);
    
    this.tfd_model = new JTextField();
    this.tfd_model.setBounds(330, 55, 140, 22);
    centerPanel.add(this.tfd_model);
    

    JLabel lbl_unit = new JLabel("单位");
    lbl_unit.setBounds(30, 95, 80, 22);
    centerPanel.add(lbl_unit);
    
    this.tfd_unit = new JTextField();
    this.tfd_unit.setBounds(110, 95, 140, 22);
    centerPanel.add(this.tfd_unit);
    
    JLabel lbl_price = new JLabel("销售单价");
    lbl_price.setBounds(270, 95, 80, 22);
    centerPanel.add(lbl_price);
    
    this.tfd_price = new JTextField();
    this.tfd_price.setBounds(330, 95, 140, 22);
    centerPanel.add(this.tfd_price);
    

    JLabel lbl_secuirty = new JLabel("安全存量");
    lbl_secuirty.setBounds(30, 135, 80, 22);
    centerPanel.add(lbl_secuirty);
    
    this.tfd_security = new JTextField();
    this.tfd_security.setBounds(110, 135, 140, 22);
    centerPanel.add(this.tfd_security);
    


    JLabel lbl_remark = new JLabel("备注");
    lbl_remark.setBounds(30, 175, 80, 22);
    centerPanel.add(lbl_remark);
    
    this.ta_summary = new JTextArea();
    JScrollPane pane = new JScrollPane(this.ta_summary, 
      22, 
      30);
    pane.setBounds(110, 175, 360, 88);
    centerPanel.add(pane);
    
    addComponentListener(new ComponentAdapter()
    {
      public void componentShown(ComponentEvent e)
      {
        UpdateProductDialog.this.model = new DefaultComboBoxModel<Category>(new Vector<Category>(UpdateProductDialog.this.cateDao.findAll()));
        UpdateProductDialog.this.cbx_cate.setModel(UpdateProductDialog.this.model);
        for (int i = 0; i < UpdateProductDialog.this.model.getSize(); i++)
        {
          Category c = (Category)UpdateProductDialog.this.model.getElementAt(i);
          if (UpdateProductDialog.this.curr_prod.getCate_id() == c.getId())
          {
            UpdateProductDialog.this.cbx_cate.setSelectedIndex(i);
            break;
          }
        }
        UpdateProductDialog.this.tfd_isbn.setText(UpdateProductDialog.this.curr_prod.getIsbn());
        UpdateProductDialog.this.tfd_name.setText(UpdateProductDialog.this.curr_prod.getName());
        UpdateProductDialog.this.tfd_model.setText(UpdateProductDialog.this.curr_prod.getModel());
        UpdateProductDialog.this.tfd_unit.setText(UpdateProductDialog.this.curr_prod.getUnit());
        UpdateProductDialog.this.tfd_price.setText(String.valueOf(UpdateProductDialog.this.curr_prod.getSale_price().doubleValue()));
        UpdateProductDialog.this.tfd_security.setText(String.valueOf(UpdateProductDialog.this.curr_prod.getSecurity_num()));
        UpdateProductDialog.this.ta_summary.setText(UpdateProductDialog.this.curr_prod.getSummary());
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
      public void actionPerformed(ActionEvent e)
      {
        for (int i = 0; i < UpdateProductDialog.this.model.getSize(); i++)
        {
          Category c = (Category)UpdateProductDialog.this.model.getElementAt(i);
          if (UpdateProductDialog.this.curr_prod.getCate_id() == c.getId())
          {
            UpdateProductDialog.this.cbx_cate.setSelectedIndex(i);
            break;
          }
        }
        UpdateProductDialog.this.tfd_isbn.setText(UpdateProductDialog.this.curr_prod.getIsbn());
        UpdateProductDialog.this.tfd_name.setText(UpdateProductDialog.this.curr_prod.getName());
        UpdateProductDialog.this.tfd_model.setText(UpdateProductDialog.this.curr_prod.getModel());
        UpdateProductDialog.this.tfd_unit.setText(UpdateProductDialog.this.curr_prod.getUnit());
        UpdateProductDialog.this.tfd_price.setText(String.valueOf(UpdateProductDialog.this.curr_prod.getSale_price().doubleValue()));
        UpdateProductDialog.this.tfd_security.setText(String.valueOf(UpdateProductDialog.this.curr_prod.getSecurity_num()));
        UpdateProductDialog.this.ta_summary.setText(UpdateProductDialog.this.curr_prod.getSummary());
      }
    });
    btnPanel.add(jbt2);
    
    JButton jbt = new JButton("提交");
    jbt.addActionListener(new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        UpdateProductDialog.this.doUpdate();
      }
    });
    btnPanel.add(jbt);
  }
  
  private void doUpdate()
  {
    Category c = (Category)this.cbx_cate.getSelectedItem();
    this.curr_prod.setCate_id(c.getId());
    
    this.curr_prod.setIsbn(this.tfd_isbn.getText());
    this.curr_prod.setName(this.tfd_name.getText());
    this.curr_prod.setModel(this.tfd_model.getText());
    this.curr_prod.setUnit(this.tfd_unit.getText());
    this.curr_prod.setSale_price(new BigDecimal(this.tfd_price.getText()));
    this.curr_prod.setSecurity_num(Integer.parseInt(this.tfd_security.getText()));
    this.curr_prod.setSummary(this.ta_summary.getText());
    
    ProductDao dao = new ProductDao();
    dao.update(this.curr_prod);
    

    ProductFrame.getInstance().model.setData(dao.findAll());
    dispose();
  }
}
