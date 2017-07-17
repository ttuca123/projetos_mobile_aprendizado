package br.com.android.chat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import br.com.android.chat.dao.EntityDao;


import util.App;

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar;

    private EntityDao entityDao;
    private DaoSession daoSession;

    private TextView txtNome;
    private TextView txtEmail;
    private TextView txtSenha;
    private TextView txtConfirmaSenha;
    private ProgressDialog dialog;


    private int REQUEST_DIALOG_PHOTO = 1;
    private int error = 0;
    File outPutFile;
    File diretorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = (TextView) findViewById(R.id.txtNomeCadastro);
        txtEmail = (TextView) findViewById(R.id.txtEmailCadastro);
        txtSenha = (TextView) findViewById(R.id.txtSenhaCadastro);
        txtConfirmaSenha = (TextView) findViewById(R.id.txtConfSenha);


        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

         final ImageView imgFoto = (ImageView) findViewById(R.id.imgFoto);

        imgFoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                imgFoto.setImageResource(android.R.color.transparent);

                Intent camera = ImagePicker.getPickImageIntent(getBaseContext());

                startActivityForResult(camera, REQUEST_DIALOG_PHOTO);

                return false;
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entityDao = new EntityDao();
        daoSession = ((App) getApplication()).getDaoSession();

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String URL = "http://192.168.25.9/projetos_web_aprendizado/chat/insert_user.php";
                 String photoFile = null;

                try {
                    photoFile = getBaseContext().getPackageManager().getPackageInfo(getBaseContext().getPackageName(), 0).applicationInfo.dataDir+"//photo//perfil.png";
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                String nome ;
                 String email;
                 String senha;
                 String confirmaSenha;

                nome = txtNome.getText().toString();
                email = txtEmail.getText().toString();
                senha = txtSenha.getText().toString();
                confirmaSenha = txtConfirmaSenha.getText().toString();

                if(nome.equals("")){

                    txtNome.setError("Preencha o campo nome");
                    txtNome.requestFocus();
                    error=1;
                }else if(email.equals("")){

                    txtEmail.setError("Preencha o campo email");
                    txtEmail.requestFocus();
                    error=1;
                } else if(senha.equals("")){

                    txtSenha.setError("Preencha o campo senha");
                    txtSenha.requestFocus();
                }else   if(!senha.equals(confirmaSenha)){
                    String mensagem = "Favor verificar se as senhas estão iguais";
                    txtSenha.setError(mensagem);
                    txtConfirmaSenha.setError(mensagem);
                    txtSenha.requestFocus();
                    error=1;

                }else {


                    try {
                        Ion.with(getBaseContext()).load(URL)
                                .setMultipartParameter("nome_user", nome)
                                .setMultipartParameter("email_user", email)
                                .setMultipartParameter("senha_user", senha)
                                .setMultipartFile("photo_user", outPutFile)
                                .asJsonObject()
                                .setCallback(new FutureCallback<JsonObject>() {
                                    @Override
                                    public void onCompleted(Exception e, JsonObject result) {

                                        if(result != null) {

                                            if (result.get("retorno").getAsString().equals("YES")) {


                                                Toast.makeText(getBaseContext(), "Cadastro realizado com sucesso.", Toast.LENGTH_LONG).show();
                                            } else if (result.get("retorno").getAsString().equals("NO")) {
                                                Toast.makeText(getBaseContext(), "Erro ao realizar cadastro.", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getBaseContext(), "Email já existe.", Toast.LENGTH_LONG).show();
                                            }
                                        }else{
                                            Toast.makeText(getBaseContext(), "Erro ao realizar cadastro.", Toast.LENGTH_LONG).show();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_DIALOG_PHOTO){


               Bitmap photoUser = ImagePicker.getImageFromResult(getBaseContext(), resultCode, data);

               ImageView imgFoto = (ImageView) findViewById(R.id.imgFoto);

                imgFoto.setImageBitmap(photoUser);

                diretorio = Environment.getDataDirectory();

                String path = "//data//"+getBaseContext().getPackageName()+"//photo//";

                diretorio = new File(diretorio, path);
               if(!diretorio.exists()) {
                   diretorio.mkdirs();
               }
               diretorio.getAbsolutePath();
                OutputStream out = null;

                outPutFile = new File(diretorio, "perfil.png");

                try {
                    out = new FileOutputStream(outPutFile);
                    photoUser.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(getBaseContext(), "Por favor, selecione uma foto", Toast.LENGTH_LONG);

            }

    }

    private class CadastroTask extends AsyncTask<Void, Void, Integer> {

            private UsuarioDao usuarioDao;
            private Usuario usuario;

        public static final String URL = "http://192.168.25.9/chat/insert_user.php";


        private String nome ;
        private String email;
        private String senha;

        @Override
        protected void onPreExecute() {

            nome = txtNome.getText().toString();
            email = txtEmail.getText().toString();
            senha = txtSenha.getText().toString();
        }


        @Override
        protected Integer doInBackground(Void... voids) {

             int status = 0;
            try {
                Ion.with(getBaseContext()).load(URL)
                        .setBodyParameter("nome_user", nome)
                        .setBodyParameter("email_user", email)
                        .setBodyParameter("senha_user", senha)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {

                                if(result!=null && result.get("retorno").equals("YES"))
                                {


                                    Toast.makeText(getBaseContext(),"Cadastro realizado com sucesso.", Toast.LENGTH_LONG);
                                }else {
                                    Toast.makeText(getBaseContext(), "Erro ao realiar cadastro.", Toast.LENGTH_LONG);
                                }
                            }

                        });
                status=1;
            } catch (Exception e) {

                status = 0;
                e.printStackTrace();

            }finally {
                dialog.dismiss();
                return status;
            }
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);



        }
    }

    private void erroConexao(){

        AlertDialog.Builder dialog;

        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Falha de conexão do servidor");
        dialog.setMessage("Não foi possível estabelecer conexão com o servidor remoto");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", null);
        dialog.show();


    }

    private void cadastroSucesso(){

        AlertDialog.Builder dialog;

        dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Sucesso");
        dialog.setMessage("Cadastro realizado com sucesso!!!");
        dialog.setCancelable(false);
        dialog.setPositiveButton("OK", null);
        dialog.show();


    }


}
