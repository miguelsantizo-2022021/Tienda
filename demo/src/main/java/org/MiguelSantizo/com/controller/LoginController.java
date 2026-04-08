package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Usuario;
import org.MiguelSantizo.com.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/Login")
    public String showLogin() {
        return "Login";
    }

    @PostMapping("/Login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Usuario usuario = usuarioService.login(username, password);
        if (usuario != null) {
            session.setAttribute("usuarioLogueado", usuario);
            return "redirect:/Home";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "Login";
        }
    }

    @GetMapping("/Home")
    public String Home(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario == null) {
            return "redirect:/Login";
        }
        model.addAttribute("usuario", usuario);
        return "Home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/Login";
    }
}