package br.com.android.media;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Tuca on 27/02/2017.
 */
public class PlayerMp3 implements MediaPlayer.OnCompletionListener{


    private static final String CATEGORIA = "livro Android";
    private static final int NOVO = 0;
    private static final int TOCANDO = 1;
    private static final int PAUSADO = 2;
    private static final int PARADO = 3;

    private int status = NOVO;
    private MediaPlayer player;

    private String mp3;

    public PlayerMp3(){
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
    }

    public void start(String mp3){
        this.mp3 = mp3;

        try{
            switch (status)
            {
                case TOCANDO:
                    player.stop();
                break;
                case PARADO:
                    player.reset();
                    break;

                case NOVO:
                    player.setDataSource(mp3);
                    player.prepare();
                    break;

                case PAUSADO:
                    player.stop();
                    break;

            }
            status = TOCANDO;
        }catch(Exception ex){
            Log.e(CATEGORIA, ex.getMessage(), ex);
        }
    }

    public  void pause(){
        player.pause();
        status =PAUSADO;
    }


    public  void stop(){
        player.stop();
        status =PARADO;
    }

    public void close(){

        stop();
        player.release();
        player = null;
    }









    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(CATEGORIA, "Fim da música "+mp3);
    }

    public boolean isPlaying(){
        return status == TOCANDO || status == PAUSADO;
    }
}
