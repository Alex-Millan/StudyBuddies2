package com.example.alex.studybuddies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import static android.R.attr.data;
import static android.webkit.ConsoleMessage.MessageLevel.LOG;


// Auto FORMAT Ctrl + ALT + L
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static GoogleMap mMap;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;

    AppInfo appInfo;

    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        disableShiftMode(navigation);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        appInfo = AppInfo.getInstance(this);
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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        ClassInfo class1 = new ClassInfo();
        class1.getCourse("Abigail"); //Initialize the course list to read from
        new file_loaded().execute(class1, class1, class1);


    }


    private class file_loaded extends AsyncTask<ClassInfo, ClassInfo, ClassInfo> {
        @Override
        protected ClassInfo doInBackground(ClassInfo... params) {
            while ((params[0].isLOADING_FILE())) {
                //DO NOTHING


            }
            Log.i("YOURMUM   ", "Load Complete size is " + params[0].getCourseSize());
            return null;
    }

    @Override
    protected void onPostExecute(ClassInfo s) {

        super.onPostExecute(s);
        Log.i("YOURMUM   ", "Post Complete size is " + s.getCourseSize());
        loadMap(s);
    }

}

public void loadMap(ClassInfo class1){
    if (mCurrLocationMarker != null) {
        mCurrLocationMarker.remove();
    }
    //Place current location marker

    LatLng latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    MarkerOptions markerOptions = new MarkerOptions();
    markerOptions.position(latLng);
    markerOptions.title("Current Position " + class1.getCourseSize());
    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
    mCurrLocationMarker = mMap.addMarker(markerOptions);
    //move map camera
    //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    //mMap.animateCamera(CameraUpdateFactory.zoomTo(20)); //change number to change radius
    final double currentLatitude = mLastLocation.getLatitude();
    final double currentLongitude = mLastLocation.getLongitude();
    boolean flag = false;
    Bundle change = getIntent().getExtras();
    if (change == null) {
    } else {
        flag = change.getBoolean("flag");
    }
    if (flag == true) {
        ViewClass(change.getString("location"));
    } else {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
    //ClassInfo class1 = new ClassInfo();
    //class1.getCourse("Abigail"); //Initialize the course list to read from
    for (int i = 0; i < class1.getCourseSize(); i++) {
        int start = class1.startTime.getHour(i); // Returns the hour of the first item in the list
        LatLng position = new LatLng(class1.loc.getLatitude(i), class1.loc.getLongitude(i));
        markerOptions.position(position);
        markerOptions.title("Study Room  " + i);
        int hour1 = class1.startTime.getHour(i);
        int hour2 = class1.endTime.getHour(i);
        int myMin = class1.startTime.getMinute(i);
        int myMin2 = class1.startTime.getMinute(i);
        String minute = String.format("%02d", myMin);
        String minute2 = String.format("%02d", myMin2);
        if (hour1 > 12) {
            hour1 -= 12;
        }
        if (hour2 > 12) {
            hour2 -= 12;
        }
        markerOptions.snippet(hour1 + ":" + minute + " - "
                + hour2 + ":" + minute2);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);
    }


    //stop location updates
    if (mGoogleApiClient != null) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            //MainActivity main = new MainActivity();
            //main.Stay();
            appInfo.addStudyGroup(marker.getTitle(), marker.getPosition().toString(), marker.getSnippet());
            Intent intent2 = new Intent(MapsActivity.this, Join.class);
            ClassInfo test = new ClassInfo();
            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);
        }
    });
}

    public static void ViewClass(String location) {
        String[] latlong = location.split("\\(");
        System.out.print("YOURMOM   " + location);
        System.out.println();
        Log.i("YOURMUM   ", location);
        String[] position = latlong[1].split(",");
        double place1 = Double.parseDouble(position[0]);
        String[] longitude = position[1].split("\\)");
        double place2 = Double.parseDouble(longitude[0]);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(place1, place2), 20));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
    }


    public void onStatusChanged(String provider, int status, Bundle extras) {

    }


    public void onProviderEnabled(String provider) {

    }


    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Intent in;
            switch (item.getItemId()) {
                case R.id.nav_classes:
                    in = new Intent(getBaseContext(), ClassSelectionActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    //return true;
                    break;
                case R.id.nav_map:
                    in = new Intent(getBaseContext(), MapsActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
                //return true;
                case R.id.nav_study_mode:
                    in = new Intent(getBaseContext(), Join.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
                case R.id.nav_settings:
                    in = new Intent(getBaseContext(), SettingsActivity.class);
                    startActivity(in);
                    overridePendingTransition(0, 0);
                    break;
            }
            return false;
        }

    };

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            //Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            //Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }
}

