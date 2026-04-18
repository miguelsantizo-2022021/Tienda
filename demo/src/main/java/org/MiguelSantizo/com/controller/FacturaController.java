package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.entity.DetalleVenta;
import org.MiguelSantizo.com.entity.Cliente;
import org.MiguelSantizo.com.service.ProductoService;
import org.MiguelSantizo.com.service.ClienteService;
import org.MiguelSantizo.com.service.VentaService;
import org.MiguelSantizo.com.service.DetalleVentaService;
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
    private final DetalleVentaService detalleService;

    private List<Producto> carrito = new ArrayList<>();

    public FacturaController(ProductoService productoService, ClienteService clienteService,
                             VentaService ventaService, DetalleVentaService detalleService) {
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.ventaService = ventaService;
        this.detalleService = detalleService;
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
    public String agregarAlCarrito(@RequestParam Integer idProducto) {
        Producto p = productoService.getProductoById(idProducto);
        if (p != null) {
            carrito.add(p);
        }
        return "redirect:/Factura/nueva";
    }

    @PostMapping("/finalizar")
    public String guardarVenta(@RequestParam Integer idCliente) {
        if (carrito.isEmpty()) return "redirect:/Factura/nueva";

        // 1. Crear y guardar la Venta principal
        Venta venta = new Venta();
        venta.setFechaVenta(LocalDate.now());
        venta.setEstado(1);
        venta.setCliente(clienteService.getClienteById(idCliente));

        BigDecimal total = carrito.stream()
                .map(p -> p.getPrecio())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        venta.setTotal(total);

        Venta ventaGuardada = ventaService.saveVenta(venta);

        for (Producto p : carrito) {
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVenta(ventaGuardada);
            detalle.setProducto(p);
            detalle.setCantidad(1);
            detalle.setPrecioUnitario(p.getPrecio());
            detalleService.saveDetalle(detalle);
        }

        carrito.clear();
        return "redirect:/Ventas";
    }
}