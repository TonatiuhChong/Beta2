package com.example.hombr.beta.Singletons;

public class config {
    private static final config ourInstance = new config();

    public static config getInstance() {
        return ourInstance;
    }

    private config() {
    }

    boolean notif,autoluz,autoluz2;

    public boolean isAutoluz2() {
        return autoluz2;
    }

    public void setAutoluz2(boolean autoluz2) {
        this.autoluz2 = autoluz2;
    }

    public boolean isAutoluz() {
        return autoluz;
    }

    public void setAutoluz(boolean autoluz) {
        this.autoluz = autoluz;
    }

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
