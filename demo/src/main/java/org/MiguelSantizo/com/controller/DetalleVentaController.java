package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.DetalleVenta;
import org.MiguelSantizo.com.service.DetalleVentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Detalles")
public class DetalleVentaController {

    private final DetalleVentaService detalleService;

    public DetalleVentaController(DetalleVentaService detalleService) {
        this.detalleService = detalleService;
    }

    @GetMapping
    public String listDetalles(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/Login";

        List<DetalleVenta> lista = detalleService.getAllDetalles();
        model.addAttribute("listaDetalles", lista);
        return "detalles";
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id) {
        detalleService.deleteDetalle(id);
        return "redirect:/Detalles";
    }
}