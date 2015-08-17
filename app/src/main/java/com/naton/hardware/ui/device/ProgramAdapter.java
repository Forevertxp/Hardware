package com.naton.hardware.ui.device;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.naton.hardware.R;
import com.naton.hardware.http.manager.RestManager;
import com.naton.hardware.http.request.ProgramRequest;
import com.naton.hardware.http.result.CommonResult;
import com.naton.hardware.http.service.DeviceService;
import com.naton.hardware.model.Device;
import com.naton.hardware.model.Program;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-17.
 */
public class ProgramAdapter extends BaseAdapter {


    private Context context;
    private String deviceId;
    private List<Program> programList;

    public ProgramAdapter(Context context, String deviceId, ArrayList<Program> programList) {
        super();
        this.deviceId = deviceId;
        this.context = context;
        this.programList = programList;
    }

    @Override
    public int getCount() {
        return getProgramCount();
    }

    @Override
    public Object getItem(int index) {
        return getProgram(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_program, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView.findViewById(R.id.programName);
            holder.btn_bind = (Button) convertView.findViewById(R.id.bindBtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Program program = programList.get(position);
        holder.tv_title.setText("设备名称： " + program.getProgramName());
        if (program.getBind() == 1) {
            holder.btn_bind.setText("已绑定");
        } else {
            holder.btn_bind.setText("绑定");
        }
        holder.btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (program.getBind() == 0)
                    bind(position);
            }
        });
        return convertView;
    }


    private int getProgramCount() {
        return programList.size();
    }

    private Program getProgram(int index) {
        if (programList != null) {
            return programList.get(index);
        } else {
            return null;
        }
    }

    private void bind(final int position) {

        DeviceService deviceService = RestManager.getInstance().create(DeviceService.class);
        final Program program = programList.get(position);
        deviceService.bindProgram(new ProgramRequest(deviceId, program.getProgramId()), new Callback<CommonResult>() {
            @Override
            public void success(CommonResult result, Response response) {
                if (result.code == 1) {
                    for (int i = 0; i < programList.size(); i++) {
                        Program p = programList.get(i);
                        if (p.getBind() == 1) {
                            p.setBind(0);
                            programList.set(i, p);
                        }
                    }
                    program.setBind(1);
                    programList.set(position, program);
                    notifyDataSetChanged();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private void cancelBind() {

    }

    class ViewHolder {
        private TextView tv_title;
        private Button btn_bind;
    }

}

