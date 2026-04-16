package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Venta> ventas = ventaService.getAllVentas();

        if (buscar != null && !buscar.isEmpty()) {
            ventas = ventas.stream()
                    .filter(v -> v.getCliente() != null &&
                            v.getCliente().getNombre().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("listaVentas", ventas);
        return "ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            ventaService.deleteVenta(id);
            redirectAttrs.addFlashAttribute("success", "Registro de venta eliminado.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al eliminar la venta.");
        }
        return "redirect:/Ventas";
    }
}