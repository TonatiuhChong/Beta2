package com.example.hombr.beta.Adapters;

public class ListItemValuesSensor {


    String ambiental, iluminacion,presencia, puerta, apagador, ventana;
    public  ListItemValuesSensor(){}

    public ListItemValuesSensor(String ambiental, String iluminacion, String presencia, String puerta, String apagador, String ventana) {
        this.ambiental = ambiental;
        this.iluminacion = iluminacion;
        this.presencia = presencia;
        this.puerta = puerta;
        this.apagador = apagador;
        this.ventana = ventana;
    }


    public String getAmbiental() {
        return ambiental;
    }

    public void setAmbiental(String ambiental) {
        this.ambiental = ambiental;
    }

    public String getIluminacion() {
        return iluminacion;
    }

    public void setIluminacion(String iluminacion) {
        this.iluminacion = iluminacion;
    }

    public String getPresencia() {
        return presencia;
    }

    public void setPresencia(String presencia) {
        this.presencia = presencia;
    }

    public String getPuerta() {
        return puerta;
    }

    public void setPuerta(String puerta) {
        this.puerta = puerta;
    }

    public String getApagador() {
        return apagador;
    }

    public void setApagador(String apagador) {
        this.apagador = apagador;
    }

    public String getVentana() {
        return ventana;
    }

    public void setVentana(String ventana) {
        this.ventana = ventana;
    }
}
