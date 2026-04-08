package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Usuario;
import org.MiguelSantizo.com.service.UsuarioService;
import org.springframework.stereotype.Controller; // CAMBIADO
import org.springframework.ui.Model; // NECESARIO PARA HTML
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller // CAMBIADO: @RestController NO sirve para HTML
@RequestMapping("/Usuarios") // Ruta para el navegador
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Método para mostrar la página HTML
    @GetMapping
    public String listUsuarios(Model model, HttpSession session) {
        // Protección de sesión
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/Login";
        }

        List<Usuario> lista = usuarioService.getAllUsuarios();
        model.addAttribute("listaUsuarios", lista);
        return "usuarios"; // Esto busca usuarios.html
    }

    // Método para eliminar y refrescar la página
    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/Usuarios";
    }
}