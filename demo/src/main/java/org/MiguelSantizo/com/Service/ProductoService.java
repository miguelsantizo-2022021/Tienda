package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Producto;
import java.util.List;

public interface ProductoService {
    List<Producto> getAllProductos();
    Producto getProductoById(Integer id);
    Producto saveProducto(Producto producto);
    Producto updateProducto(Integer id, Producto producto);
    boolean deleteProducto(Integer id);
}