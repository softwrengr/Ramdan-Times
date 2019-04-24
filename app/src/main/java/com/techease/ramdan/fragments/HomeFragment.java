package com.techease.ramdan.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.techease.ramdan.R;
import com.techease.ramdan.utilities.GeneralUtils;

import butterknife.ButterKnife;

import static android.content.Context.LOCATION_SERVICE;


public class HomeFragment extends Fragment {
    View view;
    BottomNavigationView bottomNavigationView;


    String strLatitude, strLongitude;
    public static double lattitude, longitude;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 100;
    private static final long LOCATION_REFRESH_TIME = 1;
    private static final float LOCATION_REFRESH_DISTANCE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Ramadan Timing");
        initUI();
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        GeneralUtils.connectHomeFragment(getActivity(), new MonthFragment());
        return view;
    }

    private void initUI() {
        ButterKnife.bind(this, view);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_month:
                    GeneralUtils.connectHomeFragment(getActivity(), new MonthFragment());
                    return true;
                case R.id.navigation_clock:
                    GeneralUtils.connectHomeFragment(getActivity(), new ClockFragment());
                    return true;
                case R.id.navigation_duaw:
                    GeneralUtils.connectHomeFragment(getActivity(), new DuasFragment());
                    return true;

                case R.id.navigation_setting:
                    GeneralUtils.connectHomeFragment(getActivity(),new SettingFragment());
                    return true;
            }
            return false;
        }
    };

    //getting current location
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                lattitude = latti;
                longitude = longi;

            } else if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();

                lattitude = latti;
                longitude = longi;

            } else if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lattitude = latti;
                longitude = longi;

            } else {
                Toast.makeText(getActivity(), "Unble to Trace your location", Toast.LENGTH_SHORT).show();
            }

            strLatitude = String.valueOf(lattitude);
            strLongitude = String.valueOf(longitude);
            GeneralUtils.putStringValueInEditor(getActivity(), "latitude", strLatitude);
            GeneralUtils.putStringValueInEditor(getActivity(), "longitude", strLongitude);
        }
    }


    protected void buildAlertMessageNoGps() {

        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

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
    };
}
