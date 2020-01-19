package br.com.minhasfinancas.minhasfinancas.usuario.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String email;
    private String nome;
    private String senha;
}
