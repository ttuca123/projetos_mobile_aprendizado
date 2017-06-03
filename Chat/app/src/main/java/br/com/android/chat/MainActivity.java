package br.com.android.chat;

import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class MainActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginSenha;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        loginEmail = (EditText) findViewById(R.id.txtLoginEmail);
        loginSenha = (EditText) findViewById(R.id.txtLoginSenha);
        Button btnLogar = (Button) findViewById(R.id.btnLogar);

        FloatingActionButton btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(it);
            }
        });



        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String URL = "http://192.168.25.9/chat/login_user.php";

                if (loginEmail.getText().toString().isEmpty()) {

                    loginEmail.setError("Preencha o campo email");
                    loginEmail.requestFocus();
                } else if (loginSenha.getText().toString().isEmpty()) {
                    loginSenha.setError("Preencha o campo senha");
                    loginSenha.requestFocus();
                }else {


                    try {
                        Ion.with(getBaseContext()).load(URL)
                                .setBodyParameter("email_user", loginEmail.getText().toString())
                                .setBodyParameter("senha_user", loginSenha.getText().toString())
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {

                                        if (result != null) {

                                            if (result.get("retorno").getAsInt()>0) {

                                                SharedPreferences.Editor preferences = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE).edit();
                                                preferences.putInt("id_usuario", result.get("retorno").getAsInt());
                                                preferences.putString("nome_usuario", result.get("nome_usuario").getAsString());
                                                preferences.putString("email_usuario", result.get("email_usuario").getAsString());
                                                preferences.putString("photo_usuario", result.get("photo_usuario").getAsString());
                                                preferences.commit();
                                                //Abrir a Tela Principal

                                                showChat();

                                                Toast.makeText(getBaseContext(), "Logado ", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getBaseContext(), "Login / Senha Inválido", Toast.LENGTH_LONG).show();

                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "Erro ao realizar login.", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                });
                    } catch (Exception ex) {
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showChat();

    }

    private void showChat(){
        SharedPreferences preferences = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE);

        if(preferences.getInt("id_usuario", 0)>0){

            Intent it = new Intent(MainActivity.this, ChatActivity.class);

            startActivity(it);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
