package com.cl.qrcode.provider;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;

public class Item extends BaseData implements Parcelable {
    private String value = null;
    private int inputType;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item(int icon, String title) {
        this.icon = icon;
        this.title = title;
        this.inputType = InputType.TYPE_CLASS_TEXT;
    }

    public Item(int icon, String title, int inputType) {
        this.icon = icon;
        this.title = title;
        this.inputType = inputType;
    }

    public Item(int icon, String title, String value) {
        this.icon = icon;
        this.title = title;
        this.value = value;
    }

    protected Item(Parcel in) {
        icon = in.readInt();
        title = in.readString();
        value = in.readString();
        inputType = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(icon);
        dest.writeString(title);
        dest.writeString(value);
        dest.writeInt(inputType);
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }
}