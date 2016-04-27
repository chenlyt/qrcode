package com.cl.qrcode.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

public class BeepManager implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, Closeable {

    private static final String TAG = BeepManager.class.getSimpleName();

    private static final float BEEP_VOLUME = 0.10f;
    private static final long VIBRATE_DURATION = 200L;

    private final Activity activity;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private int rawId;

    public BeepManager(Activity activity, int rawId) {
        this.activity = activity;
        this.mediaPlayer = null;
        this.rawId = rawId;
        updatePrefs();
    }

    private static boolean shouldBeep(Context activity) {
        AudioManager audioService = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
        return audioService.getRingerMode() == AudioManager.RINGER_MODE_NORMAL;
    }

    private synchronized void updatePrefs() {
        playBeep = shouldBeep(activity);
        if (playBeep && mediaPlayer == null) {
            activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = buildMediaPlayer(activity, rawId);
        }
    }

    public synchronized void playBeepSoundAndVibrate(boolean vibrate) {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    private MediaPlayer buildMediaPlayer(Context activity, int rawId) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        try {
            AssetFileDescriptor file = activity.getResources().openRawResourceFd(rawId);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
            } finally {
                file.close();
            }
            mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
            mediaPlayer.prepare();
            return mediaPlayer;
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            mediaPlayer.release();
            return null;
        }
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
        mediaPlayer = buildMediaPlayer(activity, rawId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.seekTo(0);
    }

    @Override
    public synchronized boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            activity.finish();
        } else {
            mp.release();
            mediaPlayer = null;
            updatePrefs();
        }
        return true;
    }

    @Override
    public synchronized void close() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}
