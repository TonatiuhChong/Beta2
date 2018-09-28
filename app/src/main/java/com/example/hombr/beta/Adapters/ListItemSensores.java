package com.example.hombr.beta.Adapters;

public class ListItemSensores {
    private String Ambiental;
    private String Presencia;
    private String Ventana;
    private String Puerta;
    private String Iluminacion;

    public String getAmbiental() {
        return Ambiental;
    }

    public void setAmbiental(String ambiental) {
        Ambiental = ambiental;
    }

    public String getPresencia() {
        return Presencia;
    }

    public void setPresencia(String presencia) {
        Presencia = presencia;
    }

    public String getVentana() {
        return Ventana;
    }

    public void setVentana(String ventana) {
        Ventana = ventana;
    }

    public String getPuerta() {
        return Puerta;
    }

    public void setPuerta(String puerta) {
        Puerta = puerta;
    }

    public String getIluminacion() {
        return Iluminacion;
    }

    public void setIluminacion(String iluminacion) {
        Iluminacion = iluminacion;
    }

    public String getHabitacion() {
        return Habitacion;
    }

    public void setHabitacion(String habitacion) {
        Habitacion = habitacion;
    }

    public ListItemSensores(String ambiental, String presencia, String ventana, String puerta, String iluminacion, String habitacion) {

        Ambiental = ambiental;
        Presencia = presencia;
        Ventana = ventana;
        Puerta = puerta;
        Iluminacion = iluminacion;
        Habitacion = habitacion;
    }

    private String Habitacion;


   }