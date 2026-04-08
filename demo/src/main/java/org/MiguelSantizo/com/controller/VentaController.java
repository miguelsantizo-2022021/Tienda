package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listVentas(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/Login";

        List<Venta> lista = ventaService.getAllVentas();
        model.addAttribute("listaVentas", lista);
        return "ventas"; // Buscará ventas.html en templates
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id) {
        ventaService.deleteVenta(id);
        return "redirect:/Ventas";
    }
}