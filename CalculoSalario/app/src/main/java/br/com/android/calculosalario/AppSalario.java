package br.com.android.calculosalario;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AppSalario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_salario);

        Button btnCalcular = (Button) findViewById(R.id.btnCalcular);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double salario;
                Double novoSalario = 0.0;

                EditText edSalario = (EditText) findViewById(R.id.edtSalario);

                salario = Double.parseDouble(edSalario.getText().toString());

                RadioGroup rg = (RadioGroup) findViewById(R.id.rgopcoes);

                int op = rg.getCheckedRadioButtonId();

                switch (op){

                    case R.id.rb40:
                            novoSalario = salario + (salario*0.40);
                        break;
                    case R.id.rb45:
                        novoSalario = salario + (salario*0.45);
                        break;
                    case R.id.rb50:
                        novoSalario = salario + (salario*0.50);
                        break;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(AppSalario.this);
                dialog.setTitle("Novo Salário");
                dialog.setMessage("Seu novo salário é: R$"+String.valueOf(novoSalario));
                dialog.setNeutralButton("OK", null);

                dialog.show();
            }
        });

    }
}
