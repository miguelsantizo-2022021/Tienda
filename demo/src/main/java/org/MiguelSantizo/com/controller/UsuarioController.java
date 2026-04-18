package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Usuario;
import org.MiguelSantizo.com.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Usuario> usuarios = usuarioService.getAllUsuarios();

        if (buscar != null && !buscar.isEmpty()) {
            usuarios = usuarios.stream()
                    .filter(u -> u.getUsername().toLowerCase().contains(buscar.toLowerCase()) ||
                            u.getEmail().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("listaUsuarios", usuarios);
        model.addAttribute("usuarioObj", new Usuario());
        return "usuarios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("usuarioObj") Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/Usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        model.addAttribute("usuarioObj", usuario);
        model.addAttribute("listaUsuarios", usuarioService.getAllUsuarios());
        return "usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            usuarioService.deleteUsuario(id);
            redirectAttrs.addFlashAttribute("success", "Usuario eliminado.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se puede eliminar el usuario.");
        }
        return "redirect:/Usuarios";
    }
}