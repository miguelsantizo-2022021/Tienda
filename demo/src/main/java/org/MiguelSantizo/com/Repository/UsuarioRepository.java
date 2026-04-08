package org.MiguelSantizo.com.repository;

import org.MiguelSantizo.com.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsernameAndPasswordAndEstado(String username, String password, Integer estado);
}