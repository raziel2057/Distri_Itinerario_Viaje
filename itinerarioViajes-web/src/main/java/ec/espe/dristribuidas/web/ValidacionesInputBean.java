/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

/**
 *
 * @author ale
 */


public class ValidacionesInputBean {

    public static final int LONGITUD_CORREO = 30;

    public ValidacionesInputBean() {
    }

    public String validateEmail(String correo) {
        // Reqular expression pattern to validate the format submitted

        String validator = "^[_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+"
                + "(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";

        String retorno;

        if (correo.matches(validator)) {
            if (correo.length() <= LONGITUD_CORREO) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del correo excede el limite de "+LONGITUD_CORREO;
            }
        } else {
            retorno = "Correo no valido";
        }

        return retorno;
    }

    public String validateTextoSoloLetras(String texto, int longitudMaxima) { //Incluye letras en mayúscula, minúcula, espacios
        // Reqular expression pattern to validate the format submitted

        //String validator = "^\\p{L}+(?: \\p{L}+)*$";
        String validator = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ_\\s-]*$";

        String retorno;
        if (texto.matches(validator)) {
            if (texto.length() <= longitudMaxima ) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del texto excede el limite de "+longitudMaxima;
            }
        } else {
            retorno = "Texto no válido";
        }

        return retorno;
    }
    
    public String validateTextoLetrasNumerosCaracteresEspeciales(String texto, int longitudMaxima) { 
        // Reqular expression pattern to validate the format submitted

        String validator = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ.,;#_\\s-]*$";

        String retorno;
        if (texto.matches(validator)) {
            if (texto.length() <= longitudMaxima ) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del texto excede el limite de "+longitudMaxima;
            }
        } else {
            retorno = "Texto no válidos";
        }

        return retorno;
    }
    
    public String validateTextoLetrasNumeros(String texto, int longitudMaxima) { 
        // Reqular expression pattern to validate the format submitted

        String validator = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ_\\s-]*$";

        String retorno;
        if (texto.matches(validator)) {
            if (texto.length() <= longitudMaxima ) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del texto excede el limite de "+longitudMaxima;
            }
        } else {
            retorno = "Texto no válidos";
        }

        return retorno;
    }
    
    public String validateFecha(String fecha) { 
        // Reqular expression pattern to validate the format submitted

        String validator = "\\d{4}-\\d{2}-\\d{2}"; //formato "YYYY-MM-DD"

        String retorno;
        if (fecha.matches(validator)) {
                retorno = "se"; //Sin error.
        } else {
            retorno = "Texto no válidos";
        }

        return retorno;
    }
    
    public String validateNumeroEntero(String numero, int longitudMaxima) { 
        // Reqular expression pattern to validate the format submitted

        String validator = "\\d+";

        String retorno;
        if (numero.matches(validator)) {
            if (numero.length() <= longitudMaxima) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del numero excede el limite de "+longitudMaxima;
            }
        } else {
            retorno = "Numero no válido";
        }

        return retorno;
    }
    
    public String validateNumeroDecimal(String numero, int longitudMaxima) { // La longitud: Ejemplo 10. 7 para enteros, 3 decimales
        // Reqular expression pattern to validate the format submitted

        String validator = "[0-9]+(\\.[0-9][0-9]?)?";
        longitudMaxima=longitudMaxima+1;

        String retorno;
        if (numero.matches(validator)) {
            if (numero.length() <= longitudMaxima) {
                retorno = "se"; //Sin error.
            } else {
                retorno = "La longitud del numero excede el limite de "+longitudMaxima;
            }
        } else {
            retorno = "Numero no valido";
        }

        return retorno;
    }

    

}
