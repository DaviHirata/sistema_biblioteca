package br.csi.sistema_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "Livros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa um livro no sistema")
public class Livro {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id do livro", example = "1")
    private Long id;
    @UuidGenerator
    @Schema(description = "Id único universal", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID uuid;
    @NonNull
    @Schema(description = "Título do livro", example = "Senhor dos Aneis: A Sociedade do Anel")
    private String titulo;
    @NonNull
    @Schema(description = "Nome da editora do livro", example = "HarperCollins")
    private String editora;
    @NonNull
    @Schema(description = "Ano da publicação do livro", example = "2019")
    private int ano_publicacao;
    @NonNull
    @Size(max = 17, message = "ISBN deve ter 17 dígitos no máximo")
    @Schema(description = "ISBN do produto", example = "978-8595086357")
    private String isbn;
    @NonNull
    @Schema(description = "Categoria do livro", example = "Fantasia")
    private String categoria;
    @NonNull
    @Schema(description = "Quantidade de livros disponíveis para serem emprestados", example = "20")
    private int quantidade_disponivel;
    @NonNull
    @Schema(description = "Descrição do livro", example = "The Fellowship of the Ring é o primeiro volume da trilogia O Senhor dos Anéis, escrita pelo professor e filólogo britânico J. R. R. Tolkien.")
    private String descricao;
}
