package com.example.shoppinglist.gui.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shoppinglist.R;
import com.example.shoppinglist.logic.database.DBHelper;
import com.example.shoppinglist.logic.database.ItemContract;
import com.example.shoppinglist.model.MyDate;


import java.text.DateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {

    // region 0. Constants

    private static final int DELAY = 5000;
    private static final int PERIOD = 21600000;

    private static final String CHANNEL_ID = "channel";
    private static final int NOTIFICATION_ID = 69;

    // endregion

    // region 1. Decl and Init

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler;

    // endregion

    // region 2. Lifecycle

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        startTimer();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }

    // endregion

    // region 3. Other methods

    public void startTimer() {

        this.handler = new Handler();
        this.timer = new Timer();

        initTimerTask();

        this.timer.schedule(this.timerTask, DELAY, PERIOD);

    }

    public void stopTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
        }
    }

    public void initTimerTask() {
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        createNotificationChannel();
                    }
                });

            }
        };
    }

    private void createNotificationChannel() {
        CharSequence strName = "You need to buy in these days";
        String strDescription = getDescription();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !strDescription.equals("")) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(strName)
                    .setContentText("List: " + strDescription)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
        }
    }

    // endregion

    // region 4. Getters and Setters

    public MyDate getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        String strCurrentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        String strDay = "";
        String strMonth = "";
        String strYear = "";

        Log.d("date", strCurrentDate);

        int ind = 0;
        while (ind < strCurrentDate.length() && strCurrentDate.charAt(ind) != '/') {
            strMonth += strCurrentDate.charAt(ind);
            ind++;
        }

        ind++;
        while (ind < strCurrentDate.length() && strCurrentDate.charAt(ind) != '/') {
            strDay += strCurrentDate.charAt(ind);
            ind++;
        }

        ind++;
        while (ind < strCurrentDate.length() && strCurrentDate.charAt(ind) != '/') {
            strYear += strCurrentDate.charAt(ind);
            ind++;
        }

        return new MyDate(
                Integer.parseInt("20"+strYear),
                Integer.parseInt(strMonth),
                Integer.parseInt(strDay)
        );
    }

    public String getDescription(){
        StringBuilder strDescription = new StringBuilder();

        MyDate currentDate = getCurrentDate();

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                ItemContract.ItemEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ItemContract.ItemEntry.COLUMN_POSITION + " ASC"
        );

        cursor.moveToFirst();
        do {
            MyDate date = new MyDate(
                    cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_YEAR)),
                    cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_MONTH)),
                    cursor.getInt(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_DATE_DAY))
            );

            if (currentDate.equals(date) || currentDate.equals(date.getOneDayEarlier())){
                strDescription.append(cursor.getString(cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_NAME)).toLowerCase()).append(", ");
            }
        } while(cursor.moveToNext());

        if (!strDescription.toString().equals("")){
            strDescription.delete(strDescription.length()-2, strDescription.length()-1);

        }

        return strDescription.toString();
    }

    // endregion
}
