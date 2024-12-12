package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.dto.ReservaDTO;
import br.csi.sistema_biblioteca.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservasRepository extends JpaRepository<Reserva, Long> {
    public Reserva findReservasByUuid(UUID uuid);
    public void deleteReservasByUuid(UUID uuid);

    @Query(value = "SELECT r.uuid AS uuid, r.data_emprestimo AS dataEmprestimo, " +
            "r.data_devolucao AS dataDevolucao, r.data_devolucao_real AS dataDevolucaoReal, " +
            "r.status AS status, l.titulo AS livroTitulo, u.nome AS usuarioNome " +
            "FROM reservas r " +
            "JOIN livro l ON r.livro_id = l.livro_id " +
            "JOIN usuario u ON r.usuario_id = u.usuario_id " +
            "WHERE r.uuid =: uuid", nativeQuery = true)
    ReservaDTO findReservaDTOByUuid(@Param("uuid") UUID uuid);


    @Query(value = "SELECT r.uuid AS uuid, r.data_emprestimo AS dataEmprestimo, " +
            "r.data_devolucao AS dataDevolucao, r.data_devolucao_real AS dataDevolucaoReal, " +
            "r.status AS status, l.titulo AS livroTitulo, u.nome AS usuarioNome " +
            "FROM reservas r " +
            "JOIN livros l ON r.livro_id = l.livro_id " +
            "JOIN usuarios u ON r.usuario_id = u.usuario_id", nativeQuery = true)
    List<ReservaDTO> findAllReservasDTO();

}