package br.csi.sistema_biblioteca.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "Reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Entidade que representa as reservas do usuário no sistema")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Id da reserva", example = "1")
    private Long reserva_id;

    @UuidGenerator
    @Schema(description = "Id único universal", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID uuid;

    @NonNull
    @Schema(description = "Data do empréstimo do livro", example = "2024-10-20")
    private LocalDate data_emprestimo;

    @NonNull
    @Schema(description = "Data prevista da devolução do livro", example = "2024-10-27")
    private LocalDate data_devolucao;

    @NonNull
    @Schema(description = "Data do real da devolução do livro", example = "2024-10-25")
    private LocalDate data_devolucao_real;

    @NonNull
    @Schema(description = "Status do empréstimo do livro", example = "Ativo/Devolvido/Atrasado")
    private String status;


    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
}