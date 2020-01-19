package br.com.minhasfinancas.minhasfinancas.repository;


import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import br.com.minhasfinancas.minhasfinancas.usuario.repository.UsuarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") //--> Direciona profile de teste (Banco em memória h2 (application-test.properties))
@DataJpaTest //faz com que cada teste seja feito dentro de um banco zerado, cada teste no final da um rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager entityManager;


//    @Test
//    public void deveVerificarExistenciaDeUmEmail(){
//        //Cenário
//        Usuario usuario = criarUsuario();
//        entityManager.persist(usuario);
//
//        //Execução
//        boolean result = usuarioRepository.existsByEmail("natan@@");
//
//        //Verificação
//        Assertions.assertThat(result).isTrue();
//    }

   // @Test
//    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComEmail(){
//        //Execução
//        boolean result = usuarioRepository.existsByEmail("natan@@");
//
//        //Verificação
//        Assertions.assertThat(result).isTrue();
//    }

    @Test
    public void deveSalvarUmUserNaBase(){
        //Cenário
        Usuario usuario = criarUsuario();

        //Execução
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        //Verifica
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUserPorEmail(){
        //Cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //Verifica
        Optional<Usuario> result = usuarioRepository.findByEmail("@@");
        Assertions.assertThat(result.isPresent()).isTrue();


    }

    @Test
    public void deveRetornarVazioQuandoUserNaoExisteNaBase(){
        //Cenário
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);

        //Verifica
        Optional<Usuario> result = usuarioRepository.findByEmail("123");
        Assertions.assertThat(result.isPresent()).isFalse();

    }

    public static Usuario criarUsuario(){
        return Usuario.builder().nome("Natan").email("@@").senha("123").build();
    }



}
