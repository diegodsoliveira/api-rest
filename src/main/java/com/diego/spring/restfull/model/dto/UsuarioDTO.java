package com.diego.spring.restfull.model.dto;

import com.diego.spring.restfull.model.Telefone;
import com.diego.spring.restfull.model.Usuario;

import java.io.Serializable;
import java.util.List;

public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 2421689001088321857L;

    private String username;
    private List<Telefone> phones;

    public UsuarioDTO(Usuario usuario) {
        this.username = usuario.getLogin();
        this.phones = usuario.getTelefones();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Telefone> getPhones() {
        return phones;
    }

    public void setPhones(List<Telefone> phones) {
        this.phones = phones;
    }
}
