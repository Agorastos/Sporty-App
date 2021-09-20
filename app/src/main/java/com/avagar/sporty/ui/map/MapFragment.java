package com.avagar.sporty.ui.map;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.avagar.sporty.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment {

    private String place;
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, container, false);
        place = MapFragmentArgs.fromBundle(getArguments()).getPlace();

        mMapView = view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(mMap -> {
            googleMap = mMap;
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> list = new ArrayList<>();
            try {
                list = geocoder.getFromLocationName(place, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (list.size() > 0) {
                Address address = list.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker Title").snippet("Marker Description"));
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                //            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });


        return view;
    }
}