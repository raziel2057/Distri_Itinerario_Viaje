/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.dao;


import ec.espe.dristribuidas.common.dao.DefaultGenericDAOImple;
import ec.espe.dristribuidas.modelo.Ruta;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Alejandra Ponce
 */
@LocalBean
@Stateless 
public class RutaDAO extends DefaultGenericDAOImple<Ruta, Integer>   {
    
    public RutaDAO() {
        super(Ruta.class);
    }
    
}
