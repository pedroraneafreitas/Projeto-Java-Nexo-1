package com.example.demo.services;

import com.example.demo.objects.Usuarios.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> getAll(){
        return repository.findAll();
    }

    public Optional<Usuario> findByNomeUsuario(String nome){
        return repository.findByNomeUsuario(nome);
    }
}
