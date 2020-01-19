package br.com.minhasfinancas.minhasfinancas.lancamento._model;


import br.com.minhasfinancas.minhasfinancas.enums.StatusLancamento;
import br.com.minhasfinancas.minhasfinancas.enums.TipoLancamento;
import br.com.minhasfinancas.minhasfinancas.usuario._model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "lancamento")
@Builder
@Data //LOMBOK - Equivale a todos os métodos GET and SET e os demais...
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "mes")
    private Integer mes;

    @Column(name = "ano")
    private Integer ano;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "data_cadastro")
    private Date dataCadastro;


    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

}
