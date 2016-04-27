package com.example.giris.nikiai.fragments.data;

/**
 * Created by giris on 27-03-2016.
 */
public class ContactsListModel {

    public String name, email, phone, officePhone, latitude, longitude;
    public double lat, lon;

    public ContactsListModel(String name, String email, String phone, String officePhone, String latitude, String longitude) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.officePhone = officePhone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}