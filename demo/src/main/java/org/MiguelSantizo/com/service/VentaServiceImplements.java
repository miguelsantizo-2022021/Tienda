package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Venta;
import org.MiguelSantizo.com.repository.VentaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class VentaServiceImplements implements VentaService {

    private final VentaRepository ventaRepository;

    public VentaServiceImplements(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> getAllVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta getVentaById(Integer id) {
        return ventaRepository.findById(id).orElse(null);
    }

    @Override
    public Venta saveVenta(Venta venta) {
        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDate.now());
        }
        return ventaRepository.save(venta);
    }

    @Override
    public Venta updateVenta(Integer id, Venta venta) {
        return ventaRepository.findById(id).map(existente -> {
            existente.setTotal(venta.getTotal());
            existente.setEstado(venta.getEstado());
            existente.setFechaVenta(venta.getFechaVenta());

            if (venta.getCliente() != null) {
                existente.setCliente(venta.getCliente());
            }
            if (venta.getUsuario() != null) {
                existente.setUsuario(venta.getUsuario());
            }

            return ventaRepository.save(existente);
        }).orElse(null);
    }

    @Override
    public boolean deleteVenta(Integer id) {
        if (ventaRepository.existsById(id)) {
            ventaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}