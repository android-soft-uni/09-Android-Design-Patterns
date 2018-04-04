package com.cocacola.besanta.ui.createstory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.cocacola.besanta.R;

import java.io.File;
import java.util.ArrayList;

import javax.inject.Inject;

import ly.img.android.PESDK;
import ly.img.android.sdk.models.config.ImageStickerConfig;
import ly.img.android.sdk.models.config.StickerCategoryConfig;
import ly.img.android.sdk.models.config.interfaces.StickerConfigInterface;
import ly.img.android.sdk.models.config.interfaces.ToolConfigInterface;
import ly.img.android.sdk.models.constant.Directory;
import ly.img.android.sdk.models.state.CameraSettings;
import ly.img.android.sdk.models.state.EditorSaveSettings;
import ly.img.android.sdk.models.state.PESDKConfig;
import ly.img.android.sdk.models.state.manager.SettingsList;
import ly.img.android.sdk.tools.StickerEditorTool;
import ly.img.android.sdk.tools.TextEditorTool;
import ly.img.android.sdk.tools.TransformEditorTool;
import ly.img.android.ui.activities.CameraPreviewBuilder;
import ly.img.android.ui.activities.ImgLyIntent;

public class ImageCaptureConfig {

    private static final String FOLDER = "ImgLy";
    public static int CAMERA_PREVIEW_RESULT = 1;

    @Inject
    public ImageCaptureConfig() {
    }

    public void launchCameraCapturing(Activity context) {
        new CameraPreviewBuilder(context)
                .setSettingsList(getDefaultCapturingSettings())
                .startActivityForResult(context, CAMERA_PREVIEW_RESULT);
    }

    public String getImageCapturedPath(Context context, int requestCode, Intent data) {
        if(requestCode == CAMERA_PREVIEW_RESULT) {
            String resultPath = data.getStringExtra(ImgLyIntent.RESULT_IMAGE_PATH);
            String sourcePath = data.getStringExtra(ImgLyIntent.SOURCE_IMAGE_PATH);

            if (resultPath != null) {
                // Add result file to Gallery
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(resultPath))));
            }

            if (sourcePath != null) {
                // Add sourceType file to Gallery
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(sourcePath))));
            }
            return resultPath;
        }
        return null;
    }

    private SettingsList getDefaultCapturingSettings() {
        SettingsList settingsList = new SettingsList();
        PESDKConfig config = settingsList.getConfig();

        ArrayList<ToolConfigInterface> tools = new ArrayList<>();

        StickerConfigInterface stickerBeard = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.op, R.drawable.op);
        StickerConfigInterface stickerSock = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.like_pressed, R.drawable.like_pressed);
        StickerConfigInterface stickerSnow = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.ornaments_1, R.drawable.ornaments_1);
        StickerConfigInterface stickerSnow2 = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.ornaments_2, R.drawable.ornaments_2);
        StickerConfigInterface stickerSnow3 = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.ornaments_3, R.drawable.ornaments_3);
        StickerConfigInterface stickerSnow4 = new ImageStickerConfig("stickerId", R.string.beard, R.drawable.ornaments_4, R.drawable.ornaments_4);

        ArrayList<StickerConfigInterface> stickersInCategory = new ArrayList<>();
        stickersInCategory.add(stickerBeard);
        stickersInCategory.add(stickerSock);
        stickersInCategory.add(stickerSnow);
        stickersInCategory.add(stickerSnow2);
        stickersInCategory.add(stickerSnow3);
        stickersInCategory.add(stickerSnow4);
        StickerCategoryConfig stickerCategoryConfig =
                new StickerCategoryConfig(R.string.sticker_category_cola, R.drawable.like_not_pressed, stickersInCategory);

        config.setStickerLists(stickerCategoryConfig);
        tools.add(
                new StickerEditorTool(R.string.imgly_tool_name_sticker,         R.drawable.imgly_icon_tool_sticker));

        tools.add(new TransformEditorTool(R.string.imgly_tool_name_crop,               R.drawable.imgly_icon_tool_transform));
        tools.add(new TextEditorTool(R.string.imgly_tool_name_text, R.drawable.imgly_icon_tool_text));

        config.setTools(tools);

        settingsList
                .getSettingsModel(CameraSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("camera_")

                .getSettingsModel(EditorSaveSettings.class)
                .setExportDir(Directory.DCIM, FOLDER)
                .setExportPrefix("result_")
                .setJpegQuality(80, false)
                .setSavePolicy(EditorSaveSettings.SavePolicy.KEEP_SOURCE_AND_CREATE_ALWAYS_OUTPUT);

        return settingsList;

    }
}
