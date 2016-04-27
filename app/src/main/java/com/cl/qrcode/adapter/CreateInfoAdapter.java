package com.cl.qrcode.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cl.qrcode.R;
import com.cl.qrcode.provider.CreateInfo;

import java.util.List;

public class CreateInfoAdapter extends BaseAdapter {
    private List<CreateInfo> createInfoList;
    private Context mContext;

    public CreateInfoAdapter(List<CreateInfo> createInfoList, Context mContext) {
        this.createInfoList = createInfoList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return createInfoList != null && createInfoList.size() > 0 ? createInfoList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return createInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CreateInfo createInfo = createInfoList.get(position);
        viewHolder.icon.setImageResource(createInfo.getIcon());
        viewHolder.title.setText(createInfo.getTitle());
        return viewHolder.view;
    }


    private class ViewHolder {
        private ImageView icon;
        private TextView title;
        private View view;
        public ViewHolder(View view) {
            this.view = view;
            icon = (ImageView) view.findViewById(R.id.item_icon);
            title = (TextView) view.findViewById(R.id.item_title);
        }
    }
}
