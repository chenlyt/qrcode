package com.cl.qrcode;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create, container, false);
        /*
        *
                        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 350, 350,
                                mCheckBox.isChecked() ?
                                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher) :
                                        null);
                        qrImgImageView.setImageBitmap(qrCodeBitmap);
        * */
    }
}
