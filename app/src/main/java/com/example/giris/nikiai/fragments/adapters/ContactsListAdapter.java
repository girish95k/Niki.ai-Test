package com.example.giris.nikiai.fragments.adapters;

/**
 * Created by giris on 27-03-2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devspark.robototextview.widget.RobotoTextView;
import com.example.giris.nikiai.R;
import com.example.giris.nikiai.fragments.ContactsListFragment;
import com.example.giris.nikiai.fragments.data.ContactsListModel;
import com.example.giris.nikiai.libraries.LabelTextView;
import com.rey.material.widget.Button;

import java.util.ArrayList;

public class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.MyViewHolder> {

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

        view.setOnClickListener(ContactsListFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        RobotoTextView name = holder.name;
        RobotoTextView email = holder.email;
        RobotoTextView phone = holder.phone;
        RobotoTextView officePhone = holder.officePhone;

        name.setText(dataSet.get(listPosition).name);
        email.setText(dataSet.get(listPosition).email);
        phone.setText("P: "+dataSet.get(listPosition).phone);
        officePhone.setText("O: "+dataSet.get(listPosition).officePhone);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RobotoTextView name;
        RobotoTextView email;
        RobotoTextView phone;
        RobotoTextView officePhone;
        //RobotoTextView specialization;

        Button book;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = (RobotoTextView) itemView.findViewById(R.id.name);
            this.email = (RobotoTextView) itemView.findViewById(R.id.email);
            this.phone = (RobotoTextView) itemView.findViewById(R.id.phone);
            this.officePhone = (RobotoTextView) itemView.findViewById(R.id.officePhone);
        }
        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == book.getId()){
                Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //context.startActivity(new Intent(context, BookAppointmentActivity.class));


            }
        }

    }
}
