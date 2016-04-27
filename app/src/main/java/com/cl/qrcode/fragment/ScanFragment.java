package com.cl.qrcode.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cl.qrcode.R;
import com.cl.qrcode.activity.CaptureActivity;
import com.cl.qrcode.utils.Utils;
import com.google.zxing.Result;


public class ScanFragment extends Fragment implements View.OnClickListener {

    private static final int SCAN_REQUEST = 100;
    private static final int PHOTO_REQUEST_GALLERY = 101;
    private static final int PHOTO_REQUEST_CUT = 102;
    private ImageView img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.photo_select).setOnClickListener(this);
        view.findViewById(R.id.scan).setOnClickListener(this);
        img = (ImageView) view.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap data = ((BitmapDrawable) img.getDrawable()).getBitmap();
                Result rawResult = Utils.decodeBitmap(data);
                if (rawResult != null) {
                    Utils.printResult(getActivity(), rawResult.getText());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                openCameraIntent.putExtra("title", getActivity().getTitle());
                startActivityForResult(openCameraIntent, SCAN_REQUEST);
                getActivity().overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                break;
            case R.id.photo_select:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    Uri uri = data.getData();
                    startActivityForResult(Utils.getCropIntent(uri, 1, 1, 500, 500), PHOTO_REQUEST_CUT);
                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    img.setImageBitmap(bitmap);
                }
            case SCAN_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        String scanResult = bundle.getString("result");
                        Utils.printResult(getActivity(), scanResult);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
