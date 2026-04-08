package org.MiguelSantizo.com.Service;

import org.MiguelSantizo.com.Entity.Cliente;
import org.MiguelSantizo.com.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@service
public class ClienteService {

    @autowired
    private ClienteRepository repository;

    @transactional
    public void crear(Cliente c) {
        repository.sp_agregarcliente(c.getNombre_cliente(), c.getApellido_cliente(), c.getDireccion(), c.getEstado());
    }

    public List<Cliente> listar() {
        return repository.sp_listarclientes();
    }

    @transactional
    public void actualizar(Cliente c) {
        repository.sp_actualizarcliente(c.getDpi_cliente(), c.getNombre_cliente(), c.getApellido_cliente(), c.getDireccion(), c.getEstado());
    }
}