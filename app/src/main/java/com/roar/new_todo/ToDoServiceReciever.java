package com.roar.new_todo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ToDoServiceReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("service started working!");

        Intent startServiceIntent = new Intent(context, ToDoService.class);
        PendingIntent contentIntent = PendingIntent.getService(context,
                ToDoService.SERVICE_ID, startServiceIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + AlarmManager.INTERVAL_HALF_HOUR,
                AlarmManager.INTERVAL_HOUR,
                contentIntent);
    }
}
