/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_HOSPEDAJE")
public class Hospedaje implements Serializable{
    @Id
    @Column(name = "CODIGO_HOSPEDAJE", nullable = false)
    private Integer codigo;
    
    @Column(name = "CODIGO_LUGAR", nullable = false)
    private Integer codigoLugar;
    
    @JoinColumn(name = "CODIGO_LUGAR", referencedColumnName = "CODIGO_LUGAR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Lugar lugar;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "DIRECCION", nullable = false)
    private String direccion;
    
    @Column(name = "TELEFONO", nullable = false)
    private String telefono;
    
    @Column(name = "COSTO_NOCHE", nullable = false)
    private BigDecimal costoNoche;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoLugar() {
        return codigoLugar;
    }

    public void setCodigoLugar(Integer codigoLugar) {
        this.codigoLugar = codigoLugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getCostoNoche() {
        return costoNoche;
    }

    public void setCostoNoche(BigDecimal costoNoche) {
        this.costoNoche = costoNoche;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Hospedaje other = (Hospedaje) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hospedaje{" + "codigo=" + codigo + ", codigoLugar=" + codigoLugar + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono=" + telefono + ", costoNoche=" + costoNoche + '}';
    }
    
    
    
}
