package br.csi.sistema_biblioteca.model;

import jakarta.persistence.*;
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
public class Livro {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @UuidGenerator
    private UUID uuid;
    @NonNull
    private String titulo;
    @NonNull
    private String editora;
    @NonNull
    private int ano_publicacao;
    @NonNull
    private String isbn;
    @NonNull
    private String categoria;
    @NonNull
    private int quantidade_disponivel;
    @NonNull
    private String descricao;
}
