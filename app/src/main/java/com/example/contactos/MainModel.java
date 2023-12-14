package com.example.contactos;

public class MainModel  {

    String correo,materia,nombre,teléfono,turl;

    MainModel()
    {

    }

    public MainModel(String correo, String materia, String nombre, String teléfono, String turl) {
        this.correo = correo;
        this.materia = materia;
        this.nombre = nombre;
        this.teléfono = teléfono;
        this.turl = turl;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTeléfono() {
        return teléfono;
    }

    public void setTeléfono(String teléfono) {
        this.teléfono = teléfono;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}