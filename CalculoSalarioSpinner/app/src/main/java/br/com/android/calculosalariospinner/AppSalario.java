package br.com.android.calculosalariospinner;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AppSalario extends
        Activity {

    private static final String[] percentual = {"De 40%", "De 45%", "De 50%"};

    ArrayAdapter<String> aPercentual;
    Spinner spnSal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_salario);

        Button btnMostrar = (Button) findViewById(R.id.btnCalcular);

        aPercentual = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, percentual);
        spnSal = (Spinner) findViewById(R.id.spnOpcoes);

        spnSal.setAdapter(aPercentual);


        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double salario=0, novoSal =0;

               EditText edSalario = (EditText) findViewById(R.id.edtSalario);

               if(!edSalario.getText().toString().isEmpty()) {
                   salario = Double.parseDouble(edSalario.getText().toString());
               }
                switch (spnSal.getSelectedItemPosition()){

                    case 0:

                        novoSal = salario +(salario*0.40);
                        break;

                    case 1:

                        novoSal = salario +(salario*0.45);
                        break;

                    case 2:

                        novoSal = salario +(salario*0.50);
                        break;
                }

                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(AppSalario.this);
                dialogo.setTitle("Novo salário");
                dialogo.setMessage("Seu novo salário é : R$" +
                        String.valueOf(novoSal));
                dialogo.setNeutralButton("OK", null);
                dialogo.show();


            }
        });
    }
}
