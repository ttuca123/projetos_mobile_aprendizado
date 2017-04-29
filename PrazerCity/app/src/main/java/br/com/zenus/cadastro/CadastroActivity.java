package br.com.zenus.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.zenus.main.MainActivity;
import br.com.zenus.prazercity.R;


public class CadastroActivity extends AppCompatActivity {

    Button btnCancelar;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCadastro);
        setSupportActionBar(toolbar);

        btnCancelar = (Button) findViewById(R.id.btnCancelarCadastro);
        btnSalvar = (Button) findViewById(R.id.btnSalvarCadastro);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CadastroActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "O objeto será salvo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_cadastro, menu);

        return true;
    }

}
