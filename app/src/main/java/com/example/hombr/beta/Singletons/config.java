package com.example.hombr.beta.Singletons;

public class config {
    private static final config ourInstance = new config();

    public static config getInstance() {
        return ourInstance;
    }

    private config() {
    }

    boolean notif;

    public config(boolean notif) {
        this.notif = notif;
    }

    public boolean isNotif() {
        return notif;
    }

    public void setNotif(boolean notif) {
        this.notif = notif;
    }
}
