package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.DetalleVenta;
import org.MiguelSantizo.com.entity.Producto;
import org.MiguelSantizo.com.repository.DetalleVentaRepository;
import org.MiguelSantizo.com.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class DetalleVentaServiceImplements implements DetalleVentaService {

    private final DetalleVentaRepository detalleRepository;
    private final ProductoRepository productoRepository;

    public DetalleVentaServiceImplements(DetalleVentaRepository detalleRepository, ProductoRepository productoRepository) {
        this.detalleRepository = detalleRepository;
        this.productoRepository = productoRepository;
    }

    @Override
    public List<DetalleVenta> getAllDetalles() {
        return detalleRepository.findAll();
    }

    @Override
    public DetalleVenta getDetalleById(Integer id) {
        return detalleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public DetalleVenta saveDetalle(DetalleVenta detalle) {
        if (detalle.getPrecioUnitario() != null && detalle.getCantidad() != null) {
            detalle.setSubtotal(detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())));
        }

        if (detalle.getProducto() != null) {
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto()).orElse(null);
            if (producto != null) {
                producto.setStock(producto.getStock() - detalle.getCantidad());
                productoRepository.save(producto);
            }
        }

        return detalleRepository.save(detalle);
    }

    @Override
    public boolean deleteDetalle(Integer id) {
        if (detalleRepository.existsById(id)) {
            detalleRepository.deleteById(id);
            return true;
        }
        return false;
    }
}