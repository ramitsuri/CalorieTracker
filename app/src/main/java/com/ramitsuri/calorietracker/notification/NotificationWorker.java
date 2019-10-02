package com.ramitsuri.calorietracker.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;

import com.ramitsuri.calorietracker.MainApplication;
import com.ramitsuri.calorietracker.R;
import com.ramitsuri.calorietracker.utils.PrefHelper;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import timber.log.Timber;

public class NotificationWorker extends Worker {

    public NotificationWorker(@NonNull Context context,
            @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        triggerNotification();
        return Result.success();
    }

    private void triggerNotification() {
        int lastIndex =
                PrefHelper.get(getResources().getString(R.string.settings_key_notify_index), -1);
        lastIndex = lastIndex + 1;
        if (lastIndex > 18) {
            return;
        }
        String[] texts = getResources().getStringArray(R.array.notification_texts);
        String text = texts[lastIndex];
        PrefHelper.set(getResources().getString(R.string.settings_key_notify_index), lastIndex);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainApplication.getInstance(), "channel_general")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Important Message")
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationChannel channel = new NotificationChannel(
                "channel_general",
                "General Notifications",
                NotificationManager.IMPORTANCE_DEFAULT);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainApplication.getInstance());

        notificationManager.createNotificationChannel(channel);
        builder.setChannelId(channel.getId());
        Timber.i("Showing notification");
        notificationManager.notify(1001, builder.build());
    }

    private Resources getResources() {
        return MainApplication.getInstance().getResources();
    }
}
