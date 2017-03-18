package br.com.android.gps;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {


    private static final String TAG = "Livro Android";
    protected GoogleMap map;
    protected SupportMapFragment mapFragment;
    protected GoogleApiClient mGoogleAPIClient;

    private ImageView btnZoomIn;
    private ImageView btnZoomOut;
    private CameraUpdate update;
    private String localizacaoOrigem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnZoomIn = (ImageView) findViewById(R.id.img_btn_plus);
        btnZoomOut = (ImageView) findViewById(R.id.img_btn_minus);

        mGoogleAPIClient = new GoogleApiClient.Builder(this).
                addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();



        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.moveCamera(CameraUpdateFactory.zoomIn());

            }
        });

        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                map.moveCamera(CameraUpdateFactory.zoomOut());


            }
        });

    }


    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };
        PermissionsUtil.validate(this, 0, permissoes);
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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


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
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleAPIClient);

        if (location != null) {
            switch (item.getItemId()) {

                case R.id.action_my_location:

                    setMapLocation(location);

                    return true;
                case R.id.action_my_rota:


                    localizacaoOrigem = location.getLatitude() + ", " + location.getLongitude();

                    String destino = "-3.771784, -38.535286";

                    String url = "http://maps.google.com/maps?f=d&saddr=" + localizacaoOrigem + "+&daddr=" + destino + "&hl=pt";

                    startActivity(new Intent((Intent.ACTION_VIEW), Uri.parse(url)));
                    return true;
                case R.id.action_estudio:

                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

                    //Estudio Zalem
                    LatLng latLngZalem = new LatLng(-3.764408, -38.553227);



                    map.setMyLocationEnabled(true);
                    update = CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 12);
                    map.animateCamera(update);
                    map.moveCamera(update);

                    map.addMarker(new MarkerOptions().position(minhaLocalizacao).
                            title("Minha Casa").snippet("Minha casa"));

                    map.addMarker(new MarkerOptions().position(latLngZalem).
                            title("Estúdio Zalem").snippet("Estúdio com ótimo som e com excelente qualidade")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_estudio)));

                    PolylineOptions line = new PolylineOptions();

                    line.add(minhaLocalizacao);

                    line.add(latLngZalem);

                    line.color(Color.RED);

                    Polyline polyline = map.addPolyline(line);
                    polyline.setGeodesic(true);

                    //map.addCircle(new CircleOptions().center(latLngZalem).visible(true).radius(15).fillColor(Color.BLUE));



                    map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            Toast.makeText(getBaseContext(),"Teste Zalem", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });





                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void setMapLocation(Location l) {

        if (map != null && l != null) {
            LatLng latLng = new LatLng(l.getLatitude(), l.getLongitude());
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);

            map.animateCamera(update);
            TextView textView = (TextView) findViewById(R.id.text);

            textView.setText(String.format("Minha Localização é: " +
                    "Lat/Lnt %f/%f, provider: %s", l.getLatitude(), l.getLongitude(), l.getProvider()));

            CircleOptions circle = new CircleOptions().center(latLng).radius(100);
            circle.fillColor(Color.RED);
            circle.radius(25);
            map.clear();
            map.addCircle(circle);

        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
