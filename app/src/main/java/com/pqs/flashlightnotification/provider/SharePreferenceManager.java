package com.pqs.flashlightnotification.provider;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pqs.flashlightnotification.utils.Constant;

/**
 * Created by truongpq on 05/05/2017.
 */

public class SharePreferenceManager {
    public static void setOnOff(Context context, boolean i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_ON_OFF, i).apply();
    }

    public static boolean getOnOff(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_ON_OFF, false);
    }


    public static void setOnLengthCall(Context context, int i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(Constant.KEY_ON_LENGTH_CALL, i).apply();
    }

    public static int getOnLengthCall(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constant.KEY_ON_LENGTH_CALL, 9);
    }

    public static void setOffLengthCall(Context context, int i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(Constant.KEY_OFF_LENGTH_CALL, i).apply();
    }

    public static int getOffLengthCall(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constant.KEY_OFF_LENGTH_CALL, 9);
    }

    public static void setOnLengthSMS(Context context, int i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(Constant.KEY_ON_LENGTH_SMS, i).apply();
    }

    public static int getOnLengthSMS(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constant.KEY_ON_LENGTH_SMS, 9);
    }

    public static void setOffLengthSMS(Context context, int i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(Constant.KEY_OFF_LENGTH_SMS, i).apply();
    }

    public static int getOffLengthSMS(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constant.KEY_OFF_LENGTH_SMS, 9);
    }

    public static void setTimesSMS(Context context, int i) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt(Constant.KEY_TIMES_SMS, i).apply();
    }

    public static int getTimesSMS(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Constant.KEY_TIMES_SMS, 3);
    }

    public static void setIncomingCall(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_INCOMING_CALL, b).apply();
    }

    public static boolean getIncomingCall(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_INCOMING_CALL, true);
    }

    public static void setIncomingSMS(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_INCOMING_SMS, b).apply();
    }

    public static boolean getIncomingSMS(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_INCOMING_SMS, true);
    }

    public static void setNormal(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_NORMAL, b).apply();
    }

    public static boolean getNormal(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_NORMAL, true);
    }

    public static void setVibration(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_VIRBATION, b).apply();
    }

    public static boolean getVibration(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_VIRBATION, true);
    }

    public static void setSilent(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_SILENT, b).apply();
    }

    public static boolean getSilent(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_SILENT, true);
    }

    public static void setNotifications(Context context, boolean b) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putBoolean(Constant.KEY_NOTIFICATIONS, b).apply();
    }

    public static boolean getNotifications(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(Constant.KEY_NOTIFICATIONS, false);
    }
}
