package br.com.zenus.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.zenus.EnuServicos;
import br.com.zenus.entidades.AtualizacaoLocal;
import br.com.zenus.entidades.AtualizacaoLocalDao;
import br.com.zenus.entidades.DaoSession;
import br.com.zenus.entidades.Local;
import br.com.zenus.vo.AtualizacaoLocalVO;
import br.com.zenus.vo.LocalMasterVO;
import br.com.zenus.fragments.DetalheLocal;
import br.com.zenus.prazercity.R;
import br.com.zenus.util.App;
import br.com.zenus.util.PermissionUtils;
import br.com.zenus.util.Utilitarios;

public class ActMaps extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private InterstitialAd mInterstitialAd;

    private AdView mAdView;
    protected GoogleMap mMap;
    private DaoSession daoSession;
    private int ZOOM = 13;
    protected GoogleApiClient mGoogleAPIClient;
    private SupportMapFragment mapFragment;
    private LocationManager service;
    private List<Local> locais;
    private ProgressDialog progressDialog;
    Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_maps);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleAPIClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleAPIClient.connect();

        daoSession = ((App) getApplication()).getDaoSession();

        LocationManager service = (LocationManager) getBaseContext().getSystemService(LOCATION_SERVICE);

        this.service = service;

        solicitarPermissao();

        verificarAtualizacao();

        inicializarPropaganda();

    }




    @Override
    protected void onResume() {
        super.onResume();

        auxMapearLocais();


    }


    private void inicializarPropaganda(){



        mInterstitialAd = new InterstitialAd(ActMaps.this);
        mInterstitialAd.setAdUnitId(Utilitarios.ADMOB_UNIC2);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private Location getLastKnownLocation() {
        String location_context = LOCATION_SERVICE;
        LocationManager mLocationManager = (LocationManager) getBaseContext().getSystemService(location_context);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);

            if (l == null) {
                continue;
            }
            if (bestLocation == null
                    || l.getAccuracy() < bestLocation.getAccuracy()) {

                bestLocation = l;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }


    @Override
    public void onConnected(Bundle bundle) {
        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 10000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 10000); // 1 second, in milliseconds

        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleAPIClient, mLocationRequest, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleAPIClient != null) {
            mGoogleAPIClient.connect();
        }

    }

    @Override
    protected void onStop() {
        if (mGoogleAPIClient != null) {
            mGoogleAPIClient.disconnect();
        }
        super.onStop();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_NETWORK_STATE, android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION

        };
        PermissionUtils.validate(this, 0, permissoes);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        solicitarPermissao();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }


        if (myLocation == null) {
            myLocation = getLastKnownLocation();
        }

        if (myLocation != null) {

            atualizarLocalizacao(myLocation);

            mapearLocais();
        }


    }


    private boolean verificarGPSAtivo(LocationManager service) {

        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Caso não esteja ativo abre um novo diálogo com as configurações para
        // realizar se ativamento
        if (!enabled) {

            LayoutInflater factory = LayoutInflater.from(getApplicationContext());
            View view = factory.inflate(
                    R.layout.alert_gps, null);

            final AlertDialog dialog = new AlertDialog.Builder(getBaseContext(), R.style.dialogo).create();
            dialog.setView(view);
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            Button btnAtivarGPS = (Button) view.findViewById(R.id.btnAtivarGPS);
            Button btnCancelarGPS = (Button) view.findViewById(R.id.btnCancelarGPS);

            btnAtivarGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                    startActivity(intent);

                }
            });

            btnCancelarGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        return enabled;
    }

    private void carregarDados() {

        progressDialog = ProgressDialog.show(this, "Atualizando Locais",
                "Carregando", false, true);

        Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.LOCAIS))
                .as(JsonObject.class)
                .setCallback(new FutureCallback<JsonObject>() {

                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result != null) {
                            Gson gson = new Gson();

                            LocalMasterVO localMasterVO = null;

                            localMasterVO = gson.fromJson(result, LocalMasterVO.class);

                            locais = localMasterVO.getLocais();

                            daoSession.deleteAll(Local.class);

                            for (Local local : locais) {

                                daoSession.getLocalDao().insert(local);
                            }

                            auxMapearLocais();

                            mapearLocais();

                            Toast.makeText(getBaseContext(), "Dados atualizados!", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getBaseContext(), "Erro!", Toast.LENGTH_LONG).show();
                        }


                        progressDialog.dismiss();

                    }
                });

    }

    private void conectarNet(LocationManager service) {

        if (!Utilitarios.verificarConexao(getBaseContext())) {
            LayoutInflater factory = LayoutInflater.from(getApplicationContext());
            View view = factory.inflate(
                    R.layout.alert_internet, null);

            final AlertDialog dialog = new AlertDialog.Builder(getBaseContext(), R.style.dialogo).create();
            dialog.setView(view);
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
            Button btnAtivarGPS = (Button) view.findViewById(R.id.btnAtivarNET);
            Button btnCancelarGPS = (Button) view.findViewById(R.id.btnCancelarNET);

            btnAtivarGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);

                    startActivity(intent);

                }
            });

            btnCancelarGPS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        myLocation = location;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent it;

        switch (id) {
            case R.id.action_about:

                it = new Intent(ActMaps.this, SobreActivity.class);
                startActivity(it);

                break;
            case R.id.action_atualize:


                verificarAtualizacao();
                conectarNet(service);

                if (verificarGPSAtivo(this.service)) {

                    Location location = getLastKnownLocation();

                    atualizarLocalizacao(location);

                    mapearLocais();
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void atualizarLocalizacao(Location location) {

        if (location != null) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, ZOOM);

            mMap.animateCamera(update);
            mMap.moveCamera(update);
        }

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void mapearLocais() {

        if (mMap != null) {

            mMap.setMyLocationEnabled(true);

            mMap.clear();

            locais = new ArrayList<Local>();

            locais = daoSession.getLocalDao().loadAll();

            DecimalFormat df = new DecimalFormat("0.00");

            for (Local local : locais) {

                LatLng latLongitude = new LatLng(local.getLatitude(), local.getLongitude());

                mMap.addMarker(new MarkerOptions().position(latLongitude).
                        title(local.getNome()).snippet(local.getDescricao() + " - Nota: " + df.format(local.getAvaliacao()))
                        .draggable(true));
            }

            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.setBuildingsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);

            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {

                    mInterstitialAd.show();



                    Intent it = new Intent(ActMaps.this, DetalheLocal.class);
                    Bundle bundle = new Bundle();


                    bundle.putString("nome", marker.getTitle());
                    it.putExtras(bundle);
                    startActivity(it);
                }
            });

        }
    }


    public void verificarAtualizacao() {

            progressDialog = ProgressDialog.show(this, "Verificando Novos Dados",
                    "Carregando", false, true);

            Ion.with(getBaseContext()).load(Utilitarios.acessarServico(EnuServicos.ATUALIZACAO_LOCAL))
                    .as(JsonObject.class)
                    .setCallback(new FutureCallback<JsonObject>() {


                        @Override
                        public void onCompleted(Exception e, JsonObject result) {


                            if (result != null) {
                                Gson gson = new Gson();

                                AtualizacaoLocalVO atualizacaoLocalVO = null;

                                try {
                                    atualizacaoLocalVO = gson.fromJson(result, AtualizacaoLocalVO.class);
                                } catch (JsonSyntaxException e1) {
                                    e1.printStackTrace();
                                }


                                AtualizacaoLocal atualizacaoLocal = new AtualizacaoLocal();

                                atualizacaoLocal.setSeqAtualizacao(atualizacaoLocalVO.getSeqAtualizacao());

                                atualizacaoLocal.setCodigoHash(atualizacaoLocalVO.getCodigoHash());

                                atualizacaoLocal.setDtAtualizacao(atualizacaoLocalVO.getDtAtualizacao());


                                Long contadorAtualizacaoDados = daoSession.getAtualizacaoLocalDao().queryBuilder()
                                        .where(AtualizacaoLocalDao.Properties.CodigoHash.
                                                eq(atualizacaoLocal.getCodigoHash())).count();

                                progressDialog.dismiss();
                                progressDialog=null;

                                if (contadorAtualizacaoDados == 0L) {

                                    daoSession.getAtualizacaoLocalDao().insert(atualizacaoLocal);

                                    carregarDados();

                                }

                            } else {


                                progressDialog.dismiss();
                            }

                        }
                    });

    }


    private void auxMapearLocais() {

        if (!verificarGPSAtivo(service)) {


            if (myLocation == null) {
                myLocation = getLastKnownLocation();
            }


            if (myLocation != null) {

                atualizarLocalizacao(myLocation);

                mapearLocais();
            }
        }

    }


}
