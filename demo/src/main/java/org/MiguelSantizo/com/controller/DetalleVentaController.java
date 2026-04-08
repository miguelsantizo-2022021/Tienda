package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.DetalleVenta;
import org.MiguelSantizo.com.service.DetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalles")
public class DetalleVentaController {

    private final DetalleVentaService detalleService;

    public DetalleVentaController(DetalleVentaService detalleService) {
        this.detalleService = detalleService;
    }

    @GetMapping
    public List<DetalleVenta> getAll() {
        return detalleService.getAllDetalles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        DetalleVenta detalle = detalleService.getDetalleById(id);
        if (detalle == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: El detalle con ID " + id + " no existe.");
        }
        return ResponseEntity.ok(detalle);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody DetalleVenta detalle) {
        if (detalle.getProducto() == null || detalle.getVenta() == null) {
            return ResponseEntity.badRequest().body("Error: Producto y Venta son campos obligatorios.");
        }
        DetalleVenta nuevo = detalleService.saveDetalle(detalle);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!detalleService.deleteDetalle(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se encontró el detalle con ID " + id);
        }
        return ResponseEntity.ok("Detalle eliminado exitosamente.");
    }
}