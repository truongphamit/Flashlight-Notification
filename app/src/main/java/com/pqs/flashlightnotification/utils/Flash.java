package com.pqs.flashlightnotification.utils;

import android.hardware.Camera;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

/**
 * Created by truongpq on 5/3/17.
 */

public class Flash {
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean state;

    private static Flash instance;

    public static Flash getInstance() {
        if (instance == null) {
            instance = new Flash();
        }
        return instance;
    }

    private Flash() {
        camera = Camera.open();
        parameters = camera.getParameters();
    }

    public boolean getState() {
        return state;
    }

    public void switchFlash() {
        if (state) {
            turnOffFlash();
        } else {
            turnOnFlash();
        }
    }

    public void turnOnFlash() {
        if (!state) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            state = true;
        }
    }

    public void turnOffFlash() {
        if (state) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            state = false;
        }
    }

    public static void test(final int on, final int off) {
        final Flash flash = Flash.getInstance();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 3; i++) {
                        flash.turnOnFlash();
                        Thread.sleep(on);
                        flash.turnOffFlash();
                        Thread.sleep(off);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        runnable.run();
    }
}
