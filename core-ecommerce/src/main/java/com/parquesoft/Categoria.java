package com.parquesoft;

public class Categoria {
    private String nombre;

    public static final Categoria EQUIPOS_COMPUTO = new Categoria("Equipos de Computo");
    public static final Categoria EQUIPOS_RED = new Categoria("Equipos de Red");

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
