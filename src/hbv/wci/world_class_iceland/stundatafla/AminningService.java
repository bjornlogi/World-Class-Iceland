package hbv.wci.world_class_iceland.stundatafla;

import hbv.wci.world_class_iceland.Global;
import hbv.wci.world_class_iceland.R;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Klasi sem extendar pakkann Service, hann hondlar aminninguna
 * 
 * @author Karl & Maria
 *
 */
public class AminningService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		// Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
		makeNotification();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
		return null;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		//super.onStart(intent, startId);
		// Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
		makeNotification();
		
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
		return super.onUnbind(intent);
	}
	
	/**
	 * Byr til notificationid med tvi ad na i first nafnid ur SharedPreferences
	 * 
	 */
	private void makeNotification() {
		SharedPreferences pref = this.getApplicationContext().getSharedPreferences("notifications", 1);
		String ht = pref.getString(Global.timeRightNow(), "-1");
		if (ht != "-1") prepareNotification(ht);
	}
	
	private void prepareNotification(String ht){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("World Class Iceland")
		        .setContentText(ht + " byrjar eftir eina klst");
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, StundataflanMin.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(StundataflanMin.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}
}