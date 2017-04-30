package br.com.zenus.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


import br.com.zenus.prazercity.R;
import br.com.zenus.util.PermissionUtils;

import static android.content.Context.LOCATION_SERVICE;

public class MapaFrag extends Fragment {
    protected GoogleMap gMap;
    protected MapView gMapView;
    private CameraUpdate update;

    protected SupportMapFragment mapFragment;
    private Button btnExibirLocais;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = (RelativeLayout) inflater.inflate(R.layout.tab_layout_mapa, container, false);

        btnExibirLocais = (Button) view.findViewById(R.id.btnMostrarLocais);
        try {

            FragmentManager fm = getChildFragmentManager();
            mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapView);

            gMap = mapFragment.getMap();

            LocationManager service = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

            // Verifica se o GPS está ativo
            verificarGPSAtivo(service);

            solicitarPermissao();

            Location location = getLastKnownLocation();

            if (location != null) {
                LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

                mapearLocais(location);
                btnExibirLocais.setVisibility(View.GONE);
            } else {

                btnExibirLocais.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LocationManager service = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                        // Verifica se o GPS está ativo
                        verificarGPSAtivo(service);

                        Location location = getLastKnownLocation();

                        if (location != null) {
                            LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

                            mapearLocais(location);

                            btnExibirLocais.setVisibility(View.GONE);


                        } else {
                            btnExibirLocais.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }


    private void solicitarPermissao() {

        String[] permissoes = new String[]{
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION

        };
        PermissionUtils.validate(getActivity(), 0, permissoes);
    }

    private void mapearLocais(Location l) {

        if (gMap != null) {
            gMap.setMyLocationEnabled(true);

            LatLng latLng = null;
            if (l != null) {
                latLng = new LatLng(l.getLatitude(), l.getLongitude());
                CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 12);
                try {
                    MapsInitializer.initialize(getActivity());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                gMap.animateCamera(update);
                gMap.moveCamera(update);
            }

            List<LatLng> latLongitudes = new ArrayList<LatLng>();


            latLongitudes.add(new LatLng(-3.7286049, -38.5349044));
            latLongitudes.add(new LatLng(-3.744482, -38.5340377));


            gMap.clear();


            gMap.addMarker(new MarkerOptions().position(latLongitudes.get(0)).
                    title("Skalla Drinks").snippet("Aberto durante toda a semana, exceto aos domingos.")
                    .draggable(true));

            gMap.addMarker(new MarkerOptions().position(latLongitudes.get(1)).
                    title("3010").snippet("Aberto 24 Horas, exceto as segundas.")
                    .draggable(true));


            gMap.getUiSettings().setZoomControlsEnabled(true);
            gMap.getUiSettings().setCompassEnabled(true);
            gMap.getUiSettings().setIndoorLevelPickerEnabled(true);
            gMap.getUiSettings().setMapToolbarEnabled(true);
            gMap.setBuildingsEnabled(true);
            gMap.getUiSettings().setAllGesturesEnabled(true);
            gMap.getUiSettings().setMapToolbarEnabled(true);

        }
    }


    private Location getLastKnownLocation() {
        String location_context = LOCATION_SERVICE;
        LocationManager mLocationManager = (LocationManager) getContext().getSystemService(location_context);

        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private void verificarGPSAtivo(LocationManager service) {

        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Caso não esteja ativo abre um novo diálogo com as configurações para
        // realizar se ativamento
        if (!enabled) {

            final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getContext());
            alertDialog.setTitle("GPS está desativado");
            alertDialog.setMessage("Deseja ativar o gps?");
            alertDialog.setCancelable(true);
            alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            });
            alertDialog.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.show();

        }
    }


}
