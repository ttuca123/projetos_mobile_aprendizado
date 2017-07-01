package br.com.zenus.util;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import br.com.zenus.EnuServicos;
import br.com.zenus.prazercity.R;

/**
 * Created by Tuca on 14/06/2017.
 */

public class Utilitarios {


    private static String SERVIDOR_LOCAL = "http://192.168.25.9/projetos_web_aprendizado/prazer-city/";
    private static String SERVIDOR_PRODUCAO = "https://prazercity.000webhostapp.com/prazer/";

    public static final String ADMOB_UNIC = "ca-app-pub-5459227351754314/1383857388";


    public static boolean verificarConexao(Context context) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }



    public static String acessarServico(EnuServicos enuServico) {

        StringBuilder realizarServico = new StringBuilder();

        realizarServico.append(SERVIDOR_PRODUCAO);

        realizarServico.append(enuServico.getNomeAmigavel());

        return realizarServico.toString();
    }
}
