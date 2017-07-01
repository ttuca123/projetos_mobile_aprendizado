package br.com.zenus.fragments;


import android.annotation.TargetApi;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import br.com.zenus.entidades.DaoSession;
import br.com.zenus.entidades.Local;
import br.com.zenus.entidades.LocalDao;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.PermissionUtils;
import br.com.zenus.util.Utilitarios;


public class DetalheLocal
        extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private TextView telefone;
    private TextView txtNota;

    private RatingBar ratingBar;

    protected GoogleMap gMap;
    protected SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleAPIClient;
    private ActionBar actionBar;

    private AdView mAdView;

    private DaoSession daoSession;

    private Double seqLocal;
    private Double latitude=0.0;
    private Double longitude=0.0;
    private Float media;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detalhe_local);

        inicializarElementosGraficos();

        preencherDadosTela();

        inicializarPropaganda();

    }



    @TargetApi(Build.VERSION_CODES.N)
    private void preencherDadosTela(){
        Bundle params = getIntent().getExtras();

        if (params != null) {
            String nome = params.getString("nome");

            daoSession = ((App) getApplication()).getDaoSession();

            Local local = daoSession.getLocalDao().queryBuilder().where(LocalDao.Properties.Nome.eq(nome)).unique();

            seqLocal = local.getSeqLocal().doubleValue();

            latitude = local.getLatitude();
            longitude = local.getLongitude();

            media = local.getAvaliacao();

            DecimalFormat df = new DecimalFormat("0.00");

            txtNota.setText(df.format(local.getAvaliacao()).toString());


            mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapDetail);
            mapFragment.getMapAsync(this);

            gMap = mapFragment.getMap();
            mGoogleAPIClient = new GoogleApiClient.Builder(this).
                    addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
            LatLng latLng = new LatLng(latitude, longitude);

            mapearLocal(latLng);

            if (actionBar != null) {

                actionBar.setTitle(nome);

                actionBar.setDisplayHomeAsUpEnabled(true);
            }

        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float aval, boolean fromUser) {

               Double avaliacao = Double.parseDouble(String.valueOf(aval));

                enviarAvaliacao(avaliacao);
            }
        });
    }

    private void inicializarPropaganda(){

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(Utilitarios.ADMOB_UNIC);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


    }


    private void inicializarElementosGraficos(){

        actionBar = getSupportActionBar();

        ratingBar = (RatingBar) findViewById(R.id.rtAvaliacao);
        txtNota = (TextView) findViewById(R.id.txtNota);

    }

    private void enviarAvaliacao(final Double avaliacao){
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


    private void mapearLocal(LatLng latLng) {

        if (gMap != null) {

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);

            MapsInitializer.initialize(this);

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

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient, mLocationRequest, this);

    }

    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION
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
