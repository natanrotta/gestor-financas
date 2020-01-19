package br.com.minhasfinancas.minhasfinancas.usuario;

import br.com.minhasfinancas.minhasfinancas.usuario.dto.UsuarioDTO;
import br.com.minhasfinancas.minhasfinancas.exceptions.ErroAutenticacao;
import br.com.minhasfinancas.minhasfinancas.exceptions.RegraNegocios;
import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import br.com.minhasfinancas.minhasfinancas.lancamento.LancamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioResource {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;

    @PostMapping("/autenticar")
    public ResponseEntity autenticar(@RequestBody UsuarioDTO dto){
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return new ResponseEntity(usuarioAutenticado, HttpStatus.OK);
        } catch (ErroAutenticacao e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Datos do JSON devem chegar com os mesmos nomes dos atributos da class DTO para serem convertidos;
    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto){
        Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocios r) {
            return ResponseEntity.badRequest().body(r.getMessage());
        }
    }

    @GetMapping("{id}/saldo")
    public ResponseEntity obterSaldo(@PathVariable("id") Long id){
        Optional<Usuario> usuario = service.obterPorId(id);

        if(!usuario.isPresent()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
        return ResponseEntity.ok(saldo);
    }
}
