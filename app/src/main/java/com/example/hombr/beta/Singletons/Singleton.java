package com.example.hombr.beta.Singletons;

import android.net.Uri;

import java.lang.reflect.Array;
import java.util.List;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public String getAccionExtra() {
        return accionExtra;
    }

    public void setAccionExtra(String accionExtra) {
        this.accionExtra = accionExtra;
    }

    public boolean isActivacioncontrol() {
        return activacioncontrol;
    }

    public void setActivacioncontrol(boolean activacioncontrol) {
        this.activacioncontrol = activacioncontrol;
    }

    private  Singleton sin;
    private void  reset(){
        sin=new Singleton();
    }

    public Singleton(String user, String password, String email, String habitacion, String modo, String tipo, String valor, Uri foto, String accionExtra, boolean activacioncontrol) {
        this.user = user;
        this.password = password;
        this.email = email;
        this.habitacion = habitacion;
        this.modo = modo;
        this.tipo = tipo;
        this.valor = valor;
        this.foto = foto;
        this.accionExtra=accionExtra;
        this.activacioncontrol=activacioncontrol;


    }

    String user,password,email, habitacion,modo,tipo,valor,accionExtra;
    String[] agregar;

    public String[] getAgregar() {
        return agregar;
    }

    public void setAgregar(String[] agregar) {
        this.agregar = agregar;
    }

    public Singleton(String[] agregar) {

        this.agregar = agregar;
    }

    Uri foto;
    String info;
    boolean  activacioncontrol;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Singleton(String info) {

        this.info = info;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(String habitacion) {
        this.habitacion = habitacion;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
    public Uri getFoto() {
        return foto;
    }

    public void setFoto(Uri foto) {
        this.foto = foto;
    }
}
