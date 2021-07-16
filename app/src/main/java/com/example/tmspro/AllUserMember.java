package com.example.tmspro;

public class AllUserMember {
    String name,prof,location,email,web,uid,url;

    public AllUserMember(String name, String prof, String location, String email, String web, String uid, String url) {
        this.name = name;
        this.prof = prof;
        this.location = location;
        this.email = email;
        this.web = web;
        this.uid = uid;
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public AllUserMember(){}

    public AllUserMember(String name, String prof, String location, String uid, String url) {
        this.name = name;
        this.prof = prof;
        this.location = location;
        this.uid = uid;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
