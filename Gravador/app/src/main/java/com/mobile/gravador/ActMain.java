package com.mobile.gravador;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ActMain extends AppCompatActivity {


    private Button btnGravar;
    private Button btnParar;
    private Button btnPlayUltimoAudio;
    private Button btnStopUltimoAudio;

    String audioSavePathInDevice;
    MediaRecorder mediaRecorder;
    Random random;
    String randomAudioFileName = "ABCDEFGH";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        btnGravar = (Button) findViewById(R.id.btnGravar);
        btnParar = (Button) findViewById(R.id.btnStop);
        btnPlayUltimoAudio = (Button) findViewById(R.id.btnStart);
        btnStopUltimoAudio = (Button) findViewById(R.id.btnStopRecord);

        btnParar.setEnabled(false);
        btnPlayUltimoAudio.setEnabled(false);
        btnStopUltimoAudio.setEnabled(false);

        random = new Random();
        requestPermission();

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkPermission()) {

                    audioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                            + createRandomAudioFileName(5)+"AudioRecording.3gp";

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    btnGravar.setEnabled(false);
                    btnParar.setEnabled(true);
                }

            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();

                btnParar.setEnabled(false);
                btnPlayUltimoAudio.setEnabled(true);
                btnGravar.setEnabled(true);
                btnStopUltimoAudio.setEnabled(false);

                Toast.makeText(ActMain.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnPlayUltimoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnParar.setEnabled(false);
                btnGravar.setEnabled(false);
                btnStopUltimoAudio.setEnabled(true);


                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(audioSavePathInDevice);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(ActMain.this, "Recording Playing",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnStopUltimoAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnParar.setEnabled(false);
                btnGravar.setEnabled(true);
                btnStopUltimoAudio.setEnabled(false);
                btnPlayUltimoAudio.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    MediaRecorderReady();
                }
            }
        });

    }


    public String createRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(randomAudioFileName.
                    charAt(random.nextInt(randomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    public void MediaRecorderReady(){

        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioSavePathInDevice);
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(ActMain.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(ActMain.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ActMain.this,"Permission  Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

}
