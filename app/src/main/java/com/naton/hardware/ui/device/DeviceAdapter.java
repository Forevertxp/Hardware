package com.naton.hardware.ui.device;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.RoundedImageView;
import com.naton.hardware.R;
import com.naton.hardware.model.Device;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianxiaopeng on 15-1-17.
 */
public class DeviceAdapter extends BaseAdapter {


    private Context context;
    private List<Device> deviceList;

    int[] imgs = {R.drawable.ic_device1,R.drawable.ic_device2,R.drawable.ic_device3,R.drawable.ic_device4};

    public DeviceAdapter(Context context, ArrayList<Device> deviceList) {
        super();
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public int getCount() {
        return getDeviceCount();
    }

    @Override
    public Object getItem(int index) {
        return getInformation(index);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_device, null);
            holder = new ViewHolder();
            holder.iv_avatar = (RoundedImageView) convertView.findViewById(R.id.iv_avatar);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Device device = deviceList.get(position);
        holder.tv_title.setText("设备名称： " + device.getDeviceName());
        holder.tv_content.setText("设备编号： " + device.getDeviceId());

        int i = (int)(Math.random()*4);
//        String imageUrl = "http://pic.nipic.com/2007-11-09/2007119122519868_2.jpg";
//        Picasso.with(context)
//                .load(imageUrl)
//                .placeholder(R.drawable.ic_avatar_default)
//                .error(R.drawable.ic_avatar_default)
//                .into(holder.iv_avatar);
        holder.iv_avatar.setImageResource(imgs[i]);
        return convertView;
    }


    private int getDeviceCount() {
        return deviceList.size();
    }

    private Device getInformation(int index) {
        if (deviceList != null) {
            return deviceList.get(index);
        } else {
            return null;
        }
    }

    class ViewHolder {
        private RoundedImageView iv_avatar;
        private TextView tv_title;
        private TextView tv_content;
    }

}

