package co.edu.uniquindio.envio.utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.example.servicio.Notificar;

public class EnvioSms implements Notificar {
    public String mensaje;
    public String numero;


    public void crearConexion() {
        Twilio.init(
                "ACb7b2a3fcf91809cc878ba7154d151d54",
                "f59a63d9c67bd40e119c34925d843bb7"
        );
    }


    @Override
    public void enviarNotificacion() {
        crearConexion();
        Message.creator(
                        new PhoneNumber("+57"+numero),
                        new PhoneNumber("+13144634534"),
                        mensaje)
                .create();
    }




}
