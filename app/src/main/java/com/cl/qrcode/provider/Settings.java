package com.cl.qrcode.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.InputType;

import com.cl.qrcode.R;
import com.cl.qrcode.activity.BaseApplication;

import java.util.ArrayList;
import java.util.List;

public class Settings {

    public static final int TYPE_HISTORY = 0x01;
    public static final int TYPE_WEB = 0x02;
    public static final int TYPE_EMAIL = 0x03;
    public static final int TYPE_WIFI = 0x04;
    public static final int TYPE_CONTACT = 0x05;
    public static final int TYPE_TEXT = 0x06;
    public static final int TYPE_TEL = 0x07;
    public static final int TYPE_SMS = 0x08;
    public static final int TYPE_LOCATION = 0x09;
    public static final int TYPE_PRODUCT = 0x0A;
    public static final int TYPE_CAL = 0x0B;
    public static final int TYPE_GIFT = 0x0C;
    public static final int TYPE_ISBN = 0x0D;
    public static final int[] CREATE_TYPE = new int[]{TYPE_HISTORY, TYPE_WEB, TYPE_EMAIL, TYPE_WIFI, TYPE_CONTACT, TYPE_TEXT, TYPE_TEL, TYPE_SMS, TYPE_LOCATION, TYPE_PRODUCT, TYPE_CAL, TYPE_GIFT};
    public static final int[] ICON_ARRAY = new int[]{R.mipmap.ic_create, R.mipmap.ic_web, R.mipmap.ic_email, R.mipmap.ic_wifi, R.mipmap.ic_contact, R.mipmap.ic_text, R.mipmap.ic_icon_bigtel, R.mipmap.ic_message, R.mipmap.ic_location, R.mipmap.ic_product, R.mipmap.ic_cal, R.mipmap.ic_gift};
    public static final String KEY_RING = "software_ring";
    public static final String KEY_SCAN_VIBRATE = "scan_vibrate";
    public static final String WIFI_TYPE_WPA = "WPA";

    public static final String WIFI_TYPE_WEP = "WEP";
    public static final String WIFI_TYPE_NO = "nopass";

    public static final String DATA_FORMAT_WIFI = "WIFI:T:%s;S:%s;P:%s;;";
    public static final String DATA_FORMAT_GEO = "geo:%s,%s";
    public static final String DATA_FORMAT_SMS = "smsto:%s:%s";
    public static final String DATA_FORMAT_TEL = "tel:%s";
    public static final String DATA_FORMAT_EMAIL = "MATMSG:TO:%s;SUB:%s;BODY:%s;;";


    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(key, true);
    }

    public static int getWifiEncryptionType(String value) {
        int wifiType = 0;
        if (WIFI_TYPE_WEP.equalsIgnoreCase(value)) {
            wifiType = 1;
        } else if (WIFI_TYPE_WPA.equalsIgnoreCase(value)) {
            wifiType = 2;
        }
        return wifiType;
    }

    public static CreateInfo getCreateInfo(int type) {
        int value = -1;
        CreateInfo createInfo = null;
        for (int i = 0; i < CREATE_TYPE.length; i++) {
            if (type == CREATE_TYPE[i]) {
                value = i;
            }
        }
        if (value != -1) {

            String[] titles = getStringArray(R.array.titles);
            createInfo = new CreateInfo();
            createInfo.setIcon(ICON_ARRAY[value]);
            createInfo.setTitle(titles[value]);
            createInfo.setType(type);
            createInfo.addItems(getItems(type));
        }
        return createInfo;
    }

    private static String[] getStringArray(int resId) {
        return BaseApplication.getApp().getResources().getStringArray(resId);
    }

    public static List<Item> getItems(int type) {

        List<Item> items = new ArrayList<>();
        switch (type) {
            case TYPE_WEB:
                items.add(new Item(R.mipmap.ic_url_smallicon, getString(R.string.url_hint), InputType.TYPE_TEXT_VARIATION_URI));
                break;
            case TYPE_EMAIL:
                items.add(new Item(R.mipmap.ic_email_addressee, getString(R.string.email_addressee_hint), InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS));
                items.add(new Item(R.mipmap.ic_email_title, getString(R.string.email_title_hint), InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT));
                items.add(new Item(R.mipmap.ic_email_body, getString(R.string.email_content_hint)));
                break;
            case TYPE_WIFI:
                items.add(new Item(R.mipmap.ic_wifi_name, getString(R.string.wifi_ssid_hint)));
                Item wifiType = new Item(R.mipmap.ic_wifi_encryption, getString(R.string.wifi_encrypt_hint));
                wifiType.setValue(Settings.WIFI_TYPE_WPA);
                items.add(wifiType);
                items.add(new Item(R.mipmap.ic_wifi_password, getString(R.string.wifi_password_hint), InputType.TYPE_TEXT_VARIATION_PASSWORD));
                break;
            case TYPE_CONTACT:
                items.add(new Item(R.mipmap.ic_contacts_name, getString(R.string.contact_name)));
                items.add(new Item(R.mipmap.ic_contacts_company, getString(R.string.contact_company)));
                items.add(new Item(R.mipmap.ic_contacts_phone, getString(R.string.contact_phone), InputType.TYPE_CLASS_PHONE));
                items.add(new Item(R.mipmap.ic_contacts_email, getString(R.string.contact_email), InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS));
                items.add(new Item(R.mipmap.ic_contacts_address, getString(R.string.contact_address)));
                items.add(new Item(R.mipmap.ic_contacts_url, getString(R.string.contact_url), InputType.TYPE_TEXT_VARIATION_URI));
                items.add(new Item(R.mipmap.ic_contacts_memo, getString(R.string.contact_memo)));
                break;
            case TYPE_TEXT:
                items.add(new Item(R.mipmap.ic_text_icon, getString(R.string.text_hint)));
                break;
            case TYPE_TEL:
                items.add(new Item(R.mipmap.ic_icon_tel, getString(R.string.tel_number), InputType.TYPE_CLASS_PHONE));
                break;
            case TYPE_SMS:
                items.add(new Item(R.mipmap.ic_create_sms_number, getString(R.string.sms_number), InputType.TYPE_CLASS_PHONE));
                items.add(new Item(R.mipmap.ic_create_sms_content, getString(R.string.sms_content)));
                break;
            case TYPE_LOCATION:
                items.add(new Item(R.mipmap.ic_create_location_lat, getString(R.string.location_lat), InputType.TYPE_NUMBER_FLAG_DECIMAL));
                items.add(new Item(R.mipmap.ic_create_location_long, getString(R.string.location_long), InputType.TYPE_NUMBER_FLAG_DECIMAL));
                break;
            case TYPE_PRODUCT:
                items.add(new Item(R.mipmap.ic_icon_cart, getString(R.string.cart_message)));
                break;
            case TYPE_CAL:
                items.add(new Item(R.mipmap.ic_calendar_activity, getString(R.string.calendar_activity)));
                items.add(new Item(R.mipmap.ic_calendar_start, getString(R.string.calendar_start), InputType.TYPE_DATETIME_VARIATION_TIME));
                items.add(new Item(R.mipmap.ic_calendar_end, getString(R.string.calendar_end), InputType.TYPE_DATETIME_VARIATION_TIME));
                items.add(new Item(R.mipmap.ic_calendar_place, getString(R.string.calendar_place)));
                items.add(new Item(R.mipmap.ic_calendar_memo, getString(R.string.calendar_memo)));
                break;
        }
        return items;
    }

    private static String getString(int resId) {
        return BaseApplication.getApp().getString(resId);
    }
}
