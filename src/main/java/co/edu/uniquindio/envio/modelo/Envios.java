package co.edu.uniquindio.envio.modelo;
import co.edu.uniquindio.envio.modelo.enums.Ciudad;
import co.edu.uniquindio.envio.modelo.enums.TipEstado;
import co.edu.uniquindio.envio.modelo.enums.TipoEnvio;
import co.edu.uniquindio.envio.utils.Persistencia;
import lombok.Getter;
import org.example.servicio.EnvioServicio;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
public class Envios implements EnvioServicio, Serializable {
    private final ArrayList<Persona> personas;
    private final ArrayList<Paquete> paquetes;
    private final  ArrayList<EnvioHistorico> envioHistory;
    private  final ArrayList<Factura> facturas;

    public Envios() throws Exception {
        this.personas = new ArrayList<>();
        this.paquetes = new ArrayList<>();
        this.envioHistory = new ArrayList<>();
        this.facturas = new ArrayList<>();
//        llenarDatosPrueba();
        cargarDatos();
    }

    private void llenarDatosPrueba(){
        try{
            agregarPersonas("1003534487","anye","Mz R","Armenia","3107316994","anyiedcondiza@gmail.com");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Persona agregarPersonas(String cedula,String nombre,String direccion,String ciudad,String numero,String correo)throws Exception{
        if(cedula == null || cedula.isBlank()){
            throw new Exception("El número de identificación es obligatorio");
        }

        if(nombre == null || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(direccion == null || direccion.isBlank()){
            throw new Exception("La dirección es obligatoria");
        }

        if(ciudad == null || ciudad.isBlank()){
            throw new Exception("La ciudad es obligatoria");
        }

        Persona persona = Persona.builder()
                .cedula(cedula)
                .nombre(nombre)
                .direccion(direccion)
                .ciudad(ciudad)
                .numero(numero)
                .correo(correo)
                .build();

        personas.add(persona);
        guardarDatos();
        return persona;
    }
    @Override
    public void guardarDatos() throws Exception {
        try {
            Persistencia persistencia = new Persistencia();
            persistencia.guardarPersonas(personas);
            Persistencia.guardarFacturas(facturas);
            persistencia.guardarEnviosHistory(envioHistory);
            Persistencia.guardarPaquetes(paquetes);

        } catch (IOException e) {
            throw new Exception(e);
        }
    }
    @Override
    public void cargarDatos() throws Exception {
        try {
            Persistencia persistencia = new Persistencia();
            ArrayList<Persona> personasCargadas = persistencia.cargarPersonas();
            ArrayList<Factura> facturasCargadas = persistencia.cargarFacturas();
            ArrayList<EnvioHistorico> enviosCargados = persistencia.cargarEnvioHistoricos();

            if(personasCargadas != null){
                personas.addAll(personasCargadas);
            }

            if(facturasCargadas != null){
                facturas.addAll(facturasCargadas);
            }

            if(enviosCargados != null){
                envioHistory.addAll(enviosCargados);
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public Persona obtenerPersonas(String cedula){
        for(int perso = 0; perso < personas.size(); perso ++){
            if(personas.get(perso).getCedula().equals(cedula)){
                return personas.get(perso);
            }
        }

        return null;
    }
    public void actualizarPersona(String cedula,String nombre,String direccion,String ciudad,String numero,String correo)throws Exception{
        if(cedula == null || cedula.isBlank()){
            throw new Exception("El número de identificación es obligatorio");
        }

        if(nombre == null || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(direccion == null || direccion.isBlank()){
            throw new Exception("La dirección es obligatoria");
        }

        if(ciudad == null || ciudad.isBlank()){
            throw new Exception("La ciudad es obligatoria");
        }
        for (int perso = 0; perso < personas.size(); perso ++){
            if(personas.get(perso).getCedula().equals(cedula)){
                Persona persona = new Persona(cedula, nombre, direccion, ciudad, numero, correo);
                personas.set(perso, persona);
                guardarDatos();
                break;
            }
        }
    }

    public Paquete agregarPaquete(String descripcion,String peso)throws Exception{
        if(descripcion == null || descripcion.isBlank() || descripcion ==""){
            throw new Exception("La descripción es obligatoria");
        }
        if(peso == null || peso.isBlank() || peso ==""){
            throw new Exception("El peso es obligatorio");
        }
        float pesoConvert = Float.parseFloat(peso);
        Paquete paquete = Paquete.builder()
                .descripcion(descripcion)
                .peso(pesoConvert)
                .build();
        paquetes.add(paquete);
        guardarDatos();
        return paquete;
    }

    private static final double PRECIO_BASE_EXPRESS = 10.00;
    private static final double PRECIO_BASE_ESTANDAR = 7.00;

    // Tarifas adicionales
    private static final double TARIFA_ADICIONAL_ESTANDAR = 2.00;
    private static final double TARIFA_ADICIONAL_EXPRESS = 3.00;
    private static final double TARIFA_DISTANCIA_ESTANDAR = 2.00;
    private static final double TARIFA_DISTANCIA_EXPRESS = 4.00;

    // Descuentos y promociones
    private static final double DESCUENTO_VOLUMEN_ESTANDAR = 0.10;
    private static final double DESCUENTO_VOLUMEN_EXPRESS = 0.15;

    // Impuestos y tasas
    private static final double IVA = 0.07;

    public double calcularPrecio(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes) throws Exception {
        alerta(distancia,tipo);
        double precioBase;
        double tarifaAdicional;
        double descuentoVolumen;

        // Seleccionar el precio base según el tipo de envío
        if (tipo == TipoEnvio.EXPRESS) {
            precioBase = PRECIO_BASE_EXPRESS;
            tarifaAdicional = TARIFA_ADICIONAL_EXPRESS;
            descuentoVolumen = DESCUENTO_VOLUMEN_EXPRESS;
        } else {
            precioBase = PRECIO_BASE_ESTANDAR;
            tarifaAdicional = TARIFA_ADICIONAL_ESTANDAR;
            descuentoVolumen = DESCUENTO_VOLUMEN_ESTANDAR;
        }

        // Calcular tarifa adicional por peso
        double pesoAdicional = Math.max(0, peso - (tipo == TipoEnvio.EXPRESS ? 2.0 : 2.5));
        double tarifaPesoAdicional = pesoAdicional * tarifaAdicional;

        // Calcular tarifa adicional por distancia
        double tarifaDistancia = (distancia > 500) ?
                (tipo == TipoEnvio.EXPRESS ? TARIFA_DISTANCIA_EXPRESS : TARIFA_DISTANCIA_ESTANDAR) : 0.0;

        // Calcular descuento por volumen
        double descuento = (cantidadPaquetes > 10) ? descuentoVolumen * precioBase : 0.0;

        // Aplicar descuento
        double precioSubtotal = precioBase + tarifaPesoAdicional + tarifaDistancia - descuento;

        // Calcular impuestos
        double impuestos = precioSubtotal * IVA;

        // Calcular precio total
        double precioTotal = precioSubtotal + impuestos;

        return precioTotal;
    }
    public double calcularPericoSubTotal(float distancia, TipoEnvio tipo, float peso, int cantidadPaquetes){
        double precioBase;
        double tarifaAdicional;

        // Seleccionar el precio base según el tipo de envío
        if (tipo == TipoEnvio.EXPRESS) {
            precioBase = PRECIO_BASE_EXPRESS;
            tarifaAdicional = TARIFA_ADICIONAL_EXPRESS;
        } else {
            precioBase = PRECIO_BASE_ESTANDAR;
            tarifaAdicional = TARIFA_ADICIONAL_ESTANDAR;
        }

        // Calcular tarifa adicional por peso
        double pesoAdicional = Math.max(0, peso - (tipo == TipoEnvio.EXPRESS ? 2.0 : 2.5));
        double tarifaPesoAdicional = pesoAdicional * tarifaAdicional;

        // Calcular tarifa adicional por distancia
        double tarifaDistancia = (distancia > 500) ?
                (tipo == TipoEnvio.EXPRESS ? TARIFA_DISTANCIA_EXPRESS : TARIFA_DISTANCIA_ESTANDAR) : 0.0;

        double precioSubtotal = precioBase + tarifaPesoAdicional + tarifaDistancia ;

        double precioTotal = precioSubtotal ;

        return precioTotal;
    }
    public void alerta(float distancia, TipoEnvio tipo)throws Exception{
        if (distancia == 0.0|| tipo == null ) {
            throw new Exception("Todos los parámetros son obligatorios");
        }
    }
    public String generarCodigo(TipoEnvio tipo){
        String pref = null;
        String numeroAleatorio = generarNumeroAleatorio();
        if(tipo == TipoEnvio.EXPRESS){
            pref ="31";
        } else if (tipo == TipoEnvio.ESTÁNDAR) {
            pref ="21";
        }
        String code = pref+numeroAleatorio;
        String obtenerCode = obtenerCodigo(code);
        if(obtenerCode != null){
            numeroAleatorio = generarNumeroAleatorio();
            code = pref+numeroAleatorio;
        }
        return code;
    }
    private String generarNumeroAleatorio() {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(1000000);
        return String.format("%06d", numeroAleatorio);
    }
    public String obtenerCodigo(String codigo){
        String codigoEnv = null;
        for(int enviHist = 0; enviHist < envioHistory.size(); enviHist ++){
            if(envioHistory.get(enviHist).getCodigoEnvio().equals(codigo)){
                codigoEnv = envioHistory.get(enviHist).getCodigoEnvio();
            }
        }
        return codigoEnv;
    }
    public EnvioHistorico crearHistorial(String codigoEnvio, String remitente, String destinatario, List<Paquete> paquetes, TipoEnvio tipo, Ciudad ciudad, TipEstado estados, LocalDate fecha, float distancia, float valor)throws Exception{
        if(tipo ==null){
            throw new Exception("El Tipo es obligatorio");
        }
        EnvioHistorico envios =EnvioHistorico.builder()
                .codigoEnvio(codigoEnvio)
                .remitente(remitente)
                .destinatario(destinatario)
                .paquetes(paquetes)
                .ciudad(ciudad)
                .tipo(tipo)
                .estados(estados)
                .fecha(fecha)
                .distancia(distancia)
                .valor(valor)
                .build();
        envioHistory.add(envios);
        guardarDatos();
        return envios;
    }
    public Factura crearFactura(String codigoEnvio,String total,String subtotal) throws Exception {
        Factura factura= Factura.builder()
                .codigoEnvio(codigoEnvio)
                .total(total)
                .subtotal(subtotal)
                .build();
        facturas.add(factura);
        guardarDatos();
        return factura;
    }
    public List<EnvioHistorico> filtrarDatos(LocalDate fecha, TipoEnvio tipo, TipEstado estado) {
        List<EnvioHistorico> enviosFiltrados = new ArrayList<>();
        for (EnvioHistorico envio : envioHistory) {
            if (envio.getFecha().equals(fecha)) {
                if (envio.getTipo() == tipo) {
                    if (envio.getEstados() == estado) {
                        enviosFiltrados.add(envio);
                    }
                }
            }
        }
        return enviosFiltrados;
    }
    public List<EnvioHistorico> datos(){
        List<EnvioHistorico> enviosHito = new ArrayList<>();
        for (EnvioHistorico envioss : envioHistory){
            enviosHito.add(envioss);
        }
        return enviosHito;
    }
    public EnvioHistorico cargarEnvio(String codigo){
        for(EnvioHistorico envio : envioHistory){
            if(envio.getCodigoEnvio().equals(codigo)){
                return envio;
            }
        }
        return null;
    }

    public void actualizarEnvio(String codigo, TipEstado estado)throws Exception{
        if(estado == null){
            throw new Exception("el estado es obligatoria");
        }
        for (EnvioHistorico envio : envioHistory) {
            if (envio.getCodigoEnvio().equals(codigo)) {
                envio.setEstados(estado);
                guardarDatos();
                break;
            }
        }
    }
    public List<Paquete> obtenerPaquetesPorCodigo(String codigo) {
        List<Paquete> paquetesPorCodigo = new ArrayList<>();
        for (EnvioHistorico envio : envioHistory) {
            if (envio.getCodigoEnvio().equals(codigo)) {
                paquetesPorCodigo.addAll(envio.getPaquetes());
            }
        }
        return paquetesPorCodigo;
    }
    public List<Paquete> paquetesCargar() {
        List<Paquete> listaPaquetes = new ArrayList<>();
        for (Paquete paquete : paquetes) {
            listaPaquetes.add(paquete);
        }
        return listaPaquetes;
    }


}


















