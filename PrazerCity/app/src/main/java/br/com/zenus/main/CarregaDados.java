package br.com.zenus.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

import br.com.zenus.EnuServicos;
import br.com.zenus.entidades.DaoSession;
import br.com.zenus.entidades.Local;
import br.com.zenus.entidades.LocalMaster;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.Utilitarios;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class CarregaDados extends AppCompatActivity {


    private ProgressDialog dialog;
    public static DaoSession daoSession;
    private List<Local> locais;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_carrega_dados);

        daoSession = ((App) getApplication()).getDaoSession();


        dialog = ProgressDialog.show(this, "Atualização dos Dados",
                "Carregando Novos Locais", false, true);

        popularDados();

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

    }



    public void popularDados() {


        try {
            Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.LOCAIS))
                    .as(JsonObject.class)
                    .setCallback(new FutureCallback<JsonObject>() {


                        @Override
                        public void onCompleted(Exception e, JsonObject result) {


                            if (result != null) {
                                Gson gson = new Gson();

                                LocalMaster localMaster = null;

                                localMaster = gson.fromJson(result, LocalMaster.class);

                                locais = localMaster.getLocais();

                                daoSession.deleteAll(Local.class);

                                for (Local local : locais) {

                                    if (local.getNome().contains("")) {
                                        local.getNome().replace("\\u00c3\\u00a7", "ç");
                                    }

                                    daoSession.getLocalDao().insert(local);


                                }
                                Toast.makeText(getBaseContext(), "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();


                            } else {
                                Toast.makeText(getBaseContext(), "Erro ao atualizar dados!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        } finally {
            Intent it = new Intent(CarregaDados.this, MainActivity.class);
            startActivity(it);
            finish();
            dialog.dismiss();
        }


    }
}
