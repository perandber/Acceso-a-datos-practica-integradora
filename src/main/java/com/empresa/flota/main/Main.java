package com.empresa.flota.main;

import com.empresa.flota.dao.GestorConfiguracion;
import com.empresa.flota.model.Vehiculo;
import static com.empresa.flota.dao.GestorBackup.*;
import static com.empresa.flota.dao.GestorConfiguracion.getConfiguracion;
import static com.empresa.flota.dao.GestorInventarioJSON.*;
import static com.empresa.flota.dao.GestorReportesXML.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Perceval Andreu
 */
public class Main {

    public static void main(String[] args) {
        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
        //Crear directorios
        try {
            Path datosConfig = Paths.get("datos/config");
            Files.createDirectories(datosConfig);
            Path datosBackup = Paths.get("datos/backup");
            Files.createDirectories(datosBackup);
            Path datosReports = Paths.get("datos/reports");
            Files.createDirectories(datosReports);
        } catch (IOException ex) {
            System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

        //Crear vehiculos
        vehiculos.add(new Vehiculo("0910-1291", "Ford", "Ford 3", 2020, 1000, "M1"));
        vehiculos.add(new Vehiculo("12345OAA", "BMW", "Modelo X", 2000, 56000, "M2"));
        
        //GestorConfiguracion
        System.out.println("\nGESTOR CONFIGURACION:");
        System.out.println("-----------");
        GestorConfiguracion configuracion = getConfiguracion();
        File configFile = new File ("datos/config/empleados.dat");
        //Guardar
        configuracion.guardar(configFile);
        //Leer
        configuracion.leer(configFile);
        
        //GestorInventarioJSON
        System.out.println("\nGESTOR INVENTARIO:");
        System.out.println("-----------");
        File inventarioFile = new File ("datos/config/flota.json");
        //Guardar
        Inventarioguardar(inventarioFile, vehiculos);
        //Leer
        HashMap<String, Vehiculo> vehiculosJSON = inventarioLeer(inventarioFile);
        System.out.println(vehiculosJSON.get("0910-1291"));
        
        //GestorBackup
        System.out.println("\nGESTOR BACKUP:");
        System.out.println("-----------");
        File backup = new File ("datos/backup/flota_backup.ser");
        //Guardar
        backupGuardar(backup, vehiculos.get(1), false);
        //Leer
        backupLeer(backup);
        
        //GestorReportes
        System.out.println("\nGESTOR REPORTES:");
        System.out.println("-----------");
        File reportes = new File("datos/reports/informe_flota.xml");
        //Guardar
        generacionDOM(reportes, vehiculos);
        //Leer
        System.out.println(lecturaSAX(reportes));
        
        //Salto de linea final (Resultado mas limpio)
        System.out.println("");
    }
}
