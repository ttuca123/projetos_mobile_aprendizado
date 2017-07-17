package br.com.android.vendas;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Tuca on 02/04/2017.
 */

public class ExportarVendasService extends Service implements  Runnable {


    ProgressDialog progressDialog;
    int totalDB;

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

    public Handler handler =new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==0) {
                progressDialog.setProgress(progressDialog.getProgress() + 1);
            }else if(msg.what==1){
                progressDialog.dismiss();
                Toast.makeText(ExportarVendasService.this, "Sucesso na replicação", Toast.LENGTH_LONG);
            }else if(msg.what==2){
                progressDialog.dismiss();
                Toast.makeText(ExportarVendasService.this, "Erro na replicação", Toast.LENGTH_LONG);
            }


        }

    };

    @Override
    public void run() {

        SQLiteDatabase db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

        Cursor cursor  = db.rawQuery("SELECT * FROM vendas", null);
        int totalReplicado = 0;

        totalDB = cursor.getCount();

        progressDialog = new ProgressDialog(ExportarVendasService.this);

        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        progressDialog.setCancelable(false);

        progressDialog.setTitle("Replicando dados");

        progressDialog.setMax(cursor.getCount());

        progressDialog.show();


        while(cursor.moveToNext()){

            StringBuilder str = new StringBuilder();
            str.append("http://192.168.25.9/projetos_web_aprendizado/vendas/inserir.php?produto=");
            str.append(cursor.getInt(cursor.getColumnIndex("produto")));
            str.append("&preco=");
            str.append(cursor.getDouble(cursor.getColumnIndex("preco")));
            str.append("&latitude=");
            str.append(cursor.getString(cursor.getColumnIndex("la")));
            str.append("&longitude=");
            str.append(cursor.getString(cursor.getColumnIndex("lo")));

            try {
                URL url = new URL(str.toString());

                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                InputStreamReader ips = new InputStreamReader(http.getInputStream());
                BufferedReader line = new BufferedReader(ips);

                String linhaRetorno = line.readLine();

                    db.delete("vendas", "_id=?", new String[]{String.valueOf(cursor.getInt(0))});
                    handler.sendEmptyMessage(0);
                    totalReplicado++;


                Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext()).setSmallIcon(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .setContentTitle("Replicação realizada com sucesso").setContentText("Dados replicados")
                        .setAutoCancel(true).setTicker("Nova Mensagem!").setSound(alarmSound).setLights(100, 50, 70);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this.getApplicationContext(), MainActivity.class), 0);

                builder.setContentIntent(pendingIntent);

                builder.setFullScreenIntent(pendingIntent, false);

                NotificationManager notificationManager = (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify((int) Math.random(), builder.build());

            } catch (Exception e) {
                Log.d("Erro", e.toString());
            }

            if(totalReplicado==totalDB){

                handler.sendEmptyMessage(1);
            }
        }

        db.close();
    }



}
