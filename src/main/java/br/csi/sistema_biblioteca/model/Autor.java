package br.csi.sistema_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Autores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um autor no sistema")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do autor", example = "1")
    private Long autor_id;

    @UuidGenerator
    @Schema(description = "Id único universal", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID uuid;

    @NonNull
    @Schema(description = "Nome do autor", example = "John Ronald Reuel Tolkien")
    private String nome;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Livro> livro;
}