package com.hellopopartz.hidlauncher.data;

import android.app.Application;
import android.content.Context;
import android.view.KeyEvent;

import com.hellopopartz.hidlauncher.data.res.ButtonInfo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Resh on 22/02/2015.
 */
public class CommonData extends Application {
    private HashMap<String, ArrayList<Short>> availableActions;
    private LinkedHashMap<Integer, ButtonInfo> availableButtons;
    private String filePath = "buttons.bin";


    private boolean firstUse;

    public void onCreate() {
        super.onCreate();
        load();
        addSupportedApps();
    }

    private void searchAvailableButtons() {
        this.availableButtons = new LinkedHashMap<Integer, ButtonInfo>();

        this.availableButtons.put(KeyEvent.KEYCODE_1, new ButtonInfo("L1"));
        this.availableButtons.put(KeyEvent.KEYCODE_2, new ButtonInfo("R1"));
        this.availableButtons.put(KeyEvent.KEYCODE_3, new ButtonInfo("U1"));
        this.availableButtons.put(KeyEvent.KEYCODE_4, new ButtonInfo("D1"));
        this.availableButtons.put(KeyEvent.KEYCODE_5, new ButtonInfo("C1"));

    }

    public ArrayList<ButtonInfo> getAvailableButtons() {
        return new ArrayList<ButtonInfo>(this.availableButtons.values());
    }

    public void setButton(int position, String app, String appPackage, short action, String actionData) {
        ButtonInfo b = (ButtonInfo) this.availableButtons.values().toArray()[position];
        b.changeAction(app, appPackage, action, actionData);
    }

    public ButtonInfo getButton(int keyCode) {
        return this.availableButtons.get(keyCode);
    }

    public boolean isSupported(String app) {
        return this.availableActions.containsKey(app);
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(openFileOutput(filePath, Context.MODE_PRIVATE));
            oos.writeObject(availableButtons);
            oos.flush();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load() {
        //Read user preferences
        try {
            ObjectInputStream ois = new ObjectInputStream(openFileInput(filePath));
            availableButtons = (LinkedHashMap<Integer, ButtonInfo>) ois.readObject();
            ois.close();
        } catch (Exception ex) {
            //Debug buttons - in future version the user can define custom buttons
            searchAvailableButtons();
        }
    }

    private void addSupportedApps() {
        this.availableActions = new HashMap<String, ArrayList<Short>>();
        this.availableActions.put("com.whatsapp", new ArrayList<Short>(Arrays.asList(Constants.OPEN_APP, Constants.AUTO_MESSAGE)));
        this.availableActions.put("org.telegram.messenger", new ArrayList<Short>(Arrays.asList(Constants.OPEN_APP, Constants.AUTO_MESSAGE)));
        this.availableActions.put("system.dialer", new ArrayList<Short>(Arrays.asList(Constants.AUTO_CALL)));
        this.availableActions.put("system.sms", new ArrayList<Short>(Arrays.asList(Constants.AUTO_MESSAGE_SMS)));
        this.availableActions.put("system.alarm", new ArrayList<Short>(Arrays.asList(Constants.QUICK_ALARM)));
        this.availableActions.put("system.camera", new ArrayList<Short>(Arrays.asList(Constants.AUTO_PHOTO, Constants.AUTO_PHOTO_SELF, Constants.AUTO_VIDEO)));
        this.availableActions.put("system.media", new ArrayList<Short>(Arrays.asList(Constants.PAUSE_PLAY_MEDIA, Constants.FORWARD_MEDIA, Constants.BACKWARD_MEDIA, Constants.VOLUME_UP, Constants.VOLUME_DOWN, Constants.MUTE)));

    }

    public ArrayList<Short> getActions(String packageName) {
        return this.availableActions.get(packageName);
    }
}
