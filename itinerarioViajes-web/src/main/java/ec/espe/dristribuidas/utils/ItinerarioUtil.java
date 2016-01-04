/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.utils;

import ec.espe.dristribuidas.modelo.Frecuencia;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAUL
 */
public class ItinerarioUtil {
    private List<Frecuencia> frecuencias;

    public ItinerarioUtil() {
        this.frecuencias = new ArrayList<>();
    }
    
    

    public List<Frecuencia> getFrecuencias() {
        return frecuencias;
    }

    public void setFrecuencias(List<Frecuencia> frecuencias) {
        this.frecuencias = frecuencias;
    }
    
    
}
