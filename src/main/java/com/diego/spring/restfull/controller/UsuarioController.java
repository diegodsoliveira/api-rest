package com.diego.spring.restfull.controller;

import com.diego.spring.restfull.model.Usuario;
import com.diego.spring.restfull.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable(value = "id") Long idUsuario) {

        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);

        return optional.<ResponseEntity<Object>>map(usuario -> ResponseEntity.ok().body(usuario)).orElseGet(() -> ResponseEntity.badRequest().body("Usuário não encontrado"));
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(usuarioRepository.findAll());
    }

    @PostMapping("/")
    @ResponseBody
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioRepository.save(usuario));
    }

    @PutMapping("/")
    @ResponseBody
    public ResponseEntity<Usuario> update(@RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioRepository.save(usuario));
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
