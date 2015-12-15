/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.espe.dristribuidas.servicios;

import ec.espe.dristribuidas.dao.ClienteDAO;
import ec.espe.dristribuidas.modelo.Cliente;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author RAUL
 */
@LocalBean
@Stateless 

public class ClienteServicio {
    @EJB
    private ClienteDAO clienteDAO;
    
    public Cliente buscarClientePorUsuario(String usuario)
    {
        List<Cliente> clientes;
        Cliente cliente;
        Cliente clienteTmp = new Cliente();
        clienteTmp.setUsuario(usuario);
        clientes = this.clienteDAO.find(clienteTmp);
        if(clientes.size()==1)
        {
            cliente = clientes.get(0);
        }
        else
        {
            cliente = null;
        }
        
        return cliente;
    }

}
