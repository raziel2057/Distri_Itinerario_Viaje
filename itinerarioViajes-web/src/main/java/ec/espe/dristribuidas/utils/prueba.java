/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.utils;

/**
 *
 * @author ale
 */
public class prueba {
    public static void main(String[] args) {
        Correo correo = new Correo();
        //correo.EnviarCorreoConArchivoAdjunto("aledennis.93@gmail.com", "aledennis.93@gmail.com", "Esto es una prueba",
         //       "d:/ISO38500.pdf", "ISO38500.pdf");
        correo.EnviarCorreoSinArchivoAdjunto("aledennis.93@gmail.com", "Esto es una prueba", "Activicación de cuenta SAIV</h1>"+
                            "<h2>Felicidades, su cuenta ha sido activada. Se le ha asignado la siguiente clave automaticamente: </h2>"+
                            "<h2>Por favor ingrese con el usuario que registro y esta clave. Posteriormente diríjae a la pestaña de"+
                            "Actualizar datos y cambie su clave</h2>");
        
    }
    
}
