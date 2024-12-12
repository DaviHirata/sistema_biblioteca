package br.csi.sistema_biblioteca.dto;

import java.time.LocalDate;
import java.util.UUID;

public interface UsuarioDTO {
    UUID getUuid();
    String getNome();
    String getEmail();
    String getSenha();
    String getTelefone();
    String getTipo_Usuario();
    LocalDate getData_Nasc();
}
