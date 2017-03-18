package br.com.android.exemploprogressbar;

import android.app.Activity;
import android.os.Handler;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class AppProgressBar extends Activity {

    ProgressBar mProgressBar;
    private int mProgressStatus = 0;

    Button button;

    Handler mHandler = new Handler();
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_progress_bar);

        mProgressBar = (ProgressBar) findViewById(R.id.progresso);


        button = (Button) findViewById(R.id.btnDownload);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (mProgressStatus < 100) {
                            mProgressStatus++;

                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    mProgressBar.setProgress(mProgressStatus);
                                }
                            });

                        }
                    }
                });

            }
        });
    }



}
