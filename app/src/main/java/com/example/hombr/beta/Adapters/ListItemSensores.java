package com.example.hombr.beta.Adapters;

public class ListItemSensores {
    private String Tsensor, ValorSensor, Tmotor, EstadoMotor, Habitacion;

    public ListItemSensores(String tsensor, String valorSensor, String tmotor, String estadoMotor, String habitacion) {
        Tsensor = tsensor;
        ValorSensor = valorSensor;
        Tmotor = tmotor;
        EstadoMotor = estadoMotor;
        Habitacion = habitacion;
    }

    public String getTsensor() {
        return Tsensor;
    }

    public void setTsensor(String tsensor) {
        Tsensor = tsensor;
    }

    public String getValorSensor() {
        return ValorSensor;
    }

    public void setValorSensor(String valorSensor) {
        ValorSensor = valorSensor;
    }

    public String getTmotor() {
        return Tmotor;
    }

    public void setTmotor(String tmotor) {
        Tmotor = tmotor;
    }

    public String getEstadoMotor() {
        return EstadoMotor;
    }

    public void setEstadoMotor(String estadoMotor) {
        EstadoMotor = estadoMotor;
    }

    public String getHabitacion() {
        return Habitacion;
    }

    public void setHabitacion(String habitacion) {
        Habitacion = habitacion;
    }
}