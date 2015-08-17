package com.naton.hardware.ui.device;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.naton.hardware.BaseActivity;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.DeviceControlRequest;
import com.naton.hardware.http.request.DeviceRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.result.DeviceStatusResult;
import com.naton.hardware.http.service.DeviceService;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ManualActivity extends BaseActivity {

    private String deviceId;

    private RelativeLayout rl_switch_water;
    private ImageView iv_switch_open_water;
    private ImageView iv_switch_close_water;

    private RelativeLayout rl_switch_heat;
    private ImageView iv_switch_open_heat;
    private ImageView iv_switch_close_heat;

    private RelativeLayout rl_switch_light;
    private ImageView iv_switch_open_light;
    private ImageView iv_switch_close_light;

    private RelativeLayout rl_switch_mode;
    private ImageView iv_switch_open_mode;
    private ImageView iv_switch_close_mode;

    private RelativeLayout rl_switch_fan;
    private ImageView iv_switch_open_fan;
    private ImageView iv_switch_close_fan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        setTitleText("手动控制");

        deviceId = getIntent().getStringExtra("deviceId");

        rl_switch_water = (RelativeLayout) findViewById(R.id.rl_private);
        iv_switch_open_water = (ImageView) findViewById(R.id.iv_switch_open_private);
        iv_switch_close_water = (ImageView) findViewById(R.id.iv_switch_close_private);

        rl_switch_heat = (RelativeLayout) findViewById(R.id.rl_switch_heat);
        iv_switch_open_heat = (ImageView) findViewById(R.id.iv_switch_open_heat);
        iv_switch_close_heat = (ImageView) findViewById(R.id.iv_switch_close_heat);

        rl_switch_light = (RelativeLayout) findViewById(R.id.rl_switch_light);
        iv_switch_open_light = (ImageView) findViewById(R.id.iv_switch_open_light);
        iv_switch_close_light = (ImageView) findViewById(R.id.iv_switch_close_light);

        rl_switch_mode = (RelativeLayout) findViewById(R.id.rl_switch_mode);
        iv_switch_open_mode = (ImageView) findViewById(R.id.iv_switch_open_mode);
        iv_switch_close_mode = (ImageView) findViewById(R.id.iv_switch_close_mode);

        rl_switch_fan = (RelativeLayout) findViewById(R.id.rl_switch_fan);
        iv_switch_open_fan = (ImageView) findViewById(R.id.iv_switch_open_fan);
        iv_switch_close_fan = (ImageView) findViewById(R.id.iv_switch_close_fan);

        rl_switch_water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_water.getVisibility() == View.VISIBLE) {
                    iv_switch_open_water.setVisibility(View.INVISIBLE);
                    iv_switch_close_water.setVisibility(View.VISIBLE);
                    changeDeviceStatus(1, 0);
                } else {
                    iv_switch_open_water.setVisibility(View.VISIBLE);
                    iv_switch_close_water.setVisibility(View.INVISIBLE);
                    changeDeviceStatus(1, 1);
                }
            }
        });

        rl_switch_heat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_heat.getVisibility() == View.VISIBLE) {
                    iv_switch_open_heat.setVisibility(View.INVISIBLE);
                    iv_switch_close_heat.setVisibility(View.VISIBLE);
                    changeDeviceStatus(2, 0);
                } else {
                    iv_switch_open_heat.setVisibility(View.VISIBLE);
                    iv_switch_close_heat.setVisibility(View.INVISIBLE);
                    changeDeviceStatus(2, 1);
                }
            }
        });

        rl_switch_light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_light.getVisibility() == View.VISIBLE) {
                    iv_switch_open_light.setVisibility(View.INVISIBLE);
                    iv_switch_close_light.setVisibility(View.VISIBLE);
                    changeDeviceStatus(3, 0);
                } else {
                    iv_switch_open_light.setVisibility(View.VISIBLE);
                    iv_switch_close_light.setVisibility(View.INVISIBLE);
                    changeDeviceStatus(3, 1);
                }
            }
        });

        rl_switch_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_mode.getVisibility() == View.VISIBLE) {
                    iv_switch_open_mode.setVisibility(View.INVISIBLE);
                    iv_switch_close_mode.setVisibility(View.VISIBLE);
                    changeDeviceStatus(4, 0);
                } else {
                    iv_switch_open_mode.setVisibility(View.VISIBLE);
                    iv_switch_close_mode.setVisibility(View.INVISIBLE);
                    changeDeviceStatus(4, 1);
                }
            }
        });

        rl_switch_fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iv_switch_open_fan.getVisibility() == View.VISIBLE) {
                    iv_switch_open_fan.setVisibility(View.INVISIBLE);
                    iv_switch_close_fan.setVisibility(View.VISIBLE);
                    changeDeviceStatus(5, 0);
                } else {
                    iv_switch_open_fan.setVisibility(View.VISIBLE);
                    iv_switch_close_fan.setVisibility(View.INVISIBLE);
                    changeDeviceStatus(5, 1);
                }
            }
        });

        getDeviceStatus();
    }

    private void getDeviceStatus() {
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.getDeviceStatus(new DeviceRequest("", deviceId), new Callback<DeviceStatusResult>() {
            @Override
            public void success(DeviceStatusResult result, Response response) {
                if (result.code == 1) {
                    setWaterStatus(result.result_data.getStatus1());
                    setHeatStatus(result.result_data.getStatus2());
                    setLightStatus(result.result_data.getStatus3());
                    setModeStatus(result.result_data.getStatus4());
                    setFanStatus(result.result_data.getStatus5());
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    /**
     * @param cmd    (1:喷雾，2：加热：3：灯光：4：手自动：5：风扇)
     * @param stauts
     */
    private void changeDeviceStatus(int cmd, int stauts) {
        SVProgressHUD.showInView(this, "正在切换，请稍后...", false);
        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        deviceService.changeDeviceStatus(new DeviceControlRequest(deviceId, cmd, stauts), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                SVProgressHUD.dismiss(ManualActivity.this);
                if (result.code == 1) {
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.showInViewWithoutIndicator(ManualActivity.this, "切换失败", 2.0f);
            }
        });
    }

    private void setWaterStatus(int status) {
        if (status == 1) {
            iv_switch_open_water.setVisibility(View.VISIBLE);
            iv_switch_close_water.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_water.setVisibility(View.INVISIBLE);
            iv_switch_close_water.setVisibility(View.VISIBLE);
        }

    }

    private void setHeatStatus(int status) {
        if (status == 1) {
            iv_switch_open_heat.setVisibility(View.VISIBLE);
            iv_switch_close_heat.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_heat.setVisibility(View.INVISIBLE);
            iv_switch_close_heat.setVisibility(View.VISIBLE);
        }
    }

    private void setLightStatus(int status) {
        if (status == 1) {
            iv_switch_open_light.setVisibility(View.VISIBLE);
            iv_switch_close_light.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_light.setVisibility(View.INVISIBLE);
            iv_switch_close_light.setVisibility(View.VISIBLE);
        }
    }

    private void setModeStatus(int status) {
        if (status == 1) {
            iv_switch_open_mode.setVisibility(View.VISIBLE);
            iv_switch_close_mode.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_mode.setVisibility(View.INVISIBLE);
            iv_switch_close_mode.setVisibility(View.VISIBLE);
        }
    }

    private void setFanStatus(int status) {
        if (status == 1) {
            iv_switch_open_fan.setVisibility(View.VISIBLE);
            iv_switch_close_fan.setVisibility(View.INVISIBLE);
        } else {
            iv_switch_open_fan.setVisibility(View.INVISIBLE);
            iv_switch_close_fan.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manual, menu);
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
