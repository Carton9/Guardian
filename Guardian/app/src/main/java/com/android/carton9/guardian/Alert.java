package com.android.carton9.guardian;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by qazwq on 6/23/2017.
 */

public class Alert {
    boolean isOpen;
    Service self;
    LocationManager locationManager;
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // 当GPS定位信息发生改变时，更新位置
            act(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        public void onProviderEnabled(String provider) {
            if (ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            act(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onProviderDisabled(String s) {
            sendText("无法检测到GPS");
        }
    };
    public Alert(Service self){
        isOpen=true;
        this.self=self;
        locationManager = (LocationManager) self.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(self, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isOpen=false;
            return;
        }
        Location location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 30, 5, locationListener);
    }
    public void sendMessege(String text){
        SmsManager smsm = SmsManager.getDefault();
        ArrayList<String> textList=new ArrayList<String>();
        if(text.length()>60){
            String buffer

        }
        try {
            PendingIntent pi = PendingIntent.getBroadcast(self, 0, new Intent(), 0);
            smsm.sendTextMessage("********", null, "住家将到达", null, null);
        } catch (Exception e) {
            Log.e("SmsSending", "SendException", e);
        }
    }
}
