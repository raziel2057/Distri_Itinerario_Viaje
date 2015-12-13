/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Alejandra Ponce
 */
@Entity
@Table(name = "G6_EMPRESA")
public class Empresa implements Serializable {
    
    @Id
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "CODIGO_EMPRESA", nullable = false, length = 13)
    private String codigo;
    
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE", nullable = false, length = 100)
    private String nombre;
    
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "TELEFONO", nullable = false, length = 10)
    private String telefono;
    
    @Size(min = 0, max = 50)
    @Column(name = "DIRECCION", nullable = true, length = 50)
    private String direccion;
    
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CORREO_ELECTRONICO", nullable = false, length = 10)
    private String correoElectronico;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Empresa other = (Empresa) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Empresa{" + "codigo=" + codigo + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion + ", correoElectronico=" + correoElectronico + '}';
    }
    
    
    
    
    
}
