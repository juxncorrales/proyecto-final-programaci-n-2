package co.edu.uniquindio.envio.utils;

import co.edu.uniquindio.envio.modelo.EnvioHistorico;
import co.edu.uniquindio.envio.modelo.Factura;
import co.edu.uniquindio.envio.modelo.Paquete;
import co.edu.uniquindio.envio.modelo.Persona;

import java.io.*;
import java.util.ArrayList;

public class Persistencia {

    private static final String RUTA_PERSONAS = "src/main/resources/persistencia/dataPersonas.ser";
    private static final String RUTA_FACTURAS = "src/main/resources/persistencia/dataFacturas.ser";
    private  final String RUTA_ENVIOS = "src/main/resources/persistencia/dataEnvios.ser";
    private static final String RUTA_PAQUETES = "src/main/resources/persistencia/dataPaquete.ser";


    public void guardarPersonas(ArrayList<Persona> personas) throws IOException {
        serializarObjeto(RUTA_PERSONAS, personas);
    }
    public void guardarEnviosHistory(ArrayList<EnvioHistorico> enviosHistory) throws IOException {
        serializarObjeto(RUTA_ENVIOS, enviosHistory);
    }
    public static void guardarPaquetes(ArrayList<Paquete> paquete) throws IOException {
        serializarObjeto(RUTA_PAQUETES, paquete);
    }
    public static void guardarFacturas(ArrayList<Factura> facturas) throws IOException {
        serializarObjeto(RUTA_FACTURAS, facturas);
    }
    public ArrayList<Persona> cargarPersonas() throws Exception {
        return (ArrayList<Persona>) deserializarObjeto(RUTA_PERSONAS);
    }
    public ArrayList<EnvioHistorico> cargarEnvioHistoricos() throws Exception {
        return (ArrayList<EnvioHistorico>) deserializarObjeto(RUTA_ENVIOS);
    }
    public ArrayList<Factura> cargarFacturas() throws Exception {
        return (ArrayList<Factura>) deserializarObjeto(RUTA_FACTURAS);
    }

    /**
         * Metodo que permite serializar un objeto en un archivo en la ruta especificada
         * @param objeto Objeto a serializar
         * @throws IOException
         */
        private static void serializarObjeto(String ruta, Object objeto) throws IOException {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta));
            oos.writeObject(objeto);
            oos.close();
        }


        /**
         * Metodo que permite deserializar un objeto de un archivo en la ruta especificada
         * @return Objeto deserializado
         * @throws Exception
         */
        private Object deserializarObjeto(String ruta) throws Exception{


            if(!new File(ruta).exists()){
                return null;
            }


            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta));
            Object objeto = ois.readObject();
            ois.close();


            return objeto;
        }



}

