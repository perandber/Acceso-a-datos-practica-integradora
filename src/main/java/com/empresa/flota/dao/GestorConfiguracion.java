package com.empresa.flota.dao;

import com.empresa.flota.model.Vehiculo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Leer y guardar la informacion del sistema a un archivo
 * @author Perceval Andreu
 */
public class GestorConfiguracion {
    public static GestorConfiguracion configuracion;
    
    private String nombreEmpresa;
    private String versionSoftware;
    private int totalVehiculos;
    private int totalKilometros;

    private GestorConfiguracion() {
        this.nombreEmpresa = "Almacen Johnny";
        this.versionSoftware = "0.1";
        this.totalVehiculos = Vehiculo.getTotalVehiculos();
        this.totalKilometros = Vehiculo.getTotalKilometros();
    }

    private GestorConfiguracion(String nombreEmpresa, String versionSoftware) {
        this.nombreEmpresa = nombreEmpresa;
        this.versionSoftware = versionSoftware;
        this.totalVehiculos = Vehiculo.getTotalVehiculos();
        this.totalKilometros = Vehiculo.getTotalKilometros();
    }
    
    /**
     * Crear la clase solo si no existe
     * @return copia de la propia clase
     */
    public static GestorConfiguracion getConfiguracion() {
        if(configuracion == null) {
            configuracion = new GestorConfiguracion();
        }
        return configuracion;
    }
      
    /**
     * Guardar la informacion del sistema
     * @param archivo donde guardar la informacion
     */
    public void guardar(File archivo) {
        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(archivo, false))) {
            //Escribir datos
            output.writeUTF(nombreEmpresa);
            output.writeUTF(versionSoftware);
            output.writeInt(totalVehiculos);
            output.writeInt(totalKilometros);
            
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo de guardado no encontrado");
        } catch (IOException ex) {
            System.out.println("Error I/O: " + ex.getMessage()); 
        }
    }
    
    /**
     * Leer la informacion del sistema
     * @param archivo donde esta guardada la informacion
     */
    public void leer(File archivo) {
        try (DataInputStream input = new DataInputStream(new FileInputStream(archivo))) { 
            
            while (true) {  
                System.out.println("Compañia: "+input.readUTF());
                System.out.println("Version de software: "+input.readUTF());
                System.out.println("Vehiculos: "+input.readInt());
                System.out.println("Kilometraje total: "+input.readInt());
            } 
            
        } catch (EOFException e) {  
                System.out.println("Fin de lectura del archivo binario alcanzado.");  
        } catch (IOException e) { 
                System.err.println("Error I/O: " + e.getMessage());  
        } 
    }
    
    
}
