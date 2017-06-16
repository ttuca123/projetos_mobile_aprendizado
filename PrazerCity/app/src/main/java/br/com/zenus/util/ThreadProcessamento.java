package br.com.zenus.util;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Tuca on 15/06/2017.
 */

public class ThreadProcessamento implements Runnable {

    private Handler handler;

    public ThreadProcessamento(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run(){

        Message msg = new Message();;
        msg.what=1;

        handler.sendMessage(msg);

    }
}