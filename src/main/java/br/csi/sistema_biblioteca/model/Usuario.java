package br.csi.sistema_biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
@Table(name = "Usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Usuario {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nome;
    @NonNull
    private String email;
    @NonNull
    private String telefone;
    /*@NonNull
    private String senha;*/
    @NonNull
    private String tipo_usuario;
    @NonNull
    private LocalDate data_nasc;
}
