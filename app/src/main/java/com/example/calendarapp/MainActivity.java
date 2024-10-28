package com.example.calendarapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";;
    private Calendar selectedDate;
    private  CalendarView calendarView;
    private ArrayList<Events> eventsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Update();
        createNotificationChannel();
    }
    private void Update(){
        eventsList = new ArrayList<Events>();
        Events events = new Events();
        Events events1 = new Events();
        Events events2 = new Events();
        Events events3 = new Events();
        events.setName("1123");
        events1.setName("LL");
        events2.setName("1123123");
        events3.setName("День рождение Вики Анатольевны");
        events.setBithday(new GregorianCalendar(2024, Calendar.OCTOBER, 25));
        events1.setBithday(new GregorianCalendar(2024, Calendar.OCTOBER , 26));
        events2.setBithday(new GregorianCalendar(2024, Calendar.OCTOBER , 27));
        events3.setBithday(new GregorianCalendar(2024, Calendar.OCTOBER , 28));
        eventsList.add(events);
        eventsList.add(events1);
        eventsList.add(events2);
        eventsList.add(events3);
        ListView eventList = findViewById(R.id.Events);
        EventsAdapter adapter = new EventsAdapter(this, R.layout.list_item, eventsList);
        eventList.setAdapter(adapter);
        calendarView = findViewById(R.id.calendarView);
        List<Calendar> calendars = new ArrayList<>();
        for (Events event:eventsList) {
            calendars.add(event.getBithday());
        }
        calendarView.setHighlightedDays(calendars);
    };
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "Cat Notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, channelName, importance);
            channel.setDescription("Channel for cat feeding reminders");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void addEvent(View view)
    {
        Calendar date = calendarView.getFirstSelectedDate();
        CustomDialogFragment dialog = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putString("date",date.get(Calendar.YEAR)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.DATE));
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "custom");
        notifyEvent("Кости");
    }
    private void notifyEvent(String name)
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Напоминание")
                        .setContentText("Сегодня день рождения у "+ name)
                        .setPriority(NotificationCompat.PRIORITY_MAX);

        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(MainActivity.this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
