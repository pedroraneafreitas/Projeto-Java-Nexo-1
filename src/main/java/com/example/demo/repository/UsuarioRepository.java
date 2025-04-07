package com.example.demo.repository;

import com.example.demo.objects.Usuarios.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
     Optional<Usuario> findByNomeUsuario(String usuarioNome);

     Optional<Usuario> findByNomeUsuarioAndUsuarioSenha(String usuarioNome,
                                                        String senhaUsuario);
}
