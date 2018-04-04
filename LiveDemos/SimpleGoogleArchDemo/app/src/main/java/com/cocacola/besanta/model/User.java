package com.cocacola.besanta.model;

import com.google.firebase.firestore.PropertyName;

public class User extends Model {

    public static final String EMAIL_FIELD = "email";
    @PropertyName("avatar_url")
    public String avatarUrl;

    @PropertyName("display_name")
    public String displayName;

    @PropertyName("first_name")
    public String firstName;

    @PropertyName("last_name")
    public String lastName;

    @PropertyName("points")
    public int points;

    @PropertyName("santa_state")
    public int santaState;

    @PropertyName("email")
    public String email;
}
