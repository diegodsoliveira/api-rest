package com.diego.spring.restfull.controller;

import com.diego.spring.restfull.model.Usuario;
import com.diego.spring.restfull.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findUserById(@PathVariable(value = "id") Long idUsuario) {

        return usuarioRepository.findById(idUsuario)
                .map(usuario -> ResponseEntity.ok().body(usuario))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário não encontrado"));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(usuarioRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario) {

        for (int i = 0; i < usuario.getTelefones().size(); i++) {
            usuario.getTelefones().get(i).setUsuario(usuario);
        }
        if (usuario.getSenha() != null) {
            usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        }
        return ResponseEntity.ok().body(usuarioRepository.save(usuario));
    }

    @PutMapping("/")
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {

        if (usuario.getId() != null) {
            Optional<Usuario> optional = usuarioRepository.findById(usuario.getId());

            if (optional.isPresent()) {
                for (int i = 0; i < usuario.getTelefones().size(); i++) {
                    usuario.getTelefones().get(i).setUsuario(usuario);
                }
                if (usuario.getSenha() != null && !optional.get().getSenha().equals(usuario.getSenha())) {
                    usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
                }
                return ResponseEntity.ok().body(usuarioRepository.save(usuario));
            }
        }
        return ResponseEntity.badRequest().body(new Usuario());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<Usuario> optional = usuarioRepository.findById(id);

        if (optional.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().body("Usuário " + optional.get().getLogin() + " deletado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Usuário não encontrado");
        }

    }
}
