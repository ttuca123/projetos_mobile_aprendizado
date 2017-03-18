package br.com.android.media;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "livro";
    private MediaPlayer player;
    private EditText text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (EditText) findViewById(R.id.arquivo);
        findViewById(R.id.start).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        player = MediaPlayer.create(MainActivity.this, R.raw.florida);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.start:
                String mp3 = text.getText().toString();

                player.start();


            break;
            case R.id.pause:
                player.pause();

                break;

            case R.id.stop:
                player.stop();
                
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
