package org.MiguelSantizo.com.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Column(name = "username", length = 45)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password", length = 45)
    private String password;

    @Email(message = "Debe proporcionar un email válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(name = "email", length = 60)
    private String email;

    @NotBlank(message = "El rol es obligatorio")
    @Column(name = "rol", length = 45)
    private String rol;

    @NotNull(message = "El estado es obligatorio")
    @Column(name = "estado")
    private Integer estado;

    public Usuario() {
    }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public Integer getEstado() { return estado; }
    public void setEstado(Integer estado) { this.estado = estado; }
}