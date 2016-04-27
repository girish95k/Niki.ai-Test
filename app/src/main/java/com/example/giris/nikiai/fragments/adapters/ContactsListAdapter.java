package com.example.giris.nikiai.fragments.adapters;

/**
 * Created by giris on 27-03-2016.
 */

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.devspark.robototextview.widget.RobotoTextView;
import com.example.giris.nikiai.R;
import com.example.giris.nikiai.fragments.ContactsListFragment;
import com.example.giris.nikiai.fragments.data.ContactsListModel;
import com.example.giris.nikiai.libraries.LabelTextView;
import com.rey.material.widget.Button;

import java.util.ArrayList;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.MyViewHolder> {

    public String p;

    public static Context context;

    private ArrayList<ContactsListModel> dataSet;
    public ContactsListAdapter(ArrayList<ContactsListModel> data, Context context) {
        this.dataSet = data;
        this.context =context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contacts_list_item, parent, false);

        //view.setOnClickListener(ContactsListFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        p = dataSet.get(listPosition).phone;

        RobotoTextView name = holder.name;
        RobotoTextView email = holder.email;
        RobotoTextView phone = holder.phone;
        RobotoTextView officePhone = holder.officePhone;


        name.setText(dataSet.get(listPosition).name);
        if(dataSet.get(listPosition).email!=null)
            email.setText("Email: "+dataSet.get(listPosition).email);
        else
            email.setText("Email: Unavailable");
        if(dataSet.get(listPosition).phone!=null)
            phone.setText("Phone: "+dataSet.get(listPosition).phone);
        else
            phone.setText("Phone: Unavailable");
        if(dataSet.get(listPosition).officePhone!=null)
            officePhone.setText("Office: " + dataSet.get(listPosition).officePhone);
        else
            officePhone.setText("Office: Unavailable");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RobotoTextView name;
        RobotoTextView email;
        RobotoTextView phone;
        RobotoTextView officePhone;
        //RobotoTextView specialization;

        Button book;
        MaterialRippleLayout ripple;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (RobotoTextView) itemView.findViewById(R.id.name);
            this.email = (RobotoTextView) itemView.findViewById(R.id.email);
            this.phone = (RobotoTextView) itemView.findViewById(R.id.phone);
            this.officePhone = (RobotoTextView) itemView.findViewById(R.id.officePhone);
            this.ripple = (MaterialRippleLayout) itemView.findViewById(R.id.ripple);
            //this.ripple.setOnClickListener(this);
            this.ripple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // handle me
                    //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + (dataSet.get(getAdapterPosition()).phone==null?dataSet.get(getAdapterPosition()).officePhone:dataSet.get(getAdapterPosition()).phone)));
                        context.startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {
                        Toast.makeText(v.getContext(), "Call failed.", Toast.LENGTH_SHORT).show();
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }
                }
            });
        }
        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == ripple.getId()){
                //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //context.startActivity(new Intent(context, BookAppointmentActivity.class));

            }
        }

    }
}
