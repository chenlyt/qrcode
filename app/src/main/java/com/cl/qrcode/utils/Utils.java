package com.cl.qrcode.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import com.cl.qrcode.decode.DecodeUtils;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static Result decodeBitmap(Bitmap data){

        MultiFormatReader multiFormatReader = new MultiFormatReader();

        multiFormatReader.setHints(DecodeUtils.getHints(DecodeUtils.ALL_MODE));
        Result rawResult = null;
        try {
            rawResult = multiFormatReader
                    .decodeWithState(new BinaryBitmap(new HybridBinarizer(
                            new BitmapLuminanceSource(data))));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return rawResult;
    }

    public static boolean checkNetWorkStatus(Context context) {
        boolean result;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            result = true;
        } else {
            result = false;
            printResult(context, "����δ����");
        }
        return result;
    }

    public static boolean isValidURL(String value) {
        Pattern pattern = Pattern.compile("(.*://)?([\\w-]+\\.)+[\\w-]+(:\\d+)?(/[^/.]*)*(/[^/]+\\.[^/\\?]+)(\\?&*([^&=]+=[^&=]*)&*(&[^&=]+=[^&=]*)*&*)");
        Matcher m = pattern.matcher(value);
        if (m.matches())
            return true;

        return false;
    }

    public static boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            int code = conn.getResponseCode();
            value = code == 200;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void printResult(Context context, String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }


    public static Intent getCropIntent(Uri uri,int aspectX,int aspectY, int outputX,int outputY) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);

        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        return intent;
    }

}
