package com.diego.spring.restfull.controller;

import com.diego.spring.restfull.model.Telefone;
import com.diego.spring.restfull.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Controller
@Transactional
@RequestMapping(value = "/telefones")
public class TelefoneController {

    @Autowired
    private TelefoneRepository telefoneRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<Telefone>> findAll() {
        return ResponseEntity.ok().body(telefoneRepository.findAll());
    }
}
