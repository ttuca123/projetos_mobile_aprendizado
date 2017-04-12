package br.com.android.vendas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Tuca on 11/04/2017.
 */

public class ExemploBroadcastReceiver   extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVER")){

            Bundle bundle = intent.getExtras();

            if(bundle != null){
                Object[] pdus = (Object[]) bundle.get("pdus");

                final SmsMessage[] messages = new SmsMessage[pdus.length];

                for(int i=0; i< pdus.length ; i++){

                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);


                }

                if(messages.length>-1){
                    if(messages[0].getMessageBody().equals("replicar")){

                        Intent serviceIntent = new Intent(context,ExportarVendasService.class);

                        context.startService(serviceIntent);
                    }

                }

            }
        }
    }
}
