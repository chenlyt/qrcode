
package com.cl.qrcode.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cl.qrcode.R;
import com.cl.qrcode.adapter.CreateItemAdapter;
import com.cl.qrcode.encoding.EncodingUtils;
import com.cl.qrcode.fragment.CreateResultFragment;
import com.cl.qrcode.provider.Item;
import com.cl.qrcode.utils.BeepManager;

import java.util.ArrayList;

import static com.cl.qrcode.provider.Settings.*;

public class CreateActivity extends BaseActivity {
    private int mIcon;
    private String mTitle;
    private int mType;
    private ArrayList<Item> mItems;

    private View mContainer;
    private ImageView mIconView;
    private TextView mTitleView;
    private View mListViewHeader;
    private ListView mListView;
    private Button mOperate;
    private CreateItemAdapter mAdapter;
    private BeepManager beepManager;

    private LinearLayout mRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        beepManager = new BeepManager(this, R.raw.create);
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        initData();
        initView();
    }

    private void initData() {

        Intent intent = getIntent();
        mItems = intent.getParcelableArrayListExtra("items");
        mIcon = intent.getIntExtra("icon", R.mipmap.ic_launcher);
        mTitle = intent.getStringExtra("title");
        mType = intent.getIntExtra("type", 0);
        setTitle(mTitle);
    }

    private void initView() {
        mRootView = (LinearLayout) findViewById(R.id.create_layout);
        mContainer = findViewById(R.id.create_fragment);
        mListViewHeader = getLayoutInflater().inflate(R.layout.create_list_header, null);
        mIconView = (ImageView) mListViewHeader.findViewById(R.id.create_icon);
        mTitleView = (TextView) mListViewHeader.findViewById(R.id.create_title);
        mListView = (ListView) findViewById(R.id.create_listView);
        mOperate = (Button) findViewById(R.id.create_operate);
    }
}