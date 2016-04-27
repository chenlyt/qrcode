package com.cl.qrcode.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cl.qrcode.R;
import com.cl.qrcode.activity.CreateActivity;
import com.cl.qrcode.provider.Item;
import com.cl.qrcode.provider.Settings;

import java.util.ArrayList;
import java.util.List;

public class CreateItemAdapter extends BaseAdapter implements View.OnClickListener {
    private List<Item> mItems;
    private Context mContext;
    private int mType;

    public CreateItemAdapter(CreateActivity context, ArrayList<Item> items, int type) {
        mItems = items;
        mContext = context;
        mType = type;
    }

    @Override
    public int getCount() {
        return mItems != null && mItems.size() > 0 ? mItems.size() : 0;
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Item item = mItems.get(position);
        View view = null;
        if (position == 1 && mType == Settings.TYPE_WIFI) {
            view = LayoutInflater.from(mContext).inflate(R.layout.create_list_item_spinner, null);
            Spinner spinner = (Spinner) view.findViewById(R.id.item_spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item);
            String[] split = item.getTitle().split(";");
            adapter.addAll(split);
            spinner.setAdapter(adapter);
            spinner.setSelection(Settings.getWifiEncryptionType(item.getValue()));
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            item.setValue(Settings.WIFI_TYPE_WPA);
                            break;
                        case 1:
                            item.setValue(Settings.WIFI_TYPE_WEP);
                            break;
                        case 2:
                            item.setValue(Settings.WIFI_TYPE_NO);
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            final ViewHolder viewHolder;
            view = LayoutInflater.from(mContext).inflate(R.layout.create_list_item, null);
            viewHolder = new ViewHolder(view);
            Drawable drawable = mContext.getResources().getDrawable(item.getIcon());
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                viewHolder.edit.setCompoundDrawables(drawable, null, null, null);
            }
            viewHolder.edit.setHint(item.getTitle());
            viewHolder.edit.setOnClickListener(this);
            viewHolder.edit.setText(item.getValue());

            viewHolder.edit.setInputType(item.getInputType());

            EditTextListener listener = new EditTextListener(item);
            viewHolder.edit.addTextChangedListener(listener);
        }
        return view;

    }

    @Override
    public void onClick(View v) {

        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
    }

    private class ViewHolder {
        private EditText edit;

        public ViewHolder(View view) {
            edit = (android.widget.EditText) view.findViewById(R.id.create_item_edit);
        }
    }

    private class EditTextListener implements TextWatcher {

        private Item item;

        public EditTextListener(Item item) {

            this.item = item;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            String value = s.toString();
            if (TextUtils.isEmpty(value)) {
                value = null;
            }
            Log.i("chenlong", "value： " + value);
            item.setValue(value);
        }
    }
}
