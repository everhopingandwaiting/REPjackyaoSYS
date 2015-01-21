package com.jack.view.sales;

import java.awt.Font;
import java.awt.GridLayout;
import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.jack.dao.SalesItemDao;

public class SalesChartFrame extends JInternalFrame
{
  private static final long serialVersionUID = 8239782841697993722L;
  private SalesItemDao dao = new SalesItemDao();

  private static SalesChartFrame frame = new SalesChartFrame();

  public static SalesChartFrame getInstance() { return frame; }

  private SalesChartFrame()
  {
    setFrameIcon(null);
    setTitle("出库商品分类统计图表");
    setSize(550, 350);

    setResizable(true);
    setIconifiable(true);
    setMaximizable(true);
    setClosable(true);
    setDefaultCloseOperation(2);

    createChartTheme();

    createChartPanel();

    setVisible(true);
  }

  private void createChartPanel() {
    final JPanel contentPanel = new JPanel();
    add(contentPanel, "Center");
    contentPanel.setLayout(new GridLayout(1, 2));

    addInternalFrameListener(new InternalFrameAdapter()
    {
      public void internalFrameActivated(InternalFrameEvent e)
      {
        Map map = SalesChartFrame.this.dao.totalByCategory();
        System.out.println(map);

        contentPanel.removeAll();
        contentPanel.add(SalesChartFrame.this.createPie(map));
        contentPanel.add(SalesChartFrame.this.createBar(map));
      }
    });
  }

  private void createChartTheme()
  {
    StandardChartTheme standardChartTheme = new StandardChartTheme("CN");

    standardChartTheme.setExtraLargeFont(new Font("隶书", 1, 16));

    standardChartTheme.setRegularFont(new Font("宋书", 0, 12));

    standardChartTheme.setLargeFont(new Font("宋书", 0, 12));

    ChartFactory.setChartTheme(standardChartTheme);
  }

  private ChartPanel createPie(Map<String, Integer> map) {
    DefaultPieDataset dpd = new DefaultPieDataset();

    for (Map.Entry entry : map.entrySet()) {
      dpd.setValue((Comparable)entry.getKey(), (Number)entry.getValue());
    }

    JFreeChart chart = ChartFactory.createPieChart("出库商品按分类统计图", dpd, true, true, false);

    return new ChartPanel(chart);
  }

  private ChartPanel createBar(Map<String, Integer> map) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Map.Entry entry : map.entrySet()) {
      dataset.setValue((Number)entry.getValue(), (Comparable)entry.getKey(), (Comparable)entry.getKey());
    }

    JFreeChart chart = ChartFactory.createBarChart("出库商品按分类统计图", "分类名", 
      "数量", dataset, PlotOrientation.VERTICAL, true, true, false);

    return new ChartPanel(chart);
  }
}