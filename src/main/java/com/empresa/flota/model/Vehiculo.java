package com.empresa.flota.model;

import com.empresa.flota.dao.GestorConfiguracion;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Entidad JavaBeans
 * @author Perceval Andreu
 */
public class Vehiculo implements Serializable{
    private String matricula;
    private String marca;
    private String modelo;
    private int anyo;
    @SerializedName("kms_recorridos") private int kilometraje;
    private String categoria; //Categoria de mantenimineto
    
    private static int totalVehiculos = 0;
    private static int totalKilometros = 0;
    
    //Constructores
    public Vehiculo() {totalVehiculos++;};

    public Vehiculo(String matricula, String marca, String modelo, int anyo, int kilometraje, String categoria) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.anyo = anyo;
        this.kilometraje = kilometraje;
        this.categoria = categoria;
        totalVehiculos++;
        totalKilometros += kilometraje;
    }

    //ToString
    @Override
    public String toString() {
        return "matricula: " + matricula + ", marca: " + marca + ", modelo: " + modelo + ", anyo: " + anyo + ", kilometraje: " + kilometraje + ", categoria: " + categoria;
    }
    
    
    //Getters y setters
    public String getMatricula() {
        return matricula;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAnyo() {
        return anyo;
    }

    public int getKilometraje() {
        return kilometraje;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAnyo(int anyo) {
        this.anyo = anyo;
    }

    public void setKilometraje(int kilometraje) {
        totalKilometros += (this.kilometraje - kilometraje);
        this.kilometraje = kilometraje;
        GestorConfiguracion config = GestorConfiguracion.getConfiguracion();
        
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public static int getTotalVehiculos() {
        return totalVehiculos;
    }

    public static int getTotalKilometros() {
        return totalKilometros;
    }

    public static void setTotalVehiculos(int totalVehiculos) {
        Vehiculo.totalVehiculos = totalVehiculos;
    }

    public static void setTotalKilometros(int totalKilometros) {
        Vehiculo.totalKilometros = totalKilometros;
    }
    
    
}
