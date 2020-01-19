package br.com.minhasfinancas.minhasfinancas.usuario._model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Builder
@Data // LOMBOK - Equivale a todos os métodos GET and SET.
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;
}
