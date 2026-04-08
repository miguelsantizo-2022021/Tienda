package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoServiceImplements implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImplements(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getProductoById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto updateProducto(Integer id, Producto producto) {
        return productoRepository.findById(id).map(existente -> {
            existente.setNombreProducto(producto.getNombreProducto());
            existente.setPrecio(producto.getPrecio());
            existente.setStock(producto.getStock());
            existente.setEstado(producto.getEstado());
            return productoRepository.save(existente);
        }).orElse(null);
    }

    @Override
    public boolean deleteProducto(Integer id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}