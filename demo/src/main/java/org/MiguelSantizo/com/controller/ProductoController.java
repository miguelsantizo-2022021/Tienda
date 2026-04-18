package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/Productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        List<Producto> productos = productoService.getAllProductos();

        if (buscar != null && !buscar.isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getNombreProducto().toLowerCase().contains(buscar.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("listaProductos", productos);
        model.addAttribute("productoObj", new Producto());
        return "productos";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("productoObj") Producto producto) {
        productoService.saveProducto(producto);
        return "redirect:/Productos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        Producto producto = productoService.getProductoById(id);
        model.addAttribute("productoObj", producto);
        model.addAttribute("listaProductos", productoService.getAllProductos());
        return "productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttrs) {
        try {
            productoService.deleteProducto(id);
            redirectAttrs.addFlashAttribute("success", "Producto eliminado.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "No se puede eliminar el producto.");
        }
        return "redirect:/Productos";
    }
}