package br.com.android.vendas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

        StringBuilder sqlProdutos = new StringBuilder();
        sqlProdutos.append("CREATE TABLE IF NOT EXISTS [produtos] (");
        sqlProdutos.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlProdutos.append(" [nome] VARCHAR(100), ");
        sqlProdutos.append(" [preco] DOUBLE(10,2));");
        db.execSQL(sqlProdutos.toString());

        StringBuilder sqlVendas = new StringBuilder();
        sqlProdutos.append("CREATE TABLE IF NOT EXISTS [vendas] (");
        sqlProdutos.append("[_id] INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sqlProdutos.append(" [produto] INTEGER, ");
        sqlProdutos.append(" [preco] DOUBLE(10,2), ");
        sqlProdutos.append(" [la] DOUBLE(10,9), ");
        sqlProdutos.append(" [lo] DOUBLE(10,9));");

        db.execSQL(sqlVendas.toString());
    }
}
