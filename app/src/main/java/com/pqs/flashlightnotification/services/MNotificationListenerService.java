package com.pqs.flashlightnotification.services;

import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.pqs.flashlightnotification.models.App;
import com.pqs.flashlightnotification.utils.Flash;
import com.pqs.flashlightnotification.utils.Utils;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by truongpq on 5/15/17.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MNotificationListenerService extends NotificationListenerService {
    private List<App> checkApps;

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn){
        // Implement what you want here
        for (App app: checkApps) {
            if (app.getApplicationInfo().packageName.equals(sbn.getPackageName())) {
                Flash.test(100, 100);
                break;
            }
        }
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        try {
            checkApps = Utils.getAppsNotification(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onNotificationRemoved(StatusBarNotification sbn){
        // Implement what you want here
    }
}
