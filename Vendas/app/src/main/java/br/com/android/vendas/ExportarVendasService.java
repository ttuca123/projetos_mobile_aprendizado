package br.com.android.vendas;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Tuca on 02/04/2017.
 */

public class ExportarVendasService extends Service implements  Runnable {


    public void onCreate(){
        new Thread(ExportarVendasService.this).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(ExportarVendasService.this).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        for(int i=0; i<10000; i++){

            try {
                Thread.sleep(100);
                Log.d("Incluindo "+i, "Incluindo "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
