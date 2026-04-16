package org.MiguelSantizo.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;

    @NotBlank(message = "El nombre del cliente es obligatorio")

    /* length estalece limites/longitud  maxima de los caracteres permitidos
    en la base de datos */

    @Column(name = "nombre_cliente", length = 50)
    private String nombre;

    @NotBlank(message = "El apellido del cliente es obligatorio")
    @Column(name = "apellido_cliente", length = 50)
    private String apellido;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado")
    private Integer estado;

    public Cliente() {
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}