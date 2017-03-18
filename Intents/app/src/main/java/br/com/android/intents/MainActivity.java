package br.com.android.intents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author Artur
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] items = new String[]{

                "Ligar para Telefone", "Discar para Telefone",
                "Enviar Email", "Enviar SMS",
                "Abrir Browser", "Mapa - Lat/Lng",
                "Mapa - Endereco", "Mapa - Rota",
                "Compartilhar", "Camera - Foto",
                "Camera Video", "Visualizar todos os contatos",
                "Visualizar Contato 1", "Selecionar Contato",
                "Intent Customizada", "Intent customizada /schema",
        };

        ListView listView = new ListView(this);
        setContentView(listView);
        listView.setOnItemClickListener(this);
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Uri uri;
        Intent intent;
        switch (position) {

            case 0:
                uri = Uri.parse("tel:987470788");
                intent = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
            case 1:
                uri = Uri.parse("tel:987470788");
                intent = new Intent(Intent.ACTION_DIAL, uri);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;

            case 2:
                intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Titulo do Email");
                intent.putExtra(Intent.EXTRA_TEXT, "Olá");
                intent.putExtra(Intent.EXTRA_EMAIL, "ttuca123@gmail.com");
                intent.setType("message/rfc/822");
                startActivity(intent);
                break;

            case 3:
                //SMS

                uri = Uri.parse("sms:987470788");
                intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "Olá");

                startActivity(intent);
                break;
            case 4:
                //BROWSER

                uri = Uri.parse("http://google.com");
                intent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(intent);
                break;

            case 5:
                //Mapa
                String GEO_URI = "geo:-25.4089185, -49.3222402";

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI));

                startActivity(intent);
                break;

            case 6:
                //Mapa
                GEO_URI = "geo:0,0?q=Estúdio Garagem";

                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI));

                startActivity(intent);
                break;

            case 7:
                String origem = "-25.4089185, -49.3222402";
                String destino = "-25.428781, -49.30925";

                //Mapa
                String rota = "http://maps.google.com/maps?f=d&saddr=" + origem + "+&daddr=" + destino + "&hl=pt";


                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rota));

                startActivity(intent);
                break;


            //Compartilhar Informações
            case 8:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);

                shareIntent.setType("text/plan");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Qualquer texto");
                startActivity(shareIntent);

                break;

            //Abrir uma camera para tirar uma foto
            case 9:
                Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(fotoIntent);

                break;
            //Abrir uma camera para gravar um video
            case 10:
                Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(videoIntent);

                break;

            //Abrir a agenda
            case 11:

                uri = Uri.parse("content://com.android.contacts/contacts");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            //Abrir uma agenda para visualizar um contato
            case 12:
                uri = Uri.parse("content://com.android.contacts/contacts/1");
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

                break;
            //Abrir uma agenda para escolher um contato
            case 13:
                uri = Uri.parse("content://com.android.contacts/contacts");
                intent = new Intent(Intent.ACTION_PICK, uri);
                startActivityForResult(intent, 13);

                break;

            //Intent com ação customizada

            case 14:

                intent = new Intent("br.com.android.intents.TESTE");
                startActivity(intent);

                break;

            default:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int codigo, int resultado, Intent it) {
        super.onActivityResult(codigo, resultado, it);


    }
}
