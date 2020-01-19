package br.com.minhasfinancas.minhasfinancas.lancamento;

import br.com.minhasfinancas.minhasfinancas.enums.StatusLancamento;
import br.com.minhasfinancas.minhasfinancas.enums.TipoLancamento;
import br.com.minhasfinancas.minhasfinancas.exceptions.RegraNegocios;
import br.com.minhasfinancas.minhasfinancas.lancamento._model.Lancamento;
import br.com.minhasfinancas.minhasfinancas.lancamento.repository.LancamentoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository lancamentoRepository;

    public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    @Override
    @Transactional //Abre uma transição. Caso der bom ele da um commit, caso de ruim ele da um rollback
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.PENDENTE);
        //implementar validação
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId()); //Garante que tem um id, para não dar NullPointer
        validar(lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        Objects.requireNonNull(lancamento.getId()); //Garante que tem um id, para não dar NullPointer
        lancamentoRepository.delete(lancamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
        //Leva em consideração apenas os atributos populados.
        //Usando um método que ignora o Case das letras que o usuário informou
        Example example = Example.of(lancamentoFiltro, ExampleMatcher
                .matching().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return lancamentoRepository.findAll(example);
    }

    @Override
    @Transactional //garante que todo processo deva ser executado com êxito, é “tudo ou nada”
    public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
        lancamento.setStatus(statusLancamento); //atualiza o status do objeto.
        atualizar(lancamento);
    }

    @Override
    @Transactional //garante que todo processo deva ser executado com êxito, é “tudo ou nada”
    public void validar(Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocios("Informe uma Descrição válida!");
        }

        if (lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
            throw new RegraNegocios("Informe um Mês válido!");
        }

        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocios("Informe um Ano válido!");
        }

        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
            throw new RegraNegocios("Informe o usuário!");
        }

        if (lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
            throw new RegraNegocios("Informe um Valor maior que 0(zero)!");
        }

        if (lancamento.getTipo() == null) {
            throw new RegraNegocios("Informe um Tipo!");
        }
    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return lancamentoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal obterSaldoPorUsuario(Long id) {
        BigDecimal receitas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RECEITA, StatusLancamento.EFETIVADO);
        BigDecimal despesas = lancamentoRepository.obterSaldoPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.DESPESA, StatusLancamento.EFETIVADO);

        if (receitas == null) {
            receitas = BigDecimal.ZERO;
        }

        if (despesas == null) {
            despesas = BigDecimal.ZERO;
        }
        return receitas.subtract(despesas);
    }
}
