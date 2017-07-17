package br.com.android.vendas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnNovaVenda;
    Button btnListarVenda;
    Button btnReplicacao;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNovaVenda = (Button) findViewById(R.id.btnNovaVenda);
        btnListarVenda = (Button) findViewById(R.id.btnListaVenda);
        btnReplicacao = (Button) findViewById(R.id.btnReplicacao);

        SQLiteDatabase db=null;

        try {
            db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

            StringBuilder sqlProdutos = new StringBuilder();
            sqlProdutos.append("CREATE TABLE IF NOT EXISTS [produtos] (");
            sqlProdutos.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlProdutos.append(" [nome] VARCHAR(100), ");
            sqlProdutos.append(" [preco] DOUBLE(10,2));");
            db.execSQL(sqlProdutos.toString());

//            db.execSQL("INSERT INTO produtos (nome, preco) values ('Coca-Cola', '2.50')");
//            db.execSQL("INSERT INTO produtos (nome, preco) values ('Red Bull', '12.50')");


            StringBuilder sqlVendas = new StringBuilder();
            sqlVendas.append("CREATE TABLE IF NOT EXISTS [vendas] (");
            sqlVendas.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
            sqlVendas.append(" [produto] INTEGER, ");
            sqlVendas.append(" [preco] DOUBLE(10,2), ");
            sqlVendas.append(" [la] DOUBLE(10,9), ");
            sqlVendas.append(" [lo] DOUBLE(10,9));");

            db.execSQL(sqlVendas.toString());


        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            db.close();
        }



        btnNovaVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, NovaVendaActivity.class);

                startActivity(it);
            }
        });

        btnListarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ListarVendas.class);

                startActivity(it);
            }
        });

        btnReplicacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent serviceIntent = new Intent(getBaseContext(),ExportarVendasService.class);

                startService(serviceIntent);
            }
        });
    }


}
