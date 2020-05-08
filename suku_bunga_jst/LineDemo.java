/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suku_bunga_jst;

/**
 *
 * @author AM
 */

import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class LineDemo extends ApplicationFrame{

    public LineDemo(String title) {
        super(title);
        ChartPanel chartPanel = (ChartPanel) createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);
    }
    
    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                            "Grafik MSE per Epoch",  // Judul
            "Epoch",             // x
            "MSE",          // y
            dataset,            // data
            true,               // legend
            true,               // gtooltips
            false               // URLs
        );
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
                XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
        }
        NumberAxis axis = new NumberAxis("Epoch");
        axis.setAutoRangeIncludesZero(true);
        plot.setDomainAxis(axis);
        return chart;
    }
    
        private static XYDataset createDataset() {
        XYSeries s1 = new XYSeries("MSE per Epoch");
        for (int i = 0; i < 120; i++){
            s1.add(i+1,core_jst.mse[i]);
        }
                
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(s1);
                
        return dataset;
                
    }
        
    public static JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        return new ChartPanel(chart);
    }
    
}
