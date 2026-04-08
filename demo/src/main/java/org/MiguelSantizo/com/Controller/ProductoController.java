package org.MiguelSantizo.com.controller;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> getAll() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: El producto con ID " + id + " no existe.");
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody Producto producto) {
        Producto nuevo = productoService.saveProducto(producto);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody Producto producto) {
        Producto actualizado = productoService.updateProducto(id, producto);
        if (actualizado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se pudo actualizar. ID no encontrado.");
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!productoService.deleteProducto(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Producto no encontrado.");
        }
        return ResponseEntity.ok("Producto eliminado correctamente.");
    }
}