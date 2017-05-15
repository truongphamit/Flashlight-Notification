package com.pqs.flashlightnotification.models;

import android.content.pm.ApplicationInfo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by truongpq on 5/15/17.
 */

public class App {
    private ApplicationInfo applicationInfo;
    private boolean isCheck;

    public App() {}

    public App(ApplicationInfo applicationInfo, boolean isCheck) {
        this.applicationInfo = applicationInfo;
        this.isCheck = isCheck;
    }

    public ApplicationInfo getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(ApplicationInfo applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof App && ((App) obj).applicationInfo.packageName.equals(applicationInfo.packageName);
    }

    public JSONObject buildJsonObject() throws JSONException {
        StringBuilder text = new StringBuilder();
        text.append("{\n");
        text.append("packagename: ").append(applicationInfo.packageName).append("\n");
        text.append("}\n");
        return new JSONObject(text.toString());
    }
}
