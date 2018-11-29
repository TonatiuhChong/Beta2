package com.example.hombr.beta.Singletons;

public class Acmin {
    private static final Acmin ourInstance = new Acmin();

    public static Acmin getInstance() {
        return ourInstance;
    }

    private Acmin() {
    }

    int totalactivos;
    int totalagregar;
    String userAcmin;
    String correoAcmin;

    public String getUserAcmin() {
        return userAcmin;
    }

    public void setUserAcmin(String userAcmin) {
        this.userAcmin = userAcmin;
    }

    public Acmin(String userAcmin) {

        this.userAcmin = userAcmin;
    }

    public int getContadoragregar() {
        return contadoragregar;
    }

    public void setContadoragregar(int contadoragregar) {
        this.contadoragregar = contadoragregar;
    }

    public Acmin(int contadoragregar) {

        this.contadoragregar = contadoragregar;
    }

    int contadoragregar;

    public int getTotalactivos() {
        return totalactivos;
    }

    public void setTotalactivos(int totalactivos) {
        this.totalactivos = totalactivos;
    }

    public int getTotalagregar() {
        return totalagregar;
    }

    public void setTotalagregar(int totalagregar) {
        this.totalagregar = totalagregar;
    }

    public Acmin(int totalactivos, int totalagregar) {

        this.totalactivos = totalactivos;
        this.totalagregar = totalagregar;
    }
}
