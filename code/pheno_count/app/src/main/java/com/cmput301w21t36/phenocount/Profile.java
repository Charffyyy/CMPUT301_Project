package com.cmput301w21t36.phenocount;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Profile implements Serializable {

    private String username;
    private String phone;

    public Profile(String username, String phone) {
        this.username = username;
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


