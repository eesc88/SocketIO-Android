package com.mimidc.socketio;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class ConnectService extends Service {
    private Socket mSocket;
    private final IBinder mBinder = new LocalBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        {
            try {
                mSocket = IO.socket(Constants.SOCKET_URL);
                mSocket.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public class LocalBinder extends Binder {
        ConnectService getService() {
            // return this instance of LocalService so clients can call public method
            return ConnectService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("close", new Emitter.Listener() {
            @Override
            public void call(Object... args) {

            }
        });
    }
}
