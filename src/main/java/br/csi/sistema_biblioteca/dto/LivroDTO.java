package br.csi.sistema_biblioteca.dto;

import java.util.UUID;

public interface LivroDTO {
    UUID getUuid();
    String getTitulo();
    String getEditora();
    Integer getAnoPublicacao();
    String getIsbn();
    String getCategoria();
    Integer getQuantidadeDisponivel();
    String getDescricao();
}
