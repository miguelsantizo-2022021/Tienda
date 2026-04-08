package org.MiguelSantizo.com.service;

import org.MiguelSantizo.com.entity.Usuario;
import org.MiguelSantizo.com.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServiceImplements implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImplements(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Integer id, Usuario usuario) {
        return usuarioRepository.findById(id).map(existente -> {
            existente.setUsername(usuario.getUsername());
            existente.setPassword(usuario.getPassword());
            existente.setEmail(usuario.getEmail());
            existente.setRol(usuario.getRol());
            existente.setEstado(usuario.getEstado());
            return usuarioRepository.save(existente);
        }).orElse(null);
    }

    @Override
    public boolean deleteUsuario(Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Usuario login(String username, String password) {
        return usuarioRepository.findByUsernameAndPasswordAndEstado(username, password, 1).orElse(null);
    }
}