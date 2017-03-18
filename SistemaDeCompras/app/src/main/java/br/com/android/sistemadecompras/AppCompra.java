package br.com.android.sistemadecompras;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class AppCompra extends Activity {

    CheckBox chkArroz;

    CheckBox chkLeite;

    CheckBox chkCarne;

    CheckBox chkFeijao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_compra);


        chkArroz = (CheckBox) findViewById(R.id.chkArroz);
        chkLeite = (CheckBox) findViewById(R.id.chkLeite);
        chkCarne = (CheckBox) findViewById(R.id.chkCarne);
        chkFeijao = (CheckBox) findViewById(R.id.chkFeijao);

        Button btnTotal = (Button) findViewById(R.id.btnTotal);

        btnTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float total =0;

                if(chkArroz.isChecked()){
                    total+=2.69;
                }
                if(chkLeite.isChecked()){
                    total+=5.00;
                }
                if(chkCarne.isChecked()){
                    total+=9.70;
                }
                if(chkFeijao.isChecked()){
                    total+=2.30;
                }

                AlertDialog.Builder dialogo = new AlertDialog.Builder(AppCompra.this);

                dialogo.setTitle("Aviso");

                dialogo.setMessage("Valor total da compra: "+String.valueOf(total));
                dialogo.setNeutralButton("OK", null);
                dialogo.show();

            }
        });
    }
}
