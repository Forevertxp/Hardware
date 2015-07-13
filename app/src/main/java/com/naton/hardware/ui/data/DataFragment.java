package com.naton.hardware.ui.data;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.AuthManager;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceDataRequest;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.result.DataResult;
import com.naton.hardware.http.result.DeviceInfoResult;
import com.naton.hardware.http.service.DataService;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Data;
import com.naton.hardware.model.DeviceInfo;
import com.naton.hardware.ui.data.chart.SingleLineChart;
import com.naton.hardware.ui.device.activity.ImagePagerActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;

import org.achartengine.GraphicalView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link android.app.Fragment} subclass.
 */
public class DataFragment extends Fragment {


    private static final String KEY_CONTENT = "Fragment1:Content";
    private Bundle bundle;

    private static final String ARG = "deviceID";

    private PullToRefreshScrollView scrollView;

    private GraphicalView temperatureChart;
    private GraphicalView humidityChart;
    private GraphicalView sunshineChart;

    private TextView deviceName,temperature,humidity,sunshine;
    private RoundedImageView deviceImage;
    private LinearLayout temperatureLL;
    private LinearLayout humidityLL;
    private LinearLayout sunshineLL;

    private ArrayList<Data> dataList = new ArrayList<Data>();

    public static Fragment newInstance(String arg) {
        DataFragment fragment = new DataFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG, arg);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            bundle = savedInstanceState.getBundle(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_data, container, false);

        scrollView = (PullToRefreshScrollView)v.findViewById(R.id.scroll);
        scrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (AuthManager.getInstance().isAuthenticated()) {
                    fetchDeviceInfo( getArguments().getString(ARG));
                    fetchDeviceData(getArguments().getString(ARG));
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

            }
        });

        deviceName = (TextView) v.findViewById(R.id.deviceName);
        deviceName.setText("设备:\n" + getArguments().getString(ARG));
        temperature = (TextView) v.findViewById(R.id.temperature);
        humidity = (TextView) v.findViewById(R.id.humidity);
        sunshine = (TextView) v.findViewById(R.id.sunshine);

        deviceImage = (RoundedImageView)v.findViewById(R.id.deviceImage);

        temperatureLL = (LinearLayout) v.findViewById(R.id.linechart1);
        humidityLL = (LinearLayout) v.findViewById(R.id.linechart2);
        sunshineLL = (LinearLayout) v.findViewById(R.id.linechart3);

        if (AuthManager.getInstance().isAuthenticated()) {
            fetchDeviceInfo( getArguments().getString(ARG));
            fetchDeviceData(getArguments().getString(ARG));
        }
        return v;
    }

    private void fetchDeviceInfo(String deviceId) {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        String userId = AuthManager.getInstance().getAccessToken().getUserId();
        deviceService.fetchDeviceInfo(new DeviceRequest(userId, deviceId), new Callback<DeviceInfoResult>() {
            @Override
            public void success(DeviceInfoResult result, Response response) {
                if (result.code == 1) {
                    final DeviceInfo deviceInfo = result.result_data;
                    if (!TextUtils.isEmpty(deviceInfo.getImage())){
                        temperature.setText("温度：\n"+deviceInfo.getTemperate());
                        humidity.setText("湿度:\n" + deviceInfo.getHumedity());
                        sunshine.setText("光照:\n"+deviceInfo.getLightStatue());
                        Picasso.with(getActivity()).load(deviceInfo.getImage()).into(deviceImage);
                        deviceImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                                ArrayList<String> imageUrls = new ArrayList<String>();
                                imageUrls.add(deviceInfo.getImage());
                                intent.putStringArrayListExtra("image_urls", imageUrls);
                                intent.putExtra("image_index",0);
                                startActivity(intent);
                            }
                        });
                    }
                }
                scrollView.onRefreshComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                scrollView.onRefreshComplete();
            }
        });
    }

    public void fetchDeviceData(String deviceId) {
        DataService dataService = RestManager.getInstance().create(DataService.class);

        Calendar calendar = Calendar.getInstance();
        // 默认显示一周内数据
        String dateEnd = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
                .getTime()); // 当前日期
        calendar.add(Calendar.MONTH, -1); // 一个月前日期
        String dateBegin = new SimpleDateFormat("yyyyMMddHHmmss").format(calendar
                .getTime());

        dataService.fetchDeviceData(new DeviceDataRequest(deviceId, dateBegin, dateEnd), new Callback<DataResult>() {
            @Override
            public void success(DataResult result, Response response) {
                if (result.code == 1) {
                    dataList = result.result_data;
                    if (dataList.size() > 0) {
                        Date[] dateArray = new Date[dataList.size()];
                        double[] temperatureArray = new double[dataList.size()];
                        double[] humidityArray = new double[dataList.size()];
                        double[] sunshineArray = new double[dataList.size()];
                        // 将服务端返回的yyyyMMddHHmmss格式的字符串时间转换为Date
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                        for (int i = 0; i < dataList.size(); i++) {
                            Data data = dataList.get(i);
                            try {
                                dateArray[i] = sdf.parse(data.getDatetime());
                                temperatureArray[i] = Double.parseDouble(data.getTemperate());
                                humidityArray[i] = Double.parseDouble(data.getHumedity());
                                sunshineArray[i] = Double.parseDouble(data.getLightStatue());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                        if (dateArray.length == 0) {
                            return;
                        }
                        Calendar calendar = Calendar.getInstance();
                        SingleLineChart singleLineChart = new SingleLineChart();
                        temperatureChart = singleLineChart.drawChart(getActivity(),
                                dateArray, temperatureArray, new SimpleDateFormat("yyyy-MM-dd").format(calendar
                                        .getTime()));
                        temperatureLL.addView(temperatureChart, new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT));
                        humidityChart = singleLineChart.drawChart(getActivity(),
                                dateArray, temperatureArray, new SimpleDateFormat("yyyy-MM-dd").format(calendar
                                        .getTime()));
                        humidityLL.addView(humidityChart, new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT));
                        sunshineChart = singleLineChart.drawChart(getActivity(),
                                dateArray, humidityArray, new SimpleDateFormat("yyyy-MM-dd").format(calendar
                                        .getTime()));
                        sunshineLL.addView(sunshineChart, new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.MATCH_PARENT,
                                RelativeLayout.LayoutParams.MATCH_PARENT));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle(KEY_CONTENT, bundle);
    }
}



