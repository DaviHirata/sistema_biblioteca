package br.csi.sistema_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Entidade que representa um usuário no sistema")
public class Usuario {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do usuário", example = "1")
    private Long id;
    @UuidGenerator
    @Schema(description = "Id único universal", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID uuid;
    @NonNull
    @Schema(description = "Nome do usuário", example = "Luciano da Rocha Neves")
    private String nome;
    @NonNull
    @Schema(description = "Email do usuário", example = "luciano.neves@gmail.com")
    private String email;
    @NonNull
    @Size(min = 10, max = 11, message = "Número de telefone inválido. Não insira caracteres especiais")
    @Schema(description = "Número de telefone do usuário", example = "(55)99999-9999")
    private String telefone;
    /*@NonNull
    private String senha;*/
    @NonNull
    @Schema(description = "Tipo de conta do usuário", example = "Administrador/Cliente")
    private String tipo_usuario;
    @NonNull
    @Schema(description = "Data de nascimento do usuário", example = "2006-10-08")
    private LocalDate data_nasc;
}
