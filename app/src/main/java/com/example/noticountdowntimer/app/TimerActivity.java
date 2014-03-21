package com.example.noticountdowntimer.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TimerActivity extends ActionBarActivity {
    private final int mId = 1;
    private long[] mVibratePattern = {0, 200, 200, 300};
    private Uri soundURI = Uri.parse("content://com.android.alarmclock/alarm");
    private EditText time;
    private TextView showTime;
    private Button start_but;
    private Button stop_but;
    private CountDownTimer countDownTimer;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private Intent mNotificationIntent;
    private PendingIntent mContentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //initialize
        time = (EditText) findViewById(R.id.number);
        showTime = (TextView) findViewById(R.id.showTime);
        start_but = (Button) findViewById(R.id.count_but);
        stop_but = (Button) findViewById(R.id.stop_but);
        mBuilder = new NotificationCompat.Builder(this).
                setSmallIcon(android.R.drawable.ic_dialog_alert).setContentTitle("Noti title")
                .setContentText("ContentText")
        .setVibrate(mVibratePattern)
        .setSound(soundURI)
        .setContentIntent(mContentIntent);
        mNotificationManager = (NotificationManager) getSystemService
                (Context.NOTIFICATION_SERVICE);

        mNotificationIntent = new Intent(getApplicationContext(), TimerActivity.class);
        mContentIntent = PendingIntent
                .getActivity(getApplicationContext(), 0, mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        start_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!time.getText().toString().equalsIgnoreCase("")) {
                    startCountDownTimer(Integer.parseInt(time.getText().toString()));
                    start_but.setVisibility(View.GONE);
                    time.setVisibility(View.GONE);
                    stop_but.setVisibility(View.VISIBLE);
                }
            }
        });

        stop_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finishCouting();
            }
        });
        Intent resultIntent = new Intent(this, TimerActivity.class);
        setUpNotification(resultIntent);
    }

    private void setUpNotification(Intent intent) {


    }

    private void finishCouting() {
        stop_but.setVisibility(View.GONE);
        time.setVisibility(View.VISIBLE);
        time.setText("");
        start_but.setVisibility(View.VISIBLE);
    }

    private void startCountDownTimer(int timeInSecond) {
        timeInSecond *= 1000;
        countDownTimer = new CountDownTimer(timeInSecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showTime.setText(millisUntilFinished / 1000 + "");
            }

            @Override
            public void onFinish() {
                showTime.setText("0");
                finishCouting();
                mNotificationManager.notify(mId, mBuilder.build());
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
