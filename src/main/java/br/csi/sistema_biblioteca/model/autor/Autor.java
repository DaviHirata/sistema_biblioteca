package br.csi.sistema_biblioteca.model.autor;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Autores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String nome;
}
