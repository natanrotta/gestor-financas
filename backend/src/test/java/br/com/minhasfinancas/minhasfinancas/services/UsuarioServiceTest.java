package br.com.minhasfinancas.minhasfinancas.services;

import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import br.com.minhasfinancas.minhasfinancas.usuario.repository.UsuarioRepository;
import br.com.minhasfinancas.minhasfinancas.usuario.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    public void deveValidarEmail(){
        //Cenário
        usuarioRepository.deleteAll();

        //Execução
        service.validarEmail("natan@");

    }

    @Test
    public void deveMostrarErroAoValidarEmailQuandoExistirEmailCadastrado(){
        //Cenário
        Usuario usuario = Usuario.builder().nome("Natan").email("@").senha("123").build();

        //Acao
        service.validarEmail("natan@");
    }
}
