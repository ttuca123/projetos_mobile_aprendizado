package br.com.android.vendas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListarVendas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_vendas);
        ListView listView = (ListView) findViewById(R.id.ltw_vendas);


        SQLiteDatabase db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

        Cursor cursor = null;

        try {
            cursor= db.rawQuery("SELECT vendas._id, vendas.preco, vendas.la, vendas.lo, produtos.nome FROM vendas "
           + " INNER JOIN produtos ON produtos._id=vendas.produto", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] from = {"_id", "preco", "nome", "la", "lo"};

        int[] to = {R.id.txvId, R.id.txvPreco, R.id.txvNome, R.id.txvLa, R.id.txvLo };

        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.model_list, cursor, from, to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> ad, View view, int position, long id) {

                SQLiteCursor cursor = (SQLiteCursor) ad.getAdapter().getItem(position);

                Intent it = new Intent(getBaseContext(), MapShowActivity.class);

                it.putExtra("latitude", cursor.getDouble(cursor.getColumnIndex("la")));
                it.putExtra("longitude", cursor.getDouble(cursor.getColumnIndex("lo")));
                it.putExtra("produto", cursor.getString(cursor.getColumnIndex("nome")));
                it.putExtra("id", cursor.getInt(cursor.getColumnIndex("_id")));

                startActivity(it);

            }
        });

    }
}
