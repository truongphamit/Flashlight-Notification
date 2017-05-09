package com.pqs.flashlightnotification.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.pqs.flashlightnotification.provider.SharePreferenceManager;
import com.pqs.flashlightnotification.services.CallService;
import com.pqs.flashlightnotification.utils.Flash;
import com.pqs.flashlightnotification.utils.Utils;

public class IncomingReveiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            if (SharePreferenceManager.getOnOff(context) && SharePreferenceManager.getIncomingSMS(context)) {
                incomingSms(context);
            }
        }

        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            if (SharePreferenceManager.getOnOff(context) && SharePreferenceManager.getIncomingCall(context)) {
                incomingCall(context);
            }
        }
    }

    private void incomingCall(Context context) {
        try {
            // TELEPHONY MANAGER class object to register one listner
            TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            //Create Listner
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);

            // Register listener for LISTEN_CALL_STATE
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e("Phone Receive Error", " " + e);
        }
    }

    private void incomingSms(Context context) {
        flashSMS(context);
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private Context context;


        private MyPhoneStateListener(Context context) {
            this.context = context;
        }

        public void onCallStateChanged(int state, String incomingNumber) {
            Intent intent = new Intent(context, CallService.class);
            intent.putExtra(Intent.ACTION_CALL, state);
            if (state == TelephonyManager.CALL_STATE_IDLE) {
                // Kết thúc cuộc gọi
//                context.stopService(new Intent(context, CallService.class));
            }
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                // Đổ chuông
//                context.startService(intent);
            }
            if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                // Nghe
//                context.stopService(new Intent(context, CallService.class));
            }
            context.startService(intent);
        }
    }

    private void flashSMS(final Context context) {
        final Flash flash = Flash.getInstance();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int times = Utils.getTime(SharePreferenceManager.getTimesSMS(context));
                    for (int i = 0; i < times; i++) {
                        flash.turnOnFlash();
                        Thread.sleep(Utils.getLength(SharePreferenceManager.getOnLengthSMS(context)));
                        flash.turnOffFlash();
                        Thread.sleep(Utils.getLength(SharePreferenceManager.getOffLengthSMS(context)));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        runnable.run();

    }
}
