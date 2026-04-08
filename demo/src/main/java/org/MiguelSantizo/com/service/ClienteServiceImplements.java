package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImplements implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImplements(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getClienteById(Integer id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Integer id, Cliente cliente) {
        return clienteRepository.findById(id).map(existente -> {
            existente.setNombre(cliente.getNombre());
            existente.setApellido(cliente.getApellido());
            existente.setDireccion(cliente.getDireccion());
            existente.setEstado(cliente.getEstado());
            return clienteRepository.save(existente);
        }).orElse(null);
    }

    @Override
    public boolean deleteCliente(Integer id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}