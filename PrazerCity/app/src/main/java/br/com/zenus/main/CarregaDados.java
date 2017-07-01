package br.com.zenus.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

import br.com.zenus.EnuServicos;
import br.com.zenus.entidades.AtualizacaoLocal;
import br.com.zenus.entidades.AtualizacaoLocalDao;
import br.com.zenus.entidades.DaoSession;
import br.com.zenus.entidades.Local;
import br.com.zenus.vo.LocalMasterVO;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.Utilitarios;
import br.com.zenus.vo.AtualizacaoLocalVO;

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

        verificarAtualizacao();

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

    }


    public void popularDados() {


        Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.LOCAIS))
                .as(JsonObject.class)
                .setCallback(new FutureCallback<JsonObject>() {


                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result != null) {
                            Gson gson = new Gson();

                            LocalMasterVO localMasterVO = null;

                            localMasterVO = gson.fromJson(result, LocalMasterVO.class);

                            locais = localMasterVO.getLocais();

                            daoSession.deleteAll(Local.class);

                            for (Local local : locais) {

                                daoSession.getLocalDao().insert(local);


                            }
                            Toast.makeText(getBaseContext(), "Dados atualizados com sucesso!", Toast.LENGTH_LONG).show();


                        } else {
                            Toast.makeText(getBaseContext(), "Erro ao atualizar dados!", Toast.LENGTH_LONG).show();
                        }

                        Intent it = new Intent(CarregaDados.this, MainActivity.class);
                        startActivity(it);
                        finish();
                        dialog.dismiss();

                    }
                });
    }


    public void verificarAtualizacao() {


        Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.ATUALIZACAO_LOCAL))
                .as(JsonObject.class)
                .setCallback(new FutureCallback<JsonObject>() {


                    @Override
                    public void onCompleted(Exception e, JsonObject result) {


                        if (result != null) {
                            Gson gson = new Gson();

                            AtualizacaoLocalVO atualizacaoLocalVO = null;

                            try {
                                atualizacaoLocalVO = gson.fromJson(result, AtualizacaoLocalVO.class);
                            } catch (JsonSyntaxException e1) {
                                e1.printStackTrace();
                            }


                            AtualizacaoLocal atualizacaoLocal = new AtualizacaoLocal();

                            atualizacaoLocal.setSeqAtualizacao(atualizacaoLocalVO.getSeqAtualizacao());

                            atualizacaoLocal.setCodigoHash(atualizacaoLocalVO.getCodigoHash());

                            atualizacaoLocal.setDtAtualizacao(atualizacaoLocalVO.getDtAtualizacao());


                            Long contadorAtualizacaoDados = daoSession.getAtualizacaoLocalDao().queryBuilder()
                                    .where(AtualizacaoLocalDao.Properties.CodigoHash.
                                            eq(atualizacaoLocal.getCodigoHash())).count();

                            if ( contadorAtualizacaoDados==0L) {

                                daoSession.getAtualizacaoLocalDao().insert(atualizacaoLocal);

                                popularDados();

                            }else{
                                Intent it = new Intent(CarregaDados.this, MainActivity.class);
                                startActivity(it);
                                finish();
                                dialog.dismiss();

                            }

                        } else {

                            Intent it = new Intent(CarregaDados.this, MainActivity.class);
                            startActivity(it);
                            finish();
                            dialog.dismiss();
                        }

                    }
                });
    }
}
