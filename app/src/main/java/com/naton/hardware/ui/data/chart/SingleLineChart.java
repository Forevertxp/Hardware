package com.naton.hardware.ui.data.chart;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.text.TextUtils;

public class SingleLineChart {

    private GraphicalView mChartView;
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private String dateStr;
    private double minY, maxY = 0.0;

    public GraphicalView drawChart(Context context, Date[] xdata,
                                   double[] ydata, String dateStr) {
        this.dateStr = dateStr;
        this.maxY = getMax(ydata);
        this.minY = getMin(ydata);
        mChartView = ChartFactory.getTimeChartView(context,
                getDataset(xdata, ydata), getRenderer(), "M/d");
        return mChartView;
    }

    /**
     * 得到住渲染器，并对其各项属性进行设置
     *
     * @return
     */
    public XYMultipleSeriesRenderer getRenderer() {
        // 设置背景色是否启
        mRenderer.setApplyBackgroundColor(true);
        // 设置背景
        mRenderer.setBackgroundColor(Color.argb(0, 92, 147, 238));
        mRenderer.setMargins(new int[]{5, 20, 5, 5});
        mRenderer.setMarginsColor(Color.argb(0, 65, 105, 225));
        // 设置是否显示网格
        mRenderer.setShowGrid(true);
        // 设置是否显示 底部legend
        mRenderer.setShowLegend(false);
        mRenderer.setYLabelsAlign(Align.RIGHT);
        // 设置图表上显示点的大小
        mRenderer.setPointSize(5);
        // 设置默认X轴默认的坐标
        Date date1 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!TextUtils.isEmpty(dateStr))
                date1 = sdf.parse(dateStr);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE, 2);// 两天后
        Long time1 = calendar.getTime().getTime();// Long类型变量
        calendar.add(Calendar.MONTH, -1); // 一个月前日期
        Long time2 = calendar.getTime().getTime();
        mRenderer.setXAxisMax(time1);
        mRenderer.setXAxisMin(time2);
        mRenderer.setXLabels(30);
        //设置Y轴最大和最小值
        mRenderer.setYAxisMax(maxY * 1.05);
        mRenderer.setYAxisMin(minY * 0.95);
        // // 设置x y轴标题字体大
        // mRenderer.setAxisTitleTextSize(16);
        // // 设置表格标题字体大小
        // mRenderer.setChartTitleTextSize(20);
        // // 设置标签字体大小
        // mRenderer.setLabelsTextSize(15);
        // // 设置图例字体大小
        // mRenderer.setLegendTextSize(15);
        // // 设置是否显示放大缩小按钮
        // mRenderer.setZoomButtonsVisible(true);
        // 设置是否允许缩放
        mRenderer.setZoomEnabled(false,false);
        // 设置是否允许滑动
        mRenderer.setPanEnabled(false,false);

        // create a new renderer for the new series
        XYSeriesRenderer renderer = new XYSeriesRenderer();
        // set some renderer properties
        renderer.setPointStyle(PointStyle.CIRCLE);
        renderer.setFillPoints(true);
        renderer.setDisplayChartValues(false);
        // renderer.setDisplayChartValuesDistance(100);
        renderer.setColor(Color.WHITE);
        renderer.setLineWidth(3);
        mRenderer.addSeriesRenderer(renderer);
        return mRenderer;
    }

    /**
     * 得到住渲染器，并对其各项属性进行设置
     *
     * @return
     */
    public XYMultipleSeriesDataset getDataset(Date[] xdata, double[] ydata) {
        String seriesTitle = "健康数据";
        TimeSeries mSeries = new TimeSeries(seriesTitle);
        for (int i = 0; i < xdata.length; i++) {
            Date x = xdata[i];
            double y = ydata[i];
            BigDecimal bd = new BigDecimal(y);
            // 保留两位小数
            double ny = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            // 把坐标添加到当前序列中去
            mSeries.add(x, ny);
        }
        mDataset.addSeries(mSeries);
        return mDataset;
    }

    private double getMin(double[] ydata) {
        double min = ydata[0];
        for (int i = 0; i < ydata.length; i++) {
            if (min > ydata[i])
                min = ydata[i];
        }
        return min;
    }

    private double getMax(double[] ydata) {
        double max = ydata[0];
        for (int i = 0; i < ydata.length; i++) {
            if (max < ydata[i])
                max = ydata[i];
        }
        return max;
    }
}
