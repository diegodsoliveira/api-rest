package com.diego.spring.restfull.repository;

import com.diego.spring.restfull.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario where u.login = ?1")
    Usuario findUserByLogin(String login);
}
