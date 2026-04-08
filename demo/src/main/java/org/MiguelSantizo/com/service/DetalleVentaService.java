package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.DetalleVenta;
import java.util.List;

public interface DetalleVentaService {
    List<DetalleVenta> getAllDetalles();
    DetalleVenta getDetalleById(Integer id);
    DetalleVenta saveDetalle(DetalleVenta detalle);
    boolean deleteDetalle(Integer id);
}