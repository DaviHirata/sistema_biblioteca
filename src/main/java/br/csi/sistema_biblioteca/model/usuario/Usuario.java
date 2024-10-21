package br.csi.sistema_biblioteca.model.usuario;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

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
    @UuidGenerator
    private UUID uuid;
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
