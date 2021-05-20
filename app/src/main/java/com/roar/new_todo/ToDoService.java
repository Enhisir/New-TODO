package com.roar.new_todo;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.roar.new_todo.data.AppDatabase;
import com.roar.new_todo.data.TaskDao;
import com.roar.new_todo.model.Task;

import java.util.ArrayList;

public class ToDoService extends Service {
    public static final int SERVICE_ID = 1010;
    public static final String CHANNEL = "TODO_SERVICE";
    public static final String INFO = "INFO";

    private static int notificationId = 0;

    private NotificationCompat.Builder builder;
    private NotificationChannel channel;
    private NotificationManager notificationManager;
    private NotificationManagerCompat notificationManagerCompat;

    public ToDoService() {
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL,
                    CHANNEL, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(INFO);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, CHANNEL)
                .setSmallIcon(R.drawable.ic_launcher_round)
                .setContentTitle("New To-Do")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppDatabase db =  Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "table")
                .allowMainThreadQueries().fallbackToDestructiveMigration()
                .build();
        TaskDao taskDao = db.taskDao();

        int number = (new ArrayList<>(taskDao.getByStatus(Task.ACTUAL))).size();
        if (number > 0) {
            builder.setContentText("У вас остались незавершенные задачи!");
            notificationManagerCompat.notify(notificationId++, builder.build());

        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}