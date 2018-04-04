package com.cocacola.besanta.ui.createstory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.cocacola.besanta.di.Injectable;

import javax.inject.Inject;

import ly.img.android.ui.utilities.PermissionRequest;

public class ImageCaptureActivity extends Activity implements PermissionRequest.Response, Injectable {

    public static final String EXTRA_IMAGE_PATH = "com.cocacola.besanta.ui.createstory.EXTRA_IMAGE_PATH";
    @Inject
    ImageCaptureConfig imageCaptureConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageCaptureConfig.launchCameraCapturing(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String imagePath = imageCaptureConfig.getImageCapturedPath(this, requestCode, data);
            Intent imagePathIntent = new Intent();
            imagePathIntent.putExtra(EXTRA_IMAGE_PATH, imagePath);
            setResult(RESULT_OK, imagePathIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionRequest.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void permissionGranted() {

    }

    @Override
    public void permissionDenied() {
        finish();
        System.exit(0);
    }
}
