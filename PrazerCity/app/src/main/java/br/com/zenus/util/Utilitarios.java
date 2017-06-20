package br.com.zenus.util;

import android.content.Context;
import android.net.ConnectivityManager;

import br.com.zenus.EnuServicos;

/**
 * Created by Tuca on 14/06/2017.
 */

public class Utilitarios {


    private static String SERVIDOR_LOCAL = "http://192.168.25.9/projetos_web_aprendizado/prazer-city/";
    private static String SERVIDOR_PRODUCAO = "https://prazercity.000webhostapp.com/prazer/";


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
