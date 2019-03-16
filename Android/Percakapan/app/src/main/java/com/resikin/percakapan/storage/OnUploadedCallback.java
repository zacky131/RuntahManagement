package com.resikin.percakapan.storage;

import android.net.Uri;

/**
 * Created by stefanodp91 on 07/09/17.
 */
public interface OnUploadedCallback {
    void onUploadSuccess(String uid, Uri downloadUrl, String type);

    void onProgress(double progress);

    void onUploadFailed(Exception e);
}