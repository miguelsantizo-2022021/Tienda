package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Usuario;
import java.util.List;

public interface UsuarioService {
    List<Usuario> getAllUsuarios();
    Usuario getUsuarioById(Integer id);
    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsuario(Integer id, Usuario usuario);
    boolean deleteUsuario(Integer id);
    Usuario login(String username, String password);
}