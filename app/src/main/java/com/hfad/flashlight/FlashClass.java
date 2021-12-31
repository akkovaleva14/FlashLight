package com.hfad.flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;

public class FlashClass {

    Context context;

    CameraManager cameraManager;
    boolean flashStatus = false;
    boolean strobeStatus = false;

    FlashClass(Context context) {
        this.context = context;
    }

    public void startLight() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager != null) {
            String cameraId;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, false);
                cameraManager.setTorchMode(cameraId, true);
                flashStatus = true;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void endLight() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager != null) {
            String cameraId;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, false);
                flashStatus = false;
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void startStrobe() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                strobeStatus = true;
                cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
                if (cameraManager != null) {
                    String cameraId;
                    try {
                        cameraId = cameraManager.getCameraIdList()[0];
                        while (strobeStatus) {
                            cameraManager.setTorchMode(cameraId, true);
                            int freq = 5;
                            Thread.sleep(100 - freq);
                            cameraManager.setTorchMode(cameraId, false);
                            Thread.sleep(freq);
                            cameraManager.setTorchMode(cameraId, true);
                        }
                    } catch (CameraAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void endStrobe() {
        strobeStatus = false;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager != null) {
            String cameraId;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(cameraId, false);
                cameraManager.setTorchMode(cameraId, true);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isStrobeOn() {
        return strobeStatus;
    }

}
