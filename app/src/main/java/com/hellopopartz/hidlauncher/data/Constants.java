package com.hellopopartz.hidlauncher.data;

import android.content.Context;
import android.content.res.Resources;
import com.hellopopartz.hidlauncher.R;


/**
 * Created by mrdomint on 28/02/15.
 */
public class Constants {
    public final static short OPEN_APP = 0;
    public final static short AUTO_MESSAGE = 1;
    public final static short AUTO_MESSAGE_SMS = 2;
    public final static short AUTO_PHOTO_SELF = 3;
    public final static short AUTO_PHOTO = 4;
    public final static short AUTO_VIDEO = 5;
    public final static short AUTO_CALL = 6;
    public final static short QUICK_ALARM = 7;
    public final static short PAUSE_PLAY_MEDIA = 8;
    public final static short FORWARD_MEDIA = 9;
    public final static short BACKWARD_MEDIA = 10;
    public final static short VOLUME_UP = 11;
    public final static short VOLUME_DOWN = 12;
    public final static short MUTE = 13;

    public final static String translateAction(short action, Context context) {
        Resources r = context.getResources();
        switch (action) {
            case OPEN_APP:
                return r.getString(R.string.dOpenApp);
            case AUTO_MESSAGE:
                return r.getString(R.string.dAutoMessage);
            case AUTO_MESSAGE_SMS:
                return r.getString(R.string.dAutoMessage);
            case AUTO_PHOTO_SELF:
                return r.getString(R.string.dAutoSelfie);
            case AUTO_PHOTO:
                return r.getString(R.string.dAutoPhoto);
            case AUTO_VIDEO:
                return r.getString(R.string.dAutoVideo);
            case AUTO_CALL:
                return r.getString(R.string.dAutoCall);
            case QUICK_ALARM:
                return r.getString(R.string.dAutoAlarm);
            case PAUSE_PLAY_MEDIA:
                return r.getString(R.string.dPausePlay);
            case FORWARD_MEDIA:
                return r.getString(R.string.dForward);
            case BACKWARD_MEDIA:
                return r.getString(R.string.dBackward);
            case VOLUME_UP:
                return r.getString(R.string.dVolumeUp);
            case VOLUME_DOWN:
                return r.getString(R.string.dVolumeDown);
            case MUTE:
                return r.getString(R.string.dMute);

        }
        return r.getString(R.string.dNoAction);
    }
}
