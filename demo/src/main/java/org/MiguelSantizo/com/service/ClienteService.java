package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> getAllClientes();
    Cliente getClienteById(Integer id);
    Cliente saveCliente(Cliente cliente);
    Cliente updateCliente(Integer id, Cliente cliente);
    boolean deleteCliente(Integer id);
}