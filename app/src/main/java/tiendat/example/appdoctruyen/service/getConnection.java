package tiendat.example.appdoctruyen.service;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import tiendat.example.appdoctruyen.global.global;

public class getConnection extends Service {


    public getConnection() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                boolean temp = checkConnect();

                while (true){
                    if (temp != checkConnect()){
                        global.isConnected = checkConnect();
                        temp = checkConnect();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


        return START_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkConnect(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            return true;
        }
        else{
            return false;
        }

    }
}