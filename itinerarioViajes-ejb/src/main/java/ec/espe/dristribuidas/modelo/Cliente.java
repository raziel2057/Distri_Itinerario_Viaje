/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAUL
 */
@Entity
@Table(name = "G6_CLIENTE")
public class Cliente implements Serializable{
    
    @Id
    @SequenceGenerator(name = "G6_CLIENTE_SECUENCIA1", sequenceName = "G6_CLIENTE_SECUENCIA", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "G6_CLIENTE_SECUENCIA1")
    @Column(name = "CODIGO_CLIENTE", nullable = false)
    private Integer codigo;
    
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
    
    @Column(name = "IDENTIFICACION", nullable = false)
    private String identificacion;
    
    @Column(name = "DIRECCION", nullable = true)
    private String direccion;
    
    @Column(name = "TELEFONO", nullable = false)
    private String telefono;
    
    @Column(name = "USUARIO", nullable = false)
    private String usuario;
    
    @Column(name = "CLAVE", nullable = false)
    private String clave;
    
    @Column(name = "TIPO", nullable = false)
    private String tipo;
    
    @Column(name = "CORREO_ELECTRONICO", nullable = false)
    private String correoElectronico;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Cliente{" + "codigo=" + codigo + ", nombre=" + nombre + ", identificacion=" + identificacion + ", direccion=" + direccion + ", telefono=" + telefono + ", usuario=" + usuario + ", clave=" + clave + ", tipo=" + tipo + '}';
    }
    
    
}
