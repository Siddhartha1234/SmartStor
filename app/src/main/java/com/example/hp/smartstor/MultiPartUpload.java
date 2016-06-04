package com.example.hp.smartstor;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;


class MultiPartUpload {
    Context context;
    Boolean autoDeleteUploadedFiles =Boolean.FALSE;
    Boolean fixedLengthStreamingMode =Boolean.FALSE;

    MultiPartUpload(Context context){
        this.context = context;

    }
    void Upload(String serverUrl,String filesToUpload) {
        final String serverUrlString = serverUrl;

        final String filesToUploadString = filesToUpload;
        final String[] filesToUploadArray = filesToUploadString.split(",");

        for (String fileToUploadPath : filesToUploadArray) {
            try {
                final String filename = getFilename(fileToUploadPath);

                MultipartUploadRequest req = new MultipartUploadRequest(context, serverUrlString)
                        .addFileToUpload(fileToUploadPath, filename)
                        .setNotificationConfig(getNotificationConfig(filename))
                        .setAutoDeleteFilesAfterSuccessfulUpload(autoDeleteUploadedFiles)
                        .setUsesFixedLengthStreamingMode(fixedLengthStreamingMode)
                        .setCustomUserAgent("SmartStor"+ BuildConfig.VERSION_NAME)
                        .setMaxRetries(2);

                String uploadID = req.startUpload();


                // these are the different exceptions that may be thrown
            } catch (FileNotFoundException exc) {
                showToast(exc.getMessage());
            } catch (IllegalArgumentException exc) {
                showToast("Missing some arguments. " + exc.getMessage());
            } catch (MalformedURLException exc) {
                showToast(exc.getMessage());
            }
        }
    }
    private UploadNotificationConfig getNotificationConfig(String filename) {

        return new UploadNotificationConfig()
                .setIcon(R.drawable.ic_cloud_upload_black_24dp)
                .setTitle(filename)
                .setInProgressMessage("Uploading")
                .setCompletedMessage("Successfully Uploaded")
                .setErrorMessage("Error in Uploading")
                .setAutoClearOnSuccess(Boolean.FALSE)
                .setClickIntent(new Intent(context, MainActivity.class))
                .setClearOnAction(true)
                .setRingToneEnabled(true);
    }

    private String getFilename(String filepath) {
        if (filepath == null)
            return null;

        final String[] filepathParts = filepath.split("/");

        return filepathParts[filepathParts.length - 1];

    }
    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

