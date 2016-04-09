package com.cl.qrcode.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cl.qrcode.CreateFragment;
import com.cl.qrcode.HistoryFragment;
import com.cl.qrcode.MyViewPagerAdapter;
import com.cl.qrcode.R;
import com.cl.qrcode.ScanFragment;
import com.cl.qrcode.SettingsFragment;
import com.cl.qrcode.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    ActionBar mActionBar;
    ViewPager mViewPager;
    MyViewPagerAdapter mAdapter;
    List<Fragment> mFragmentList = new ArrayList<>();
    RadioGroup mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(R.color.red);
            tintManager.setStatusBarTintResource(R.color.colorAccent);
        }
        setContentView(R.layout.activity_main);
        initActionbar();
        initView();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initActionbar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {

        }
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabs = (RadioGroup) findViewById(R.id.tabs);
        mFragmentList.add(new ScanFragment());
        mFragmentList.add(new CreateFragment());
        mFragmentList.add(new HistoryFragment());
        mFragmentList.add(new SettingsFragment());
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mFragmentList);
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
        for (int i = 0; i < group.getChildCount(); i++) {
            RadioButton child = (RadioButton) mTabs.getChildAt(i);
            if (checkedId == child.getId()) {
                mViewPager.setCurrentItem(i, true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            ((ScanFragment) mFragmentList.get(0)).printResult(scanResult);
        }
    }
}
