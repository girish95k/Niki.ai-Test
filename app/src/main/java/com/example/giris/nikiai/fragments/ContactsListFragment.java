package com.example.giris.nikiai.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.giris.nikiai.R;
import com.example.giris.nikiai.fragments.adapters.ContactsListAdapter;
import com.example.giris.nikiai.fragments.data.ContactsListModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsListFragment extends Fragment {

    public static final String MY_PREFS_NAME = "Login Credentials";

    public static View.OnClickListener myOnClickListener;
    private static RecyclerView recyclerView;
    private static ArrayList<ContactsListModel> DATA;
    private static RecyclerView.Adapter adapter;
    private static ArrayList<Integer> removedItems;
    private RecyclerView.LayoutManager layoutManager;
    private OnFragmentInteractionListener mListener;

    public ContactsListFragment() {
        // Required empty public constructor
    }

    public static ContactsListFragment newInstance() {
        ContactsListFragment fragment = new ContactsListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contacts_list, container, false);
        // Inflate the layout for this fragment

        myOnClickListener = new MyOnClickListener(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(false);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DATA = new ArrayList<ContactsListModel>();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://private-b08d8d-nikitest.apiary-mock.com/contacts",  new AsyncHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                Log.e("success", new String(response));
                try {
                    //JSONObject json = new JSONObject(new String(response));
                    JSONArray data = new JSONArray(new String(response));
                    JSONObject data2 = data.getJSONObject(0);
                    JSONArray data3 = data2.getJSONArray("contacts");
                    int i;
                    for(i=0; i<data3.length(); i++){
                        JSONObject obj = data3.getJSONObject(i);
                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String phone = obj.getString("phone");
                        String officePhone = obj.getString("officePhone");
                        String latitude = obj.getString("latitude");
                        String longitude = obj.getString("longitude");
                        DATA.add(new ContactsListModel(name, email, phone, officePhone, latitude, longitude));
                    }
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                Log.e("Contacts", e.toString());
            }
        });

/*
        data.add(new ContactsListModel("Phalachandra", "WhiteField", "Near Form Value Mall", "5.0", "Skin"));
        data.add(new ContactsListModel("Kempegowda", "B Narayanapura", "Near B Narayanapura Bus Stop", "2.5", "Ortho"));
        data.add(new ContactsListModel("Harish", "WhiteField", "Near Form Value Mall", "3.5", "Heart"));
        data.add(new ContactsListModel("Ram Mohan Shenoy", "AECS Layout", "Near CMRIT", "4.5", "Heart"));
        data.add(new ContactsListModel("Jagan Shankar Reddy", "Mahadevapura", "Near Phoenix Mall", "3.0", "Heart"));
        data.add(new ContactsListModel("Shyam Ashok Dellwala", "Garudacharpalya", "Near Brigade Metropolis", "4.5", "Heart"));
        */adapter = new ContactsListAdapter(DATA, getActivity());
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other com.hulhack.quandrum.wireframes.fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        public MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recyclerView.getChildLayoutPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForLayoutPosition(selectedItemPosition);
            LinearLayout hiddenLayout
                    = (LinearLayout) viewHolder.itemView.findViewById(R.id.frame_expand);
            /*
            if(hiddenLayout.getVisibility() == View.VISIBLE) {
                hiddenLayout.setVisibility(View.GONE);
            }
            else {
                hiddenLayout.setVisibility(View.VISIBLE);
            }
            */
        }

    }

}
