package com.empresa.flota.dao;

import com.empresa.flota.model.Vehiculo;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serializar los objetos de manera directa
 * @author Perceval Andreu
 */
public class GestorBackup {
  
    /**
     * Serializacion de vehiculo
     * @param archivo donde guardar vehiculo
     * @param vehiculo vehiculo a serializar
     * @param adjuntar si falso, se sobreescribe el archivo
     */
    public static void backupGuardar (File archivo, Vehiculo vehiculo, boolean adjuntar) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(archivo, adjuntar))) {
            output.writeObject(vehiculo);
            output.flush();
            output.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex) {
            System.getLogger(GestorBackup.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    /**
     * Deserializar vehiculo
     * @param archivo donde esta el vehiculo guardado
     */
    public static void backupLeer (File archivo) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(archivo))) {
            while (true) {
                Vehiculo vehiculo = (Vehiculo)input.readObject();
                System.out.println(vehiculo);
            }
        } catch (EOFException ex) {
            //Final de archivo
            //System.out.println("Test final de archivo");
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado");
        } catch (IOException ex) {
            System.getLogger(GestorBackup.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (ClassNotFoundException ex) {
            System.getLogger(GestorBackup.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
