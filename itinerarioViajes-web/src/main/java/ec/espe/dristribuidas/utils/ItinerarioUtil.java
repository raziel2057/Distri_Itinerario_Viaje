/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.utils;

import ec.espe.dristribuidas.modelo.Frecuencia;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.jfree.data.time.Hour;

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
    
    public BigDecimal costoTotal()
    {
        double costoT=0;
        for(int i=0; i<this.frecuencias.size();i++)
        {
            costoT+=this.frecuencias.get(i).getRuta().getCosto().doubleValue();
        }
        return BigDecimal.valueOf(costoT);
    }
    
    public BigDecimal distanciaTotal()
    {
        double distanciaT=0;
        for(int i=0; i<this.frecuencias.size();i++)
        {
            distanciaT+=this.frecuencias.get(i).getRuta().getKilometros().doubleValue();
        }
        return BigDecimal.valueOf(distanciaT);
    }
    
    public String tiempoTotal()
    {
        String cadena;
        long tiempoHoras=0;
        long tiempoDias=0;
        for(int i=0; i<this.frecuencias.size();i++)
        {
            tiempoHoras+=this.frecuencias.get(i).getRuta().getTiempoHoras().doubleValue();
        }
        for(int i=0; i<(this.frecuencias.size()-1);i++)
        {
          
            try {
			

			long diff = this.frecuencias.get(i+1).getFechaSalida().getTime()-this.frecuencias.get(i).getFechaLlegada().getTime();

			long diffSeconds = diff / 1000 % 60;
			long diffMinutes = diff / (60 * 1000) % 60;
			long diffHours = diff / (60 * 60 * 1000) % 24;
			long diffDays = diff / (24 * 60 * 60 * 1000);

			System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");
                        
                        tiempoHoras+=diffHours;
                        tiempoDias+=diffDays;
                        
		} catch (Exception e) {
			e.printStackTrace();
		}
            
 
        }
        
        if(tiempoHoras>=24)
        {
            long tiempoHorasAux = tiempoHoras%24;
            tiempoDias+=(tiempoHoras-tiempoHorasAux)/24;
            tiempoHoras=tiempoHorasAux;
        }
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        return "F.S: "+dt.format(this.frecuencias.get(0).getFechaSalida())+" Días: "+tiempoDias+" Horas: "+tiempoHoras;
    }

    @Override
    public String toString() {
        String cadena = "";
        for(int i=0; i<this.frecuencias.size();i++)
        {
            cadena+=this.frecuencias.get(i).getRuta().getLugarSalida().getNombre() + "-->"+this.frecuencias.get(i).getRuta().getLugarDestino().getNombre()+" ";
        }
        return cadena;
    }


    
    
  
}
