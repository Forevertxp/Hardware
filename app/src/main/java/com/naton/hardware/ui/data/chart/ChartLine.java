/**
 * 
 */
package com.naton.hardware.ui.data.chart;

import android.app.Activity;

/**
 * @author Administrator
 * 
 */
public class ChartLine extends Activity
{

	//SurfaceView sfv;

	//SurfaceHolder sfh;

	int Y_axis[] = new int[getWindowManager().getDefaultDisplay().getWidth()];

	int[] X_axis = new int[getWindowManager().getDefaultDisplay().getWidth()];

	//int centerY = (getWindowManager().getDefaultDisplay().getHeight() - sfv.getTop()) / 2;// 中心线

	int oldX, oldY;// 上一个XY点

	int currentX;// 当前绘制到的X轴上的点

}
