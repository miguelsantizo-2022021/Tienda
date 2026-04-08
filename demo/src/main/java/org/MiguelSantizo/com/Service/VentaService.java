package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Venta;
import java.util.List;

public interface VentaService {
    List<Venta> getAllVentas();
    Venta getVentaById(Integer id);
    Venta saveVenta(Venta venta);
    Venta updateVenta(Integer id, Venta venta);
    boolean deleteVenta(Integer id);
}