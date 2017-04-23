package br.com.android.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CadastroTask task = new CadastroTask();
                task.execute();

            }
        });

   }

    private class CadastroTask extends AsyncTask<Void, Void, List<?>> {

        RestAdapter restAdapter;

        @Override
        protected void onPreExecute() {
            try {

                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(API_URL)
                        .build();
            } catch (Exception e) {
                dialog.dismiss();
                AndroidHelper.createDialog(ActPrincipal.this, "Falha de conexão do servidor",
                        "Não foi possível estabelecer conexão com o servidor remoto");
            }
        }


        @Override
        protected List<?> doInBackground(Void... voids) {
            return null;
        }



        @Override
        protected void onPostExecute(List<?> objects) {
            super.onPostExecute(objects);


            if (objects != null) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CadastroActivity.this);
                alertDialog.setTitle("Dados importados");
                alertDialog.setMessage("Todos os dados foram importados com sucesso no dispositivo!");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        irParaActivity(ActPrincipal.class);
                    }
                });
                alertDialog.show();


            } else {
                AndroidHelper.createDialog(ActPrincipal.this, "Erro de importação",
                        "Não foi possível estabelecer conexão com o servidor remoto");
            }
        }
    }

}
