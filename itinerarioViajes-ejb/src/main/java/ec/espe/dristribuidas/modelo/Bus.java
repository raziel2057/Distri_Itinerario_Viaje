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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Alejandra Ponce
 */
@Entity
@Table(name = "G6_BUS")
public class Bus implements Serializable {
    
    @Id
    @Column(name = "CODIGO_BUS", nullable = false)
    private String codigo;
    
    @Column(name = "CODIGO_EMPRESA", nullable = false)
    private String codigoEmpresa;
    
    @JoinColumn(name = "CODIGO_EMPRESA", referencedColumnName = "CODIGO_EMPRESA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Empresa empresa;
    
    @Column(name = "MATRICULA", nullable = false)
    private String matricula;
    
    @Column(name = "MARCA", nullable = false)
    private String marca;
    
    @Column(name = "MODELO", nullable = false)
    private String modelo;
    
    @Column(name = "ANIO_FABRICACION", nullable = false)
    private String anioFacbricacion;
    
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(String codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAnioFacbricacion() {
        return anioFacbricacion;
    }

    public void setAnioFacbricacion(String anioFacbricacion) {
        this.anioFacbricacion = anioFacbricacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final Bus other = (Bus) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bus{" + "codigo=" + codigo + ", codigoEmpresa=" + codigoEmpresa + ", matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", anioFacbricacion=" + anioFacbricacion + ", estado=" + estado + '}';
    }
    
    
    
}
