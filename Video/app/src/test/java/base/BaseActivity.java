package base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.Toast;

import br.com.zenus.activity.ActAbout;
import br.com.zenus.activity.ActConfiguracao;
import br.com.zenus.activity.ActEvento;
import br.com.zenus.activity.R;
import br.com.zenus.dao.EntityDao;
import br.com.zenus.entity.DaoSession;

/**
 * Created by Tuca on 06/01/2017.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Menu menu;

    protected static int SPLASH_TIME_OUT = 3000;

    protected ActionBar actionBar;

    protected ProgressDialog dialog;

    protected abstract void setUpToolBar();

    protected DaoSession daoSession;

    protected EntityDao entityDao;

    public static final String API_URL = "http://192.168.25.9:8180/zenus";


    protected abstract void initializeElements();

    protected void irParaActivity(Class<?> clazze) {

        Intent intent = new Intent(getBaseContext(), clazze);
        startActivity(intent);
    }

    protected void toast(String query) {

        Toast toast = Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT);

        toast.show();
    }

    protected boolean verificarAcaoMenu(int id) {

        boolean condicao = false;

        switch (id) {
            case R.id.action_configuracoes:
                irParaActivity(ActConfiguracao.class);
                condicao = true;
                break;
            case R.id.action_buscar:

                toast("Clicou em Pesquisar");
                condicao = true;
                break;
            case R.id.action_evento:
                irParaActivity(ActEvento.class);
                break;

            case R.id.action_sobre:
                irParaActivity(ActAbout.class);
                break;

            default:
        }


        return condicao;


    }
}