package br.com.zenus.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import br.com.zenus.EnuServicos;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.PermissionUtils;
import br.com.zenus.util.Utilitarios;

public class DetalheLocal extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    TextView txtNome;

    TextView telefone;
    TextView txtNota;
    Double latitude;
    Double longitude;
    Double avaliacao;
    Double media;
    ImageButton btnLigar;
    Intent intent;
    RatingBar ratingBar;
    private Double seqLocal;

    private static final String ADMOB_UNIC = "ca-app-pub-5459227351754314/1383857388";

    protected GoogleMap gMap;
    protected SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleAPIClient;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detalhe_local);
        intent = getIntent();

        Bundle params = intent.getExtras();

        ratingBar = (RatingBar) findViewById(R.id.rtAvaliacao);

        ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {

            actionBar.setTitle("Detalhes");

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        txtNome = (TextView) findViewById(R.id.txtViewNome);
        telefone = (TextView) findViewById(R.id.txtViewFone);
        txtNota = (TextView) findViewById(R.id.txtNota);
        btnLigar = (ImageButton) findViewById(R.id.btnFone);

        if (params != null) {
            seqLocal = params.getDouble("seqLocal");
            txtNome.setText("Nome: "+params.getString("nome"));
            telefone.setText("Fone: "+params.getString("telefone"));
            latitude = params.getDouble("latitude");
            longitude = params.getDouble("longitude");
            avaliacao = params.getDouble("avaliacao");
            media = params.getDouble("media");
            txtNota.setText("Nota: "+avaliacao.toString());


            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDetail);
            mapFragment.getMapAsync(this);

            gMap = mapFragment.getMap();
            mGoogleAPIClient = new GoogleApiClient.Builder(this).
                    addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            LatLng local = new LatLng(latitude, longitude);

            mapearLocais(local);

        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float aval, boolean fromUser) {

                avaliacao = Double.parseDouble(String.valueOf(aval));

                if (avaliacao > 0) {

                    Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.AVALIAR))
                            .setMultipartParameter("seqLocal", seqLocal.toString())
                            .setMultipartParameter("aval", avaliacao.toString())
                            .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            if (result != null && !result.equals("YES")) {

                                Toast.makeText(getBaseContext(), "Avaliação " + avaliacao + " enviada com sucesso!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getBaseContext(), "Erro ao enviar avaliação!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }
            }
        });


        final Activity activity = this;

        btnLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissoes = new String[]{
                        Manifest.permission.CALL_PHONE
                };

                if (PermissionUtils.validate(activity, 0, permissoes)) {
                    Uri uri = Uri.parse("tel:" + telefone.getText());
                    intent = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(getBaseContext(), "Permissão não concedida!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(ADMOB_UNIC);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void mapearLocais(LatLng latLng) {

        if (gMap != null) {


            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            try {
                MapsInitializer.initialize(this);
            } catch (Exception e) {
                e.printStackTrace();
            }


            gMap.animateCamera(update);
            gMap.moveCamera(update);


            gMap.clear();

            gMap.addMarker(new MarkerOptions().position(latLng));

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleAPIClient.connect();


    }

    @Override
    protected void onStop() {

        mGoogleAPIClient.disconnect();
        super.onStop();

    }


    @Override
    public void onConnected(Bundle bundle) {

        solicitarPermissao();

        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient, mLocationRequest, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE
        };
        PermissionUtils.validate(this, 0, permissoes);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
