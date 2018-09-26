package com.example.hombr.beta.Adapters;

import java.net.URI;

public class ListItemUsuarios {

    String Nombre, Correo, Nivel;
    int FotoUsuario;

    public String getNombre() {
        return Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public String getNivel() {
        return Nivel;
    }

    public int getFotoUsuario() {
        return FotoUsuario;
    }

    public ListItemUsuarios(String nombre, String correo, String nivel, int fotoUsuario) {

        Nombre = nombre;
        Correo = correo;
        Nivel = nivel;
        FotoUsuario = fotoUsuario;
    }
}
