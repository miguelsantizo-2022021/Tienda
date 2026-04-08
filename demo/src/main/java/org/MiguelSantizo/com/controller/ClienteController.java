package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public String getAll(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/Login";

        // Traemos la lista de la base de datos
        List<Cliente> lista = clienteService.getAllClientes();
        model.addAttribute("clientes", lista);

        return "clientes"; // <--- ESTO DEBE LLAMARSE IGUAL QUE TU ARCHIVO .HTML
    }
}