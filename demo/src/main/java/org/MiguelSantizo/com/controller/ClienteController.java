package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Validated
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: El cliente con ID " + id + " no existe en la base de datos.");
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getApellido() == null) {
            return ResponseEntity.badRequest().body("Error: El nombre y apellido del cliente son obligatorios.");
        }

        Cliente nuevo = clienteService.saveCliente(cliente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getApellido() == null) {
            return ResponseEntity.badRequest().body("Error: Debe proporcionar datos válidos para actualizar al cliente.");
        }

        Cliente updated = clienteService.updateCliente(id, cliente);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se pudo actualizar. El cliente con ID " + id + " no existe.");
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        boolean eliminado = clienteService.deleteCliente(id);
        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se puede eliminar. El cliente con ID " + id + " no existe.");
        }
        return ResponseEntity.ok("Cliente con ID " + id + " eliminado correctamente.");
    }
}