package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.service.ProductoService;
import org.MiguelSantizo.com.service.ClienteService;
import org.MiguelSantizo.com.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Factura")
public class FacturaController {

    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final VentaService ventaService;


    private List<Producto> carrito = new ArrayList<>();

    public FacturaController(ProductoService productoService, ClienteService clienteService, VentaService ventaService) {
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.ventaService = ventaService;
    }

    @GetMapping("/nueva")
    public String nuevaFactura(Model model) {
        model.addAttribute("clientes", clienteService.getAllClientes());
        model.addAttribute("productos", productoService.getAllProductos());
        model.addAttribute("carrito", carrito);
        
        BigDecimal total = carrito.stream()
                .map(p -> p.getPrecio())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalFactura", total);
        return "form-factura";
    }

    @PostMapping("/agregar-producto")
    public String agregarAlCarrito(@RequestParam Integer idProducto, @RequestParam(defaultValue = "1") Integer cantidad) {
        Producto p = productoService.getProductoById(idProducto);
        if (p != null) {

            for (int i = 0; i < cantidad; i++) {
                carrito.add(p);
            }
        }
        return "redirect:/Factura/nueva";
    }

    @PostMapping("/limpiar")
    public String limpiarCarrito() {
        carrito.clear();
        return "redirect:/Factura/nueva";
    }

    @PostMapping("/finalizar")
    public String guardarVenta(@RequestParam Integer idCliente) {
        if (carrito.isEmpty()) {
            return "redirect:/Factura/nueva";
        }

        Venta nuevaVenta = new Venta();
        nuevaVenta.setFechaVenta(LocalDate.now());
        nuevaVenta.setEstado(1);

        Cliente clienteDb = clienteService.getClienteById(idCliente);
        nuevaVenta.setCliente(clienteDb);

        BigDecimal total = carrito.stream()
                .map(p -> p.getPrecio())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        nuevaVenta.setTotal(total);

        ventaService.saveVenta(nuevaVenta);

        for (Producto p : carrito) {
            if (p.getStock() > 0) {
                p.setStock(p.getStock() - 1);
                productoService.saveProducto(p);
            }
        }

        carrito.clear();

        return "redirect:/Ventas";
    }
}