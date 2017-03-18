package br.com.android.helloreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Tuca on 06/03/2017.
 */

public class HelloReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        Log.d("Android", "Oi");
    }
}
