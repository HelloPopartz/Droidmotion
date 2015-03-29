package com.tactil.lpro.muv_config.data.res;

import com.tactil.lpro.muv_config.data.Constants;

import java.io.Serializable;

/**
 * Created by Resh on 21/02/2015.
 */
public class ButtonInfo implements Serializable {
    private String buttonName;
    private String app;
    private String appPackage;
    private short action;
    private String actionData;

    public ButtonInfo(String buttonName) {
        this.buttonName = buttonName;
        this.action = -1;
    }

    public String getName() {
        return this.buttonName;
    }

    public String getApp() {
        return this.app;
    }

    public short getAction() {
        return this.action;
    }


    public short getActionCode() {
        return this.action;
    }

    public String getActionData() {
        return this.actionData;
    }

    public String getAppPackage() {
        return this.appPackage;
    }

    public void changeAction(String app, String appPackage, short action, String actionData) {
        this.app = app;
        this.appPackage = appPackage;
        this.action = action;
        this.actionData = actionData;
    }
}

