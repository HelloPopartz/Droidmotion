package com.tactil.lpro.muv_config;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;

import com.tactil.lpro.muv_config.data.CommonData;
import com.tactil.lpro.muv_config.data.Constants;
import com.tactil.lpro.muv_config.data.res.ButtonInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrdomint on 4/03/15.
 */
public class MuvAdapter extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        CommonData appState = (CommonData) getApplicationContext();
        ButtonInfo b = appState.getButton(keyCode);

        //If we have information about the button b won't we null
        if (b != null)
            executeAction(b.getActionCode(), b.getAppPackage(), b.getActionData());
        else
            return super.onKeyDown(keyCode, event);

        //Fix
        return true;
    }

    private void executeAction(short action, String appPackage, String actionData) {
        Intent i;
        Uri fileUri;
        switch (action) {
            case Constants.OPEN_APP:
                PackageManager manager = getPackageManager();
                i = manager.getLaunchIntentForPackage(appPackage);
                i.addCategory(Intent.CATEGORY_LAUNCHER);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case Constants.AUTO_MESSAGE_SMS:
                Uri u = Uri.parse("smsto:600527529");
                Intent it = new Intent(Intent.ACTION_SENDTO, u);
                it.putExtra("sms_body",actionData);
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
                break;
            case Constants.AUTO_MESSAGE:
                i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage(appPackage);
                i.putExtra(Intent.EXTRA_TEXT, actionData);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent k = Intent.createChooser(i, "Share with");
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(k);
                break;
            case Constants.AUTO_PHOTO_SELF:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                i.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivity(i);
            case Constants.AUTO_PHOTO:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
                startActivity(i);
                break;
            case Constants.AUTO_VIDEO:
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

                // start the Video Capture Intent
                startActivity(intent);
                break;
            case Constants.AUTO_CALL:
                String uri = "tel:" + actionData.trim();
                i = new Intent(Intent.ACTION_CALL);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setData(Uri.parse(uri));
                startActivity(i);
                break;
            case Constants.QUICK_ALARM:
                int time = Integer.parseInt(actionData);
                i = new Intent(AlarmClock.ACTION_SET_TIMER)
                        .putExtra(AlarmClock.EXTRA_MESSAGE, "test")
                        .putExtra(AlarmClock.EXTRA_LENGTH, time)
                        .putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }

                break;
            case Constants.PAUSE_PLAY_MEDIA:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE));
                break;
            case Constants.FORWARD_MEDIA:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
                break;
            case Constants.BACKWARD_MEDIA:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS));
                break;
            case Constants.VOLUME_UP:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_UP));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_UP));
                break;
            case Constants.VOLUME_DOWN:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_VOLUME_DOWN));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_VOLUME_DOWN));
                break;
            case Constants.MUTE:
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MUTE));
                getCurrentInputConnection().sendKeyEvent(
                        new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MUTE));
                break;
        }
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "DroidMotion");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
}
