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
 * ������
 * @author solo
 */
public class MainFrame extends JFrame {
	private static final long serialVersionUID = -3969235297453054371L;
	
	private Timer timer = new Timer();
	private DateFormat df = new SimpleDateFormat(" ��ǰʱ�䣺yyyy��MM��dd�� EEE HH:mm:ss ");
	private BufferedImage bg = null; //����ͼƬ
	
	private JDesktopPane desktop;
	private JLabel lbl_name;
	private JLabel lbl_info;
	
	//��ǰ��¼�Ĺ���Ա����
	public static Manager curr_mgr = null;
	
	
	//����ģʽ
	private static MainFrame frame = new MainFrame();
	public static MainFrame getInstance(Manager mgr){
		MainFrame.curr_mgr = mgr;
		frame.lbl_name.setText(" ��ǰ��¼�û���" + curr_mgr.getLname() + " ");
		return frame;
	}
	
	public static MainFrame getInstance(){
		return frame;
	}
	
	private MainFrame(){ //˽�еĹ��췽��
		
		this.setIconImage(new ImageIcon("images/afe.jpg").getImage());
		this.setTitle("����ϵͳ");
		this.setSize(900, 700);
		//���õ���Ļ������λ�ó���
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - this.getWidth()) / 2, (d.height - this.getHeight())/2);
				
		initNavigationPanel();
		
		initMainPanel();
		
		initStatePanel();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	/**
	 * ��ʼ�������
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
	 * ��ʼ��������ǩ���
	 */
	private void initNavigationPanel() {
		final JTabbedPane tabbedPane = new JTabbedPane();
		
		///////////1. �ɹ�����
		JPanel purc = new JPanel();
		purc.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("�ɹ�����", purc);

		purc.add(createBtn("images/������.png", PurchaseFrame.getInstance()));
		purc.add(createBtn("images/����ѯ.png", StoragePurchaseFrame.getInstance()));
		purc.add(createBtn("images/ͳ�ƻ���.png", PurchaseChartFrame.getInstance()));
		
		///////////2. ���۹���
		JPanel sale = new JPanel();
		sale.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("���۹���", sale);
         
		sale.add(createBtn("images/���۵�.png", SalesFrame.getInstance() ));
		sale.add(createBtn("images/������Ϣ��ѯ.png",  OutboundSalesFrame.getInstance()));
		sale.add(createBtn("images/ͳ�ƻ���.png", SalesChartFrame.getInstance()));
		
		///////////3. ������Ϣ����
		JPanel base = new JPanel();
		base.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("������Ϣ����", base);

		base.add(createBtn("images/��Ʒ�������.png", CategoryFrame.getInstance()));
		base.add(createBtn("images/��Ʒ��Ϣ����.png", ProductFrame.getInstance()));
		base.add(createBtn("images/��Ӧ����Ϣ����.png", SupplierFrame.getInstance()));
		base.add(createBtn("images/�ͻ���Ϣ����.png", CustomerFrame.getInstance()));
		
		
		///////////4. ϵͳ����
		JPanel sys = new JPanel();
		sys.setLayout(new FlowLayout(FlowLayout.LEFT));
		tabbedPane.addTab("ϵͳ����", sys);
		
		sys.add(createBtn("images/����Ա����.png", ManagerFrame.getInstance()));
		sys.add(createBtn("images/��������.png", UpdatePWD.getInstance()));
		sys.add(createBtn("images/��������.png", AboutUsFrame.getInstance()));
		
		JButton btn2 = new JButton();
		btn2.setFocusPainted(false);
		btn2.setBorderPainted(false);
		btn2.setContentAreaFilled(false);
		btn2.setIcon(new ImageIcon("images/�˳�ϵͳ.png"));
		btn2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//�����Ի���
				int result = JOptionPane.showConfirmDialog(MainFrame.this, 
						"�����˳�ϵͳ���Ƿ�ȷ����", 
						"ȷ���˳�", 
						JOptionPane.OK_CANCEL_OPTION);
				
				if(result == JOptionPane.OK_OPTION){
					MainFrame.this.dispose(); 
					System.exit(0); //������˳�
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
	 * ����ͼƬ·����Ҫ�򿪵�Ŀ�괰�ڣ����������������ť��
	 * @param imgfile ��ť�ı���ͼƬ
	 * @param target ��������ťʱҪ�򿪵���Ƕ�����ʵ��
	 * @param removerOther �Ƿ��Ƴ���ǰ�򿪵���Ƕ����
	 * @return 
	 */
	private JButton createBtn(String imgfile, final JInternalFrame target, final boolean removerOther){
		JButton btn = new JButton(new AbstractAction() {
			private static final long serialVersionUID = 1894186024482693616L;
			public void actionPerformed(ActionEvent e) {
				if(target == null){
					return;
				}
				
				//��ǰ�ڲ�������δ��ӵ�DesktopPane��ʱ�����
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
					target.setMaximum(true);//���
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setContentAreaFilled(false);
		btn.setIcon(new ImageIcon(imgfile)); //ͼƬ��ť
		
		return btn;
	}
	
	/**
	 * ��ʼ��״̬��壺������ʾ��¼�û���ʱ��
	 */
	private void initStatePanel(){
		JPanel statePanel = new JPanel();
		statePanel.setBorder(BorderFactory.createEtchedBorder()); //���ñ߿�
		statePanel.setLayout(new BorderLayout());
		this.add(statePanel, BorderLayout.SOUTH);
		
		//��ǰ��¼�û�
		lbl_name = new JLabel();
		statePanel.add(lbl_name, BorderLayout.WEST);
		//lbl_name.setText("  ��ǰ��¼�û���" + realname + "  ");
		lbl_name.setBorder(BorderFactory.createLoweredBevelBorder());
		
		//��ʾ��̬ʱ��ı�ǩ
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
						"�����˳�ϵͳ���Ƿ�ȷ����", 
						"ȷ���˳�", 
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
		
		//�м���ಿ��
		lbl_info = new JLabel();
		lbl_info.setBorder(BorderFactory.createLoweredBevelBorder());
		lbl_info.setText("  Welcome!! ");
		statePanel.add(lbl_info, BorderLayout.CENTER);
	}
}