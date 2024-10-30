package br.csi.sistema_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Livros_Autores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que faz a conexão entre os livros e seus autores")
public class Livros_Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do registro da conexão entre um livro e um autor", example = "1")
    private Long id;
    @NonNull
    @Schema(description = "Id do livro para o registro", example = "1")
    private Long livro_id;
    @NonNull
    @Schema(description = "Id do autor para o registro", example = "1")
    private Long autor_id;
}