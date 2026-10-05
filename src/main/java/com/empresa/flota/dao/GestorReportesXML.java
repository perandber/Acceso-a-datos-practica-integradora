package com.empresa.flota.dao;

import com.empresa.flota.model.Vehiculo;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Crear y leer un archivo XML basado en una lista de vehiculos
 * (Aqui admito que no entiendo como funcionan estas tecnologias [XML y SAX Parsers]
 * la mayoria del codigo tubo que ser copiado del repositorio github proveido y otros resultado)
 * @author Perceval Andreu
 */
public class GestorReportesXML {
    
    /**
     * Crear un archivo XML dado una lista de vehiculos
     * @param archivo donde crear el archivo XML
     * @param vehiculos lista de vehiculos a guardar
     */
    public static void generacionDOM (File archivo, List<Vehiculo> vehiculos) {
        try {
            //Crear archivo si no existe
            archivo.createNewFile();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            //Elemento raíz
            Element informe = doc.createElement("informe_flota");
            doc.appendChild(informe);

            //Datos globales
            Element resumen = doc.createElement("resumen");
            informe.appendChild(resumen);
            //Total vehiculos
            Element totalVehiculos = doc.createElement("total_vehiculos");
            totalVehiculos.appendChild(
                doc.createTextNode(
                    String.valueOf(Vehiculo.getTotalVehiculos())
                )
            );
            resumen.appendChild(totalVehiculos);
            //Total Kilometros
            Element totalKilometros = doc.createElement("total_kilometros");
            totalKilometros.appendChild(
                doc.createTextNode(
                    String.valueOf(Vehiculo.getTotalKilometros())
                )
            );
            resumen.appendChild(totalKilometros);

            //Raiz vehículos
            Element listaVehiculos = doc.createElement("vehiculos");
            informe.appendChild(listaVehiculos);

            //Datos de todos los vehiculos
            for (Vehiculo vehiculo : vehiculos) {
                //Raiz de cada vehiculo
                Element elementoVehiculo = doc.createElement("vehiculo");
                listaVehiculos.appendChild(elementoVehiculo);

                //Matricula
                Element matricula = doc.createElement("matricula");
                matricula.appendChild(doc.createTextNode(vehiculo.getMatricula()));
                elementoVehiculo.appendChild(matricula);

                //Marca
                Element marca = doc.createElement("marca");
                marca.appendChild(doc.createTextNode(vehiculo.getMarca()));
                elementoVehiculo.appendChild(marca);

                //Modelo
                Element modelo = doc.createElement("modelo");
                modelo.appendChild(doc.createTextNode(vehiculo.getModelo()));
                elementoVehiculo.appendChild(modelo);

                //Año
                Element anyo = doc.createElement("anyo");
                anyo.appendChild(doc.createTextNode(String.valueOf(vehiculo.getAnyo())));
                elementoVehiculo.appendChild(anyo);

                //Kilometraje
                Element kilometraje = doc.createElement("kilometraje");
                kilometraje.appendChild(doc.createTextNode(String.valueOf(vehiculo.getKilometraje())));
                elementoVehiculo.appendChild(kilometraje);

                //Categoria
                Element categoria = doc.createElement("categoria");
                categoria.appendChild(doc.createTextNode(vehiculo.getCategoria()));
                elementoVehiculo.appendChild(categoria);
            }

            //Transformar el árbol DOM en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();


            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivo);

            transformer.transform(source, result);
            
        } catch (ParserConfigurationException | TransformerException | NullPointerException e) {
            System.out.println("Error al generar el XML: " + e.getMessage());
        } catch (IOException ex) {
            System.getLogger(GestorReportesXML.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    /**
     * Leer el archivo XML conteniendo la lista de vehiculos
     * @param archivo de donde leer el archivo XML
     * @return Lista unica (contenido no se repite) de las categorias de mantenimineto de los vehiculos
     */
    public static HashSet<String> lecturaSAX(File archivo) {
        HashSet<String> categorias = new HashSet<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                private String elementoActual = "";

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    elementoActual = qName;
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {

                    if ("categoria".equals(elementoActual)) {
                        String categoria = new String(ch, start, length).trim();

                        if (!categoria.isEmpty()) {
                            categorias.add(categoria);
                        }
                    }
                }

                @Override
                public void endElement(String uri, String localName,String qName) throws SAXException {
                    elementoActual = "";
                }
            };

            //Procesar el XML mediante SAX
            parser.parse(archivo, handler);

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error al leer el XML: " + e.getMessage());
        }
        
        return categorias;
    }
}

