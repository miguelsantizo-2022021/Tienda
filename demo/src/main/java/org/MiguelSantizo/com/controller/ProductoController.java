package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/Productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listProductos(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/Login";

        List<Producto> lista = productoService.getAllProductos();
        model.addAttribute("listaProductos", lista);
        return "productos"; // Busca productos.html
    }

    @GetMapping("/eliminar/{id}")
    public String delete(@PathVariable Integer id) {
        productoService.deleteProducto(id);
        return "redirect:/Productos";
    }
}