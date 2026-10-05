package com.empresa.flota.dao;

import com.empresa.flota.model.Vehiculo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Exportar e importar la lista completa de vehiculos a formato JSON
 * @author Perceval Andreu
 */
public class GestorInventarioJSON {
    
    /**
     * Guardar una lista de vehiculos a JSON
     * Se sobreescribe el archivo
     * @param archivo donde guardar los vehiculos
     * @param vehiculos lista de vehiculos a guardar
     */
    public static void Inventarioguardar(File archivo, List vehiculos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); 
            
            writer.print(gson.toJson(vehiculos));
        } catch (IOException ex) {
            System.getLogger(GestorInventarioJSON.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }    
    }
    
    /**
     * Leer lista de vehiculos desde un archivo JSON
     * @param archivo archivo con los vehiculos
     * @return HashMap con los archivos guardados
     */
    public static HashMap inventarioLeer(File archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            //Leer archivo (Se requiere convertir a array, no puede ir directamente a arraylist
            Vehiculo[] vehiculosArray = gson.fromJson(reader, Vehiculo[].class);
            //Array a List (La tarea lo pide)
            List<Vehiculo> vehiculosArrayList = Arrays.asList(vehiculosArray);
            //List a HashMap
            HashMap<String, Vehiculo> vehiculosMap = new HashMap<>();
            for (Vehiculo vehiculo : vehiculosArrayList) {
                vehiculosMap.put(vehiculo.getMatricula(), vehiculo);
            }
            
            return vehiculosMap; //Resultado
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo "+archivo+" no encontrado");
        } catch (IOException ex) {
            System.getLogger(GestorInventarioJSON.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null; //Operacion a fallado, se devuelve null
    }
}
