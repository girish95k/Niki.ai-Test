package com.example.giris.nikiai.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.giris.nikiai.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A fragment that launches other parts of the demo application.
 */
public class ContactsMapFragment extends Fragment {

    private static ArrayList<Marker> locations;
    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_map, container,
                true);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }


        mMapView = (MapView) v.findViewById(R.id.mapView2);
        if(mMapView == null)
        {
            Log.e("null", "maps");
        }
        else
        {
            Log.e("not null", mMapView.toString());
        }
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();


        locations = new ArrayList<Marker>();

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
        pDialog.setCancelable(true);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://private-b08d8d-nikitest.apiary-mock.com/contacts", new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                pDialog.hide();
                try {
                    JSONArray data = new JSONArray(new String(response));
                    JSONObject data2 = data.getJSONObject(0);
                    JSONArray data3 = data2.getJSONArray("contacts");
                    //JSONObject json = new JSONObject(new String(response));
                    //JSONArray data2 = json.getJSONArray("data");
                    int i;
                    for (i = 0; i < data3.length(); i++) {
                        JSONObject obj = data3.getJSONObject(i);
                        String latitude = obj.getString("latitude");
                        String longitude = obj.getString("longitude");
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String phone = obj.getString("phone");
                        String officePhone = obj.getString("officePhone");
                        Marker marker = googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)))
                                .title(name)
                                .snippet("Contact Details\nEmail- "+email+"\nPhone- "+phone+"\nOffice- "+officePhone));
                        locations.add(marker);


                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker m : locations) {
                    builder.include(m.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 0; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                googleMap.animateCamera(cu);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                pDialog.hide();
                Log.e("DOCTOR", e.toString());
            }
        });

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}