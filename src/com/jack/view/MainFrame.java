package com.jack.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import com.jack.entity.Manager;
import com.jack.view.category.CategoryFrame;
import com.jack.view.customer.CustomerFrame;
import com.jack.view.manager.ManagerFrame;
import com.jack.view.manager.UpdatePWD;
import com.jack.view.product.ProductFrame;
import com.jack.view.purchase.PurchaseChartFrame;
import com.jack.view.purchase.PurchaseFrame;
import com.jack.view.purchase.StoragePurchaseFrame;
import com.jack.view.sales.OutboundSalesFrame;
import com.jack.view.sales.SalesChartFrame;
import com.jack.view.sales.SalesFrame;
import com.jack.view.supplier.SupplierFrame;

/**
 * 主界面
 * @author solo
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -3969235297453054371L;
	
	private Timer timer = new Timer();
	private DateFormat df = new SimpleDateFormat(" 当前时间：yyyy年MM月dd日 EEE HH:mm:ss ");
	private BufferedImage bg = null; //背景图片
	
	private JDesktopPane desktop;
	private JLabel lbl_name;
	private JLabel lbl_info;
	
	//当前登录的管理员对象
	public static Manager curr_mgr = null;
	
	
	//单例模式
	private static MainFrame frame = new MainFrame();
	public static MainFrame getInstance(Manager mgr){
		MainFrame.curr_mgr = mgr;
		frame.lbl_name.setText(" 当前登录用户：" + curr_mgr.getLname() + " ");
		return frame;
	}
	
	public static MainFrame getInstance(){
		return frame;
	}
	
	private MainFrame(){ //私有的构造方法
		
		this.setIconImage(new ImageIcon("images/afe.jpg").getImage());
		this.setTitle("管理系统");
		this.setSize(900, 700);
		//设置到屏幕的中央位置出现
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getWidth()) / 2, (d.height - this.getHeight())/2);
				
		initNavigationPanel();
		
		initMainPanel();
		
		initStatePanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * 初始化主面板
	 */
	private void initMainPanel(){
		try {  
			bg = ImageIO.read(new FileInputStream("images/beijing.jpg"));  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		desktop = new JDesktopPane() {
			private static final long serialVersionUID = 1L;
			protected void paintChildren(java.awt.Graphics g) {
				if (bg != null) {
					g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
				}
				super.paintChildren(g);
			};
		};
		
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		
		desktop.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.add(desktop, BorderLayout.CENTER);
	}
	
	/**
	 * 初始化导航标签面板
	 */
	private void initNavigationPanel() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		
		///////////1. 采购管理
		JPanel purc = new JPanel();
		purc.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("采购管理", purc);

		purc.add(createBtn("images/进货单.png", PurchaseFrame.getInstance()));
		purc.add(createBtn("images/入库查询.png", StoragePurchaseFrame.getInstance()));
		purc.add(createBtn("images/统计汇总.png", PurchaseChartFrame.getInstance()));
		
		///////////2. 销售管理
		JPanel sale = new JPanel();
		sale.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("销售管理", sale);
         
		sale.add(createBtn("images/销售单.png", SalesFrame.getInstance() ));
		sale.add(createBtn("images/销售信息查询.png",  OutboundSalesFrame.getInstance()));
		sale.add(createBtn("images/统计汇总.png", SalesChartFrame.getInstance()));
		
		///////////3. 基础信息管理
		JPanel base = new JPanel();
		base.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("基础信息管理", base);

		base.add(createBtn("images/商品分类管理.png", CategoryFrame.getInstance()));
		base.add(createBtn("images/商品信息管理.png", ProductFrame.getInstance()));
		base.add(createBtn("images/供应商信息管理.png", SupplierFrame.getInstance()));
		base.add(createBtn("images/客户信息管理.png", CustomerFrame.getInstance()));
		
		
		///////////4. 系统设置
		JPanel sys = new JPanel();
		sys.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("系统设置", sys);
		
		sys.add(createBtn("images/操作员管理.png", ManagerFrame.getInstance()));
		sys.add(createBtn("images/更改密码.png", UpdatePWD.getInstance()));
		sys.add(createBtn("images/关于我们.png", AboutUsFrame.getInstance()));
		
		JButton btn2 = new JButton();
		btn2.setFocusPainted(false);
		btn2.setBorderPainted(false);
		btn2.setContentAreaFilled(false);
		btn2.setIcon(new ImageIcon("images/退出系统.png"));
		btn2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//弹出对话框
				int result = JOptionPane.showConfirmDialog(MainFrame.this, 
						"即将退出系统，是否确定？", 
						"确认退出", 
						JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION){
					MainFrame.this.dispose(); 
					System.exit(0); //虚拟机退出
				}
			}
		});
		sys.add(btn2);
		
		this.add(tabbedPane, BorderLayout.NORTH);
	}
	
	
	private JButton createBtn(String imgfile, final JInternalFrame target){
		return createBtn(imgfile, target, true);
	}
	
	/**
	 * 根据图片路径和要打开的目标窗口，创建出这个操作按钮。
	 * @param imgfile 按钮的背景图片
	 * @param target 点击这个按钮时要打开的内嵌窗体的实例
	 * @param removerOther 是否移除先前打开的内嵌窗体
	 * @return 
	 */
	private JButton createBtn(String imgfile, final JInternalFrame target, final boolean removerOther){
		JButton btn = new JButton(new AbstractAction() {
			private static final long serialVersionUID = 1894186024482693616L;
			public void actionPerformed(ActionEvent e) {
				if(target == null){
					return;
				}
				
				//当前内部窗体中未添加到DesktopPane中时才添加
				if (target.getDesktopPane() == null) {
					desktop.add(target);
				}
				
				if(removerOther){
					JInternalFrame[] f = desktop.getAllFrames();
					int length = f == null ? 0 : f.length;
					for (int i = 0; i < length; i++) {
						if(f[i].getClass() == target.getClass()){
							continue;
						}
						desktop.remove(f[i]);
					}
				}
				
				try {
					target.setSelected(true);
					target.setVisible(true);
					target.setMaximum(true);//最大化
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setIcon(new ImageIcon(imgfile)); //图片按钮
		
		return btn;
	}
	
	/**
	 * 初始化状态面板：用于显示登录用户和时间
	 */
	private void initStatePanel(){
		JPanel statePanel = new JPanel();
		statePanel.setBorder(BorderFactory.createEtchedBorder()); //设置边框
		statePanel.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		
		//当前登录用户
		lbl_name = new JLabel();
		statePanel.add(lbl_name, BorderLayout.WEST);
		//lbl_name.setText("  当前登录用户：" + realname + "  ");
		lbl_name.setBorder(BorderFactory.createLoweredBevelBorder());
		
		//显示动态时间的标签
		final JLabel lbl_time = new JLabel();
		lbl_time.setHorizontalAlignment(SwingConstants.RIGHT);
		statePanel.add(lbl_time, BorderLayout.EAST);
		lbl_time.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				timer.scheduleAtFixedRate(new TimerTask() {
					@Override
					public void run() {
						lbl_time.setText(df.format(new Date()));
					}
				}, 0, 1000);
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(MainFrame.this, 
						"即将退出系统，是否确定？", 
						"确认退出", 
						JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION){
					MainFrame.this.dispose(); 
					System.exit(0);
				}else{
                   MainFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);  
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				timer.cancel();
			}
		});
		
		//中间空余部分
		lbl_info = new JLabel();
		lbl_info.setBorder(BorderFactory.createLoweredBevelBorder());
		lbl_info.setText("  Welcome!! ");
		statePanel.add(lbl_info, BorderLayout.CENTER);
	}
}