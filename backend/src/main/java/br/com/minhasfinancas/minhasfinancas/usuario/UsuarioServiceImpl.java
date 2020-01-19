package br.com.minhasfinancas.minhasfinancas.usuario;

import br.com.minhasfinancas.minhasfinancas.enums.StatusLancamento;
import br.com.minhasfinancas.minhasfinancas.enums.TipoLancamento;
import br.com.minhasfinancas.minhasfinancas.exceptions.ErroAutenticacao;
import br.com.minhasfinancas.minhasfinancas.exceptions.RegraNegocios;
import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import br.com.minhasfinancas.minhasfinancas.lancamento.repository.LancamentoRepository;
import br.com.minhasfinancas.minhasfinancas.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;
    private LancamentoRepository lancamentoRepository;

    //Construtor
    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);
        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }

        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional //garante que todo processo deva ser executado com êxito, é “tudo ou nada”
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocios("Já existe um usuário cadastrado com esse Email.");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public BigDecimal obterSaldoPorUsuario(Long id) {
        BigDecimal receitas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO);
        BigDecimal despesas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO);

        if(receitas == null){
            receitas = BigDecimal.ZERO;
        }

        if(despesas == null){
            despesas = BigDecimal.ZERO;
        }

        return receitas.subtract(despesas);
    }


}
