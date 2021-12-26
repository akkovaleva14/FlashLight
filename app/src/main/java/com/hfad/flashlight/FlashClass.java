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

    // startLight, endLight, startStrobe, endStrobe, isStrobeOn

    // функция отвечает за включение фонарика
    public void startLight() {
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager != null) {
            String cameraId = null;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
                // выключаем фонарик:
                cameraManager.setTorchMode(cameraId, false);
                // включаем фонарик: чтобы переключиться между стробоскопом и обычным фонариком
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
            String cameraId = null;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
                // выключаем фонарик:
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
                    String cameraId = null;
                    try {
                        cameraId = cameraManager.getCameraIdList()[0];
                        do {
                            cameraManager.setTorchMode(cameraId, true);
                            int freq = 5;
                            Thread.sleep(100 - freq);
                            cameraManager.setTorchMode(cameraId, false);
                            Thread.sleep(freq);
                        } while (strobeStatus);
                    } catch (CameraAccessException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();
    }

    // flashStatus = true;
    public void endStrobe() {
        strobeStatus = false;
        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        if (cameraManager != null) {
            String cameraId = null;
            try {
                cameraId = cameraManager.getCameraIdList()[0];
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
