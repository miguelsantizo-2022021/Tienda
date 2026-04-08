package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.service.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public List<Venta> getAll() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Venta venta = ventaService.getVentaById(id);
        if (venta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: Venta con ID " + id + " no encontrada.");
        }
        return ResponseEntity.ok(venta);
    }

    @PostMapping
    public ResponseEntity<Venta> create(@RequestBody Venta venta) {
        Venta nueva = ventaService.saveVenta(venta);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Venta venta) {
        Venta actualizada = ventaService.updateVenta(id, venta);
        if (actualizada == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se pudo actualizar. La venta con ID " + id + " no existe.");
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!ventaService.deleteVenta(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No existe la venta con ID " + id);
        }
        return ResponseEntity.ok("Venta eliminada con éxito.");
    }
}