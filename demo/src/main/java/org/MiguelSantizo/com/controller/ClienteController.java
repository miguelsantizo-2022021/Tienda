package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Cliente> clientes = clienteService.getAllClientes();

        if (buscar != null && !buscar.isEmpty()) {
            clientes = clientes.stream()
                    .filter(c -> c.getNombre().toLowerCase().contains(buscar.toLowerCase()) ||
                            c.getApellido().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("clientes", clientes);
        model.addAttribute("clienteObj", new Cliente());
        return "clientes";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("clienteObj") Cliente cliente) {
        clienteService.saveCliente(cliente);
        return "redirect:/Clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            boolean eliminado = clienteService.deleteCliente(id);
            if (!eliminado) {
                redirectAttrs.addFlashAttribute("error", "El cliente no existe.");
            } else {
                redirectAttrs.addFlashAttribute("success", "Cliente eliminado correctamente.");
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se puede eliminar: El cliente tiene historial de ventas.");
        }
        return "redirect:/Clientes";
    }
}