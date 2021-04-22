package com.thils.librarybook.model;

import org.json.JSONObject;

public class User {

    public String id = null;
    public String name = null;
    public String age = null;
    public String address = null;
    public String contactno = null;
    public String username = null;
    public String password = null;
    public String role = null;
    public String status = null;

    public static User getUser(JSONObject jo) {
        User user = new User();
        user.id = jo.optString("id");
        user.name = jo.optString("name");
        user.age = jo.optString("age");
        user.address = jo.optString("address");
        user.contactno = jo.optString("contactno");
        user.username = jo.optString("username");
        user.password = jo.optString("password");
        user.role = jo.optString("role");
        user.status = jo.optString("status");
        return user;
    }
}
