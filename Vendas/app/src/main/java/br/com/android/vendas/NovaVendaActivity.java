package br.com.android.vendas;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;

import util.PermissionsUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

public class NovaVendaActivity extends AppCompatActivity implements LocationListener {

    Button btnSalvar;

    private double lo;
    private double la;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_venda);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        final Spinner spProdutos = (Spinner) findViewById(R.id.spnProduto);

        SQLiteDatabase db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

        Cursor cursor = db.rawQuery("SELECT * FROM produtos ORDER BY nome ASC", null);

        String[] from = {"_id", "nome", "preco"};

        int[] to = {R.id.txvId, R.id.txvNome, R.id.txvPreco};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(), R.layout.spinner, cursor, from, to);

        spProdutos.setAdapter(adapter);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                solicitarPermissao();

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();
                String provider = locationManager.getBestProvider(criteria, false);

                Location location = null;

                try {
                    if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    location = locationManager.getLastKnownLocation(provider);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(location!=null) {
                   la = location.getLatitude();
                   lo = location.getLongitude();
               }


                SQLiteDatabase db = openOrCreateDatabase("vendas.db", Context.MODE_PRIVATE, null);

                SQLiteCursor dados = (SQLiteCursor) spProdutos.getAdapter().getItem(spProdutos.getSelectedItemPosition());


                ContentValues ctv = new ContentValues();
                ctv.put("produto", dados.getInt(0));
                ctv.put("preco", dados.getDouble(2));
                ctv.put("la", la);
                ctv.put("lo", lo);

               if(db.insert("vendas", "_id", ctv)>0){

                   Toast.makeText(getBaseContext(), "Venda Salva com sucesso", Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(getBaseContext(), "Erro ao Cadastrar venda", Toast.LENGTH_SHORT).show();
               }

                db.close();




            }
        });
    }

    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
        };
        PermissionsUtil.validate(this, 0, permissoes);

    }

    @Override
    public void onLocationChanged(Location location) {

        la= location.getLatitude();
        lo = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
