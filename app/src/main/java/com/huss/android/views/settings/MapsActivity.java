package com.huss.android.views.settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.huss.android.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.huss.android.utility.NetworkReceiverUtil;
import com.ldoublem.loadingviewlib.view.LVCircularZoom;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, NetworkReceiverUtil.ConnectivityReceiverListener {

    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    ListView lstPlaces;
    private PlacesClient mPlacesClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private static final int M_MAX_ENTRIES = 30;
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs;
    private LVCircularZoom lvCircularZoom;
    private TextView progressText;
    private LocationManager locationManager;
    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        lstPlaces = findViewById(R.id.listPlaces);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(), apiKey);
        mPlacesClient = Places.createClient(this);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        lvCircularZoom = findViewById(R.id.progress);
        lvCircularZoom.setViewColor(getResources().getColor(R.color.gray));
        lvCircularZoom.startAnim(100);
        progressText = findViewById(R.id.progressText);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        getLocationPermission();
        pickCurrentPlace();
    }

    private void getLocationPermission() {
        mLocationPermissionGranted = false;
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void getCurrentPlaceLikelihoods() {
        List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
                Place.Field.LAT_LNG);
        final FindCurrentPlaceRequest request =
                FindCurrentPlaceRequest.builder(placeFields).build();
        Task<FindCurrentPlaceResponse> placeResponse = mPlacesClient.findCurrentPlace(request);
        placeResponse.addOnCompleteListener(this,
                task -> {
                    if (task.isSuccessful()) {
                        FindCurrentPlaceResponse response = task.getResult();
                        int count;
                        assert response != null;
                        if (response.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
                            count = response.getPlaceLikelihoods().size();
                        } else {
                            count = M_MAX_ENTRIES;
                        }

                        int i = 0;
                        mLikelyPlaceNames = new String[count];
                        mLikelyPlaceAddresses = new String[count];
                        mLikelyPlaceAttributions = new String[count];
                        mLikelyPlaceLatLngs = new LatLng[count];

                        for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                            Place currPlace = placeLikelihood.getPlace();
                            mLikelyPlaceNames[i] = currPlace.getName();
                            mLikelyPlaceAddresses[i] = currPlace.getAddress();
                            mLikelyPlaceAttributions[i] = (currPlace.getAttributions() == null) ?
                                    null : TextUtils.join(" ", currPlace.getAttributions());
                            mLikelyPlaceLatLngs[i] = currPlace.getLatLng();

                            String currLatLng = (mLikelyPlaceLatLngs[i] == null) ?
                                    "" : mLikelyPlaceLatLngs[i].toString();

                            Log.i(TAG, String.format("Place " + currPlace.getName()
                                    + " has likelihood: " + placeLikelihood.getLikelihood()
                                    + " at " + currLatLng));

                            i++;
                            if (i > (count - 1)) {
                                break;
                            }
                        }

                        fillPlacesList();
                    } else {
                        Exception exception = task.getException();
                        if (exception instanceof ApiException) {
                            ApiException apiException = (ApiException) exception;
                            Log.e(TAG, "Place not found: " + apiException.getMessage());
                        }
                    }
                });
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                 locationManager = (LocationManager)
                        this.getSystemService(Context.LOCATION_SERVICE);

                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                    locationResult.addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation == null){
                                getCurrentPlaceLikelihoods();
                            }else {
                                Log.d(TAG, "Latitude: " + mLastKnownLocation.getLatitude());
                                Log.d(TAG, "Longitude: " + mLastKnownLocation.getLongitude());
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(mLastKnownLocation.getLatitude(),
                                                mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        }

                        getCurrentPlaceLikelihoods();
                    });
                }

        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (mLocationPermissionGranted) {
            getDeviceLocation();
        } else {
            Log.i(TAG, "The user did not grant location permission.");
            mMap.addMarker(new MarkerOptions()
                    .title(getString(R.string.default_info_title))
                    .position(mDefaultLocation)
                    .snippet(getString(R.string.default_info_snippet)));
            getLocationPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            }
        }
    }

    private AdapterView.OnItemClickListener listClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {

            LatLng markerLatLng = mLikelyPlaceLatLngs[position];
            String markerSnippet = mLikelyPlaceAddresses[position];

            if (getIntent().getStringExtra("FROM").equals("ContactDetailsActivity")){
                Intent intent = new Intent(MapsActivity.this, ContactDetailsActivity.class);
                intent.putExtra("VALUE", markerSnippet);
                startActivity(intent);
                finish();
            }else if(getIntent().getStringExtra("FROM").equals("CreateAd")){

            }

            if (mLikelyPlaceAttributions[position] != null) {
                markerSnippet = markerSnippet + "\n" + mLikelyPlaceAttributions[position];
            }

            mMap.addMarker(new MarkerOptions()
                    .title(mLikelyPlaceNames[position])
                    .position(markerLatLng)
                    .snippet(markerSnippet));

            mMap.moveCamera(CameraUpdateFactory.newLatLng(markerLatLng));

        }
    };

    private void fillPlacesList() {
        ArrayAdapter<String> placesAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLikelyPlaceAddresses);
        lstPlaces.setAdapter(placesAdapter);
        lstPlaces.setOnItemClickListener(listClickedHandler);

        lvCircularZoom.stopAnim();
        lvCircularZoom.setVisibility(View.GONE);
        lvCircularZoom.stopAnim();
        progressText.setVisibility(View.GONE);
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkReceiverUtil.Companion.setConnectivityReceiverListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected){
            snackbar = Snackbar.make(lstPlaces, "No internet connection", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }else{
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }
    }
}
