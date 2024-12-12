package br.csi.sistema_biblioteca.dto;

import java.time.LocalDate;
import java.util.UUID;

public interface ReservaDTO {
    UUID getUuid();
    LocalDate getDataEmprestimo();
    LocalDate getDataDevolucao();
    LocalDate getDataDevolucaoReal();
    String getStatus();
    Long getLivroId();
    Long getUsuarioId();
}
