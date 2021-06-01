package com.example.notificationreader;

import android.app.Notification;
import android.content.Context;
import android.os.Environment;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class NotiListenerService extends NotificationListenerService {

    private final static String TAG = "NotiReader";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Service Working");
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        String title = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE));
        String bigTitle = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TITLE_BIG));
        String text = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT));
        String bigText = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_BIG_TEXT));
        String infoText = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_INFO_TEXT));
        String subText = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_SUB_TEXT));
        String summaryText = String.valueOf(sbn.getNotification().extras.getCharSequence(Notification.EXTRA_SUMMARY_TEXT));
        String packageName = sbn.getPackageName();
        String postTime = String.valueOf(sbn.getPostTime());
        String tag = sbn.getTag();
        String isOngoing = String.valueOf(sbn.isOngoing());


        if(packageName.contains("com.google.android.googlequicksearchbox")
                || packageName.contains("com.sec.android.app.clockpackage")){
            Log.d(TAG, "\n\nTitle : " + title);
            Log.d(TAG, "\nBig Title : " + bigTitle);
            Log.d(TAG, "\nText : " + text);
            Log.d(TAG, "\nBig Text : " + bigText);
            Log.d(TAG, "\nInfo Text : " + infoText);
            Log.d(TAG, "\nSub Text : " + subText);
            Log.d(TAG, "\nSummary Text : " + summaryText);
            Log.d(TAG, "\nPost Time : " + postTime);
            Log.d(TAG, "\nPackage : " + packageName);
            Log.d(TAG, "\nOngoing : " + isOngoing);
            savePublic("\n\nTitle : " + title +
                            //"\nBig Title : " + bigTitle +
                            "\nText : " + text +
                            //"\nBig Text : " + bigText +
                            //"\nSummary Text : " + summaryText +
                            "\nPackage : " + packageName +
                            "\nIs Ongoing : " + isOngoing);
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void savePublic(String data) {

        // getExternalStoragePublicDirectory() represents root of external storage, we are using DOWNLOADS
        // We can use following directories: MUSIC, PODCASTS, ALARMS, RINGTONES, NOTIFICATIONS, PICTURES, MOVIES
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);

        // Storing the data in file with name as geeksData.txt
        File file = new File(folder, "TestData.txt");
        writeTextData(file, data);
    }

    public void savePrivate(String data){

        // Creating folder with name GeekForGeeks
        File folder = getExternalFilesDir("Noti Reader");

        // Creating file with name gfg.txt
        File file = new File(folder, "TestData.txt");
        writeTextData(file, data);
    }

    // writeTextData() method save the data into the file in byte format
    // It also toast a message "Done/filepath_where_the_file_is_saved"
    private void writeTextData(File file, String data) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write(data.getBytes());
            Toast.makeText(this, "Done" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d(TAG, "Write Failed" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showPublicData() {
        // Accessing the saved data from the downloads folder
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        // geeksData represent the file data that is saved publicly
        File file = new File(folder, "TestData.txt");
        String data = getdata(file);
        if (data != null) {
            //textView.setText(data);
        } else {
            //textView.setText("No Data Found");
        }
    }

    public void showPrivateData() {

        // GeeksForGeeks represent the folder name to access privately saved data
        File folder = getExternalFilesDir("Noti Reader");

        // gft.txt is the file that is saved privately
        File file = new File(folder, "ScoresData.txt");
        String data = getdata(file);
        if (data != null) {
            //textView.setText(data);
        } else {
            //textView.setText("No Data Found");
        }
    }

    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
