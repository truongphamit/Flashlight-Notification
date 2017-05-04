package com.pqs.flashlightnotification.utils;

import android.hardware.Camera;

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

    private void turnOnFlash() {
        if (!state) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            state = true;
        }
    }

    private void turnOffFlash() {
        if (state) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            state = false;
        }
    }
}
