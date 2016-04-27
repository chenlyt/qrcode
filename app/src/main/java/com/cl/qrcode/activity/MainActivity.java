package com.cl.qrcode.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cl.qrcode.R;
import com.cl.qrcode.adapter.MyViewPagerAdapter;
import com.cl.qrcode.fragment.CreateFragment;
import com.cl.qrcode.fragment.HistoryFragment;
import com.cl.qrcode.fragment.ScanFragment;
import com.cl.qrcode.fragment.SettingsFragment;
import com.cl.qrcode.provider.Settings;
import com.cl.qrcode.utils.BeepManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    ViewPager mViewPager;
    MyViewPagerAdapter mAdapter;
    List<Fragment> mFragmentList = new ArrayList<>();
    RadioGroup mTabs;
    private BeepManager beepManager;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beepManager = new BeepManager(this, R.raw.tab);
        initView();
    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabs = (RadioGroup) findViewById(R.id.tabs);
        mFragmentList.add(new ScanFragment());
        mFragmentList.add(new CreateFragment());
        mFragmentList.add(new HistoryFragment());
        mFragmentList.add(new SettingsFragment());
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mTabs.setOnCheckedChangeListener(this);
        ((RadioButton) mTabs.getChildAt(0)).setChecked(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) mTabs.getChildAt(position)).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (!isFirst) {
            playRing();
        }
        isFirst = false;
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton child = (RadioButton) mTabs.getChildAt(i);
            if (checkedId == child.getId()) {
                mViewPager.setCurrentItem(i, true);
                if (mActionBar != null) {
                    mActionBar.setTitle(child.getContentDescription());
                }
                child.setTextColor(Color.GREEN);
            }
            child.setTextColor(Color.WHITE);
        }
    }

    public void playRing() {
        if (Settings.getBoolean(this, Settings.KEY_RING)) {
            beepManager.playBeepSoundAndVibrate(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        beepManager.close();
    }
}
