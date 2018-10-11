package ezbar.ccx.com.ezbarlib;

import android.hardware.Camera;
import android.view.View;

public class CameraManager {

    private View   mScannerView;
    private Camera mCamera;

    public CameraManager(View view) {
        this.mScannerView = view;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            if (mCamera != null) {
                return mCamera;
            }
            c = Camera.open();
            this.mCamera = c;
        } catch (Exception e) {
        }
        return c;
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    public void stop() {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
    }

    public void start() {
        try {
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        mCamera.setPreviewCallback((Camera.PreviewCallback) mScannerView);
        start();
        autoFocus();
    }

    public void autoFocus() {
        mCamera.autoFocus((Camera.AutoFocusCallback) mScannerView);
    }

    public void openFlash(boolean isOpen) {
        Camera.Parameters parameters = mCamera.getParameters();
        if (isOpen) {
            try {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } catch (Exception ignored) {
            }
        } else {
            try {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            } catch (Exception ignored) {
            }

        }
    }
}