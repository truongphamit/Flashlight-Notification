package com.pqs.flashlightnotification.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.pqs.flashlightnotification.provider.SharePreferenceManager;
import com.pqs.flashlightnotification.utils.Flash;
import com.pqs.flashlightnotification.utils.Utils;

public class CallService extends Service {

    private Flash flash = Flash.getInstance();

    private boolean finish;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                while (finish) {
                    flash.turnOnFlash();
                    Thread.sleep(Utils.getLength(SharePreferenceManager.getOnLengthSMS(CallService.this)));
                    flash.turnOffFlash();
                    Thread.sleep(Utils.getLength(SharePreferenceManager.getOffLengthSMS(CallService.this)));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Thread thread = new Thread(runnable);

    public CallService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra(Intent.ACTION_CALL, TelephonyManager.CALL_STATE_IDLE)) {
            case TelephonyManager.CALL_STATE_IDLE:
                finish = false;
                stopSelf();
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                finish = true;
                thread.start();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                finish = false;
                stopSelf();
                break;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
