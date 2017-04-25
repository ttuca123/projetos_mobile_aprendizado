package br.com.zenus.fragments;

import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import br.com.zenus.prazercity.R;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

//        createLista();
    }

//    private void createLista(){
//
//        ListView listView = (ListView) findViewById(R.id.lista);
//
//
//        String[] from = {"Teste", "teste2"};
//
//        int[] toViews = {R.id.txtNome, R.id.txtClassif};
//
//        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.local_item,null,
//                from, toViews,0 );
//
//        listView.setAdapter(adapter);
//    }


}
