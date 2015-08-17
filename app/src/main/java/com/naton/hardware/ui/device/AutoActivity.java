package com.naton.hardware.ui.device;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.result.ProgramResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Device;
import com.naton.hardware.model.Program;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AutoActivity extends BaseActivity {

    private String deviceId;
    private ArrayList<Program> programList = new ArrayList<Program>();

    private PullToRefreshListView programListView;
    private ProgramAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        deviceId = getIntent().getStringExtra("deviceId");
        programListView = (PullToRefreshListView)findViewById(R.id.programListView);

        setTitleText("程序列表");

        fetchProgramList();

        // 下拉刷新 及 加载更多
        programListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                fetchProgramList();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });
    }

    private void fetchProgramList(){
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.listProgram(new DeviceRequest("",deviceId),new Callback<ProgramResult>() {
            @Override
            public void success(ProgramResult result, Response response) {
                if (result.code==1){
                    programList = result.result_data;
                    adapter = new ProgramAdapter(AutoActivity.this,deviceId,programList);
                    programListView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
