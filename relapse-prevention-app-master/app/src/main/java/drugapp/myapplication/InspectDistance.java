package drugapp.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.R;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Random;

public class InspectDistance {

    private static Random r = new Random();
    private static String[] messages = {
            "remeber why you're diong this!",
            "people are counting on you!",
            "make the right decision!",
    };

    public static void Inspect(Marker current, ArrayList<Marker> ml, Context c) {
        Log.d("Debug", "TESTING DISTANCES");
        Log.d("Debug", "CURRENT = " + current);
        if (current == null) return;
        Log.d("Debug", "SIZE = " + ml.size());
        double cLat = current.getPosition().latitude;
        double cLong = current.getPosition().longitude;
        for (Marker m : ml) {
            Log.d("Debug", "LOC_1 = " + cLat + ", " + cLong);
            Log.d("Debug", "LOC_2 = " + m.getPosition().latitude + ", " + m.getPosition().longitude);
            float results[] = new float[2];
            Location.distanceBetween(cLat, cLong, m.getPosition().latitude, m.getPosition().longitude, results);
            Log.d("Debug", "RESULTS = " + results[0] + ", " + results[1]);
            if (results[0] < 100) {
                new Thread(new HttpPostRequest()).start();
                sendNotification(c);
                return;
            }
        }
    }

    private static void sendNotification(Context c) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(c)
                        .setSmallIcon(R.drawable.alert_light_frame)
                        .setContentTitle("Risk Zone")
                        .setContentText("You entered a Risk Zone, " + messages[r.nextInt(messages.length)]);

        Intent notificationIntent = new Intent(c, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(c, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }


}
