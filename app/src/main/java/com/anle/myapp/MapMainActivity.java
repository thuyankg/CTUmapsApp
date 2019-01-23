package com.anle.myapp;

import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
// classes needed to initialize map
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.maps.MapView;
// classes needed to add location layer
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import android.location.Location;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.mapbox.mapboxsdk.geometry.LatLng;
import android.support.annotation.NonNull;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
// classes needed to add a marker
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
// classes to calculate a route
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
// classes needed to launch navigation UI
import android.view.View;
import android.widget.Button;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;

public class MapMainActivity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener, LocationEngineListener, PermissionsListener {
    private MapView mapView;
    // variables for adding location layer
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationLayerPlugin;
    private LocationEngine locationEngine;
    private Location originLocation;
    // variables for adding a marker
    private Marker destinationMarker;
    private LatLng originCoord;
    private LatLng destinationCoord;
    // variables for calculating and drawing a route
    private Point originPosition;
    private Point destinationPosition;
    private DirectionsRoute currentRoute;
    private static final String TAG = "DirectionsActivity";
    private NavigationMapRoute navigationMapRoute;
    private Button button;
    private Button openLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_map_main);
        //set Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        mapView = findViewById(R.id.mapView);
        //mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //AutoCompleteText
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoText);
        ImageView imageAuto = findViewById(R.id.image);
        Button search = findViewById(R.id.searchAndress);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MapMainActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.nameAddress));
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        autoCompleteTextView.setAdapter(adapter);
        imageAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoCompleteTextView.showDropDown();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = autoCompleteTextView.getText().toString();
                originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());
                double lat=0;
                double lng=0;
                switch (str) {
                    case "/A3: Nhà học A3":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.030607;
                        lng = 105.771797;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("A3")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/B1: Nhà học B1":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.031571;
                        lng = 105.767849;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("B1")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/C1: Nhà học C1":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.030940;
                        lng = 105.770270;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("C1")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/C2: Nhà học C2":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.028948;
                        lng = 105.768218;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("C2")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/KH: Khoa Khoa học tự nhiên":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.032517;
                        lng = 105.770586;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KHTN")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/KT: Khoa Kinh tế":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.033025;
                        lng = 105.770703;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KT")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/MT: Khoa Khoa học chính trị":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.032882;
                        lng = 105.771567;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KHCT")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/XH: Khoa KHoa học xã hội NV":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.031518;
                        lng = 105.772322;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KHXHNV")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/DB: Khoa Dự bị dân tộc":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.028525;
                        lng = 105.767723;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("DBDT")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/CN: Khoa Công nghệ":
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }
                        lat = 10.032374;
                        lng = 105.766864;
                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("CN")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/TS hoặc /AQ: Khoa Thủy sản":
                        lat = 10.031677;
                        lng =  105.765508;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("TS")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/DI: Khoa Công nghệ thông tin và Truyền thông":
                        lat = 10.030832;
                        lng =  105.769038;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("CNTT")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/NN: Khoa Nông nghiệp":
                        lat = 10.030346;
                        lng = 105.767890;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("NN")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Trung tâm Ngoại ngữ":
                        lat = 10.028080;
                        lng =  105.770046;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("TTNN")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/SH: Viện nghiên cứu và phát triển CNSH":
                        lat = 10.029206;
                        lng = 105.770485;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("VNCPTCNSH")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/MTN: Khoa Môi trường và tài nguyên TN":
                        lat = 10.027279;
                        lng = 105.764909;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("MTTNTN")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/KL: Khoa Luật":
                        lat = 10.028488;
                        lng = 105.769777;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KL")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/HA: Khoa phát triển nông thôn":
                        lat = 10.029345;
                        lng = 105.765257;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("PTNT")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "HL: Trung tâm học liệu":
                        lat = 10.030285;
                        lng = 105.771589;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("TTHL")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/D1: Nhà học D1":
                        lat = 10.029670;
                        lng = 105.770907;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("D1")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "/D2: Nhà học D2":
                        lat = 10.030125;
                        lng = 105.770238;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("D2")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Hội trường Rùa":
                        lat = 10.029341;
                        lng =  105.769484;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("HTR")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Nhà điều hành":
                        lat = 10.029795;
                        lng = 105.770570;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("NDH")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Nhà thi đấu TDTT":
                        lat = 10.033124;
                        lng = 105.769151;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("NTD")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Nhà tập luyện TDTT":
                        lat = 10.032195;
                        lng = 105.768353;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("NTL")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Ký túc xá A":
                        lat = 10.033809;
                        lng = 105.769942;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KTXA")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Ký túc xá B":
                        lat = 10.029834;
                        lng = 105.764432;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("KTXB")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Văn phòng Đoàn trường ĐHCT":
                        lat = 10.030785;
                        lng = 105.769563;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("VPD")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "Viện Nghiên cứu và Phát triển ĐBSCL":
                        lat = 10.029379;
                        lng = 105.765185;
                        Log.d("Selected", ":" + str);
                        if (destinationMarker != null) {
                            mapboxMap.removeMarker(destinationMarker);
                        }

                        destinationCoord = new LatLng(lat,lng);
                        destinationMarker = mapboxMap.addMarker(new MarkerOptions().title("VNCVPTDBCSL")
                                .position(destinationCoord));
                        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
                        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
                        getRoute(originPosition, destinationPosition);
                        button.setEnabled(true);
                        button.setBackgroundResource(R.color.mapboxBlue);
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                }
            }
        });


    };

    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        enableLocationPlugin();
        originCoord = new LatLng(originLocation.getLatitude(), originLocation.getLongitude());
        mapboxMap.addOnMapClickListener(this);
        Log.d("currentLocation",":"+originCoord);
        //set condition if-else show alert
        if (originCoord.getLatitude()<10.026703 || originCoord.getLongitude()<105.762555 || originCoord.getLatitude() >10.037720 || originCoord.getLongitude()>105.772946 ){
            Log.d("currentLocation","not in CTU");
            showAlertDialog();
        }
        button = findViewById(R.id.startButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean simulateRoute = true;
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build();
                // Call this method with Context from within an Activity
                NavigationLauncher.startNavigation(MapMainActivity.this, options);
            }
        });
        //set button open link
        openLink = findViewById(R.id.openLink);


        mapboxMap.setInfoWindowAdapter(new MapboxMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(@NonNull Marker marker) {

                // The info window layout is created dynamically, parent is the info window
                // container
                LinearLayout parent = new LinearLayout(MapMainActivity.this);
                parent.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                parent.setOrientation(LinearLayout.VERTICAL);

                // Depending on the marker title, the correct image source is used. If you
                // have many markers using different images, extending Marker and
                // baseMarkerOptions, adding additional options such as the image, might be
                // a better choice.
                ImageView myImage = new ImageView(MapMainActivity.this);
                switch (marker.getTitle()) {
                    case "A3":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhahoca3));

                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxw"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "B1":

                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhahocb1));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxr"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "C1":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhahocc1));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFx7"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "C2":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhahocc2));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxM"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "CNTT":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.cntt));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFx8"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "CN":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.congnghe));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxH"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "DBDT":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.dubidantoc));
