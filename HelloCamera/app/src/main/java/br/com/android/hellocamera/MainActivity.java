package br.com.android.hellocamera;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    private ImageView imgView;

    private File file;
    private static final int CAPTURA_IMAGEM=1;
    private Uri uri;
    String nomeImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = (ImageView) findViewById(R.id.imagem);
        ImageButton b = (ImageButton) findViewById(R.id.btnCamera);


        solicitarPermissao();


       // Picasso.with(getBaseContext()).load("http://i.imgur.com/DvpvklR.png").into(imgView);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                File diretorio = getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                 nomeImagem = diretorio.getPath() +"/servico/foto"+Math.random()+".jpg";

                uri = Uri.fromFile(new File(nomeImagem));

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, 0);
            }
        });

        if(savedInstanceState != null){
            file = (File) savedInstanceState.getSerializable("file");
            showImage(file);
        }

    }

    private void showImage(File file){

        if(file!=null && file.exists()){
            int w = imgView.getWidth();
            int h = imgView.getHeight();


        }

    }

    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                Manifest.permission.CAMERA,  Manifest.permission.INTERNET
        };
        PermissionsUtil.validate(this, 0, permissoes);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Picasso.with(getBaseContext()).load(nomeImagem).resize(50, 50).centerCrop().into(imgView);
        Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);

        this.sendBroadcast(intent);

        visualizarImagem();


//        if(data!= null){
//            String url = "http://i.imgur.com/DvpvklR.png";
//
//            Bundle bundle = data.getExtras();
//            if(bundle != null){
//                Bitmap bitMap = (Bitmap) bundle.get("data");
//
//                imgView.setImageBitmap(bitMap);
//            }
//        }
    }


    private void visualizarImagem(){

        Intent intent =new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "image/jpeg");
        startActivity(intent);
    }
}
