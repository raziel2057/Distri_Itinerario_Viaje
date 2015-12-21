/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.web;

import java.io.Serializable;

/**
 *
 * @author H200
 */
public class BaseBean implements Serializable{
    
    private boolean enNuevo;
    private boolean enModificar;
    private boolean enEliminar;

    public boolean isEnNuevo() {
        return enNuevo;
    }

    public void setEnNuevo(boolean enNuevo) {
        this.enNuevo = enNuevo;
    }

    public boolean isEnModificar() {
        return enModificar;
    }

    public void setEnModificar(boolean enModificar) {
        this.enModificar = enModificar;
    }

    public boolean isEnEliminar() {
        return enEliminar;
    }

    public void setEnEliminar(boolean enEliminar) {
        this.enEliminar = enEliminar;
    }

    public void nuevo() {
        this.enNuevo = true;
    }
    
    public void modificar() {
        this.enModificar = true;
    }
    
    public void eliminar() {
        this.enEliminar = true;
    }
    
    public void cancelar() {
        this.reset();
    }
    
    public void reset() {
        this.enModificar = false;
        this.enNuevo = false;
        this.enEliminar=false;
    }
}
