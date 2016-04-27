package com.cl.qrcode.decode;

import android.os.Handler;
import android.os.Looper;

import com.cl.qrcode.activity.CaptureActivity;
import com.google.zxing.DecodeHintType;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class DecodeThread extends Thread {

    public static final String BARCODE_BITMAP = "barcode_bitmap";


    private final CaptureActivity activity;
    private final Map<DecodeHintType, Object> hints;
    private final CountDownLatch handlerInitLatch;
    private Handler handler;

    public DecodeThread(CaptureActivity activity, int decodeMode) {

        this.activity = activity;
        handlerInitLatch = new CountDownLatch(1);
        hints = DecodeUtils.getHints(decodeMode);
    }

    public Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(activity, hints);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