//                        openLink.setEnabled(false);
//                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "HTR":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.htr));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxD"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "KHCT":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.khoahocchinhtri));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxF"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "KHTN":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.khoahoctunhien));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxI"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "KL":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.khoaluat));
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "KT":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.kinhte));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFx9"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "KTXA":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ktxa));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxx"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "KTXB":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ktxb));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxB"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "MTTNTN":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.mtvtntt));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxK"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "D1":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ndh));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxC"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "D2":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ndh));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxC"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "NDH":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ndh));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxC"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "TTHL":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.tthl));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxA"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "TTNN":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.ttngoaingu));
//                        openLink.setEnabled(false);
//                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "KHXHNV":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.xhnv));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CEpv"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "TS":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.thuysan));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxv"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "NN":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nongnghiep));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxu"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "NTL":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhatapluyen));
//                        openLink.setEnabled(false);
//                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                    case "NTD":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.nhathidau));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxy"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "VPD":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.vanphongdoan));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CFxN"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;
                    case "VNCVPTDBCSL":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.vienncvaptdbscl));

                        break;
                    case "VNCPTCNSH":
                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.dhct));
                        openLink.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent linkBroswer = new Intent(Intent.ACTION_VIEW, Uri.parse("https://skfb.ly/6CEpz"));
                                startActivity(linkBroswer);
                            }
                        });
                        openLink.setEnabled(true);
                        openLink.setBackgroundResource(R.drawable.background_button_clickopen3dmodel);
                        break;


                    default:
                        // By default all markers without a matching title will use the

                        myImage.setImageDrawable(ContextCompat.getDrawable(
                                MapMainActivity.this, R.drawable.dhct));
                        openLink.setEnabled(false);
                        openLink.setBackgroundResource(R.color.mapboxGrayLight);
                        break;
                }

                // Set the size of the image
                myImage.setLayoutParams(new android.view.ViewGroup.LayoutParams(700, 650));

                // add the image view to the parent layout
                parent.addView(myImage);

                return parent;
            }
        });


    }

    private void showAlertDialog() {
        //back HomePage
        Intent intent = new Intent(this, HomeMainActivity.class);
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("You aren't in CTU. You have to come back Homepage.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(intent);
                Toast.makeText(MapMainActivity.this,"Back to Homepage...",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onMapClick(@NonNull LatLng point){
        if (destinationMarker != null) {
            mapboxMap.removeMarker(destinationMarker);
        }
        destinationCoord = point;
        destinationMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(destinationCoord)
        );
        destinationPosition = Point.fromLngLat(destinationCoord.getLongitude(), destinationCoord.getLatitude());
        originPosition = Point.fromLngLat(originCoord.getLongitude(), originCoord.getLatitude());
        getRoute(originPosition, destinationPosition);
        button.setEnabled(true);
        button.setBackgroundResource(R.color.mapboxBlue);
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        // You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            // Create an instance of the plugin. Adding in LocationLayerOptions is also an optional
            // parameter
            LocationLayerPlugin locationLayerPlugin = new LocationLayerPlugin(mapView, mapboxMap);

            // Set the plugin's camera mode
            locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
            getLifecycle().addObserver(locationLayerPlugin);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {
        LocationEngineProvider locationEngineProvider = new LocationEngineProvider(this);
        locationEngine = locationEngineProvider.obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocationPlugin();
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
