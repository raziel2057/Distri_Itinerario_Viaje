/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;


import ec.espe.dristribuidas.dao.EmpresaDAO;
import ec.espe.dristribuidas.exception.ValidacionException;
import ec.espe.dristribuidas.modelo.Empresa;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alejandra Ponce
 */
@LocalBean
@Stateless 
public class EmpresaServicio {
    @EJB
    private EmpresaDAO empresaDAO;
    
    public List<Empresa> obtenerTodas(){
        return this.empresaDAO.findAll();
    }
    
    public Empresa obtenerPorID(String codigoEmpresa){
        return this.empresaDAO.findById(codigoEmpresa, false);
    }
   public void crearEmpresa(Empresa empresa) throws ValidacionException {
        Empresa empresaTmp=this.obtenerPorID(empresa.getCodigo());
        if(empresaTmp==null){
            this.empresaDAO.insert(empresa);
        }
        else{
        throw new ValidacionException("El codigo es "+empresa.getCodigo()+" ya existe"); 
        }
        
    }
    public void actualiarEmpresa(Empresa empresa){
        this.empresaDAO.update(empresa);
    }
    
    public void eliminarEmpresa(String codigoEmpresa){
    try{
     Empresa empresaTmp=this.obtenerPorID(codigoEmpresa);
     this.empresaDAO.remove(empresaTmp);
    }catch(Exception e)
    {
        throw new ValidacionException("la sede "+codigoEmpresa+" esta asociada a un candidado");
    }
    }
    
    
}
