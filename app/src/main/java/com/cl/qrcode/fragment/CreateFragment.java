package com.cl.qrcode.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.cl.qrcode.R;
import com.cl.qrcode.activity.CreateActivity;
import com.cl.qrcode.activity.MainActivity;
import com.cl.qrcode.adapter.CreateInfoAdapter;
import com.cl.qrcode.provider.CreateInfo;
import com.cl.qrcode.provider.Item;

import java.util.ArrayList;
import java.util.List;

import static com.cl.qrcode.provider.Settings.TYPE_CAL;
import static com.cl.qrcode.provider.Settings.TYPE_CONTACT;
import static com.cl.qrcode.provider.Settings.TYPE_EMAIL;
import static com.cl.qrcode.provider.Settings.TYPE_GIFT;
import static com.cl.qrcode.provider.Settings.TYPE_HISTORY;
import static com.cl.qrcode.provider.Settings.TYPE_LOCATION;
import static com.cl.qrcode.provider.Settings.TYPE_SMS;
import static com.cl.qrcode.provider.Settings.TYPE_PRODUCT;
import static com.cl.qrcode.provider.Settings.TYPE_TEL;
import static com.cl.qrcode.provider.Settings.TYPE_TEXT;
import static com.cl.qrcode.provider.Settings.TYPE_WEB;
import static com.cl.qrcode.provider.Settings.TYPE_WIFI;
import static com.cl.qrcode.provider.Settings.getCreateInfo;

public class CreateFragment extends Fragment implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private List<CreateInfo> createInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create, container, false);
        mGridView = (GridView) view.findViewById(R.id.gridView);
        initData();
        bindData();
        return view;
    }

    private void bindData() {
        CreateInfoAdapter adapter = new CreateInfoAdapter(createInfoList,getActivity());
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);
    }

    private void initData() {
        List<List<Item>> lists = new ArrayList<>();
        createInfoList = new ArrayList<>();
        createInfoList.add(getCreateInfo(TYPE_HISTORY));
        createInfoList.add(getCreateInfo(TYPE_WEB));
        createInfoList.add(getCreateInfo(TYPE_EMAIL));
        createInfoList.add(getCreateInfo(TYPE_WIFI));
        createInfoList.add(getCreateInfo(TYPE_CONTACT));
        createInfoList.add(getCreateInfo(TYPE_TEXT));
        createInfoList.add(getCreateInfo(TYPE_TEL));
        createInfoList.add(getCreateInfo(TYPE_SMS));
        createInfoList.add(getCreateInfo(TYPE_LOCATION));
        createInfoList.add(getCreateInfo(TYPE_PRODUCT));
        createInfoList.add(getCreateInfo(TYPE_CAL));
        createInfoList.add(getCreateInfo(TYPE_GIFT));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        ((MainActivity) getActivity()).playRing();
        if(position == 0){
            Toast.makeText(getActivity(),"create history",Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(getActivity(), CreateActivity.class);
        CreateInfo createInfo = createInfoList.get(position);
        intent.putExtra("icon", createInfo.getIcon());
        intent.putExtra("title", createInfo.getTitle());
        intent.putExtra("type", createInfo.getType());
        intent.putParcelableArrayListExtra("items", createInfo.getItems());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.abc_fade_in,R.anim.abc_fade_out);
    }
}
