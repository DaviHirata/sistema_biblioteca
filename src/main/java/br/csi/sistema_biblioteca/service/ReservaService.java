package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.dto.ReservaDTO;
import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.model.Reserva;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.repository.LivrosRepository;
import br.csi.sistema_biblioteca.repository.ReservasRepository;
import br.csi.sistema_biblioteca.repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {
    private final ReservasRepository reservasRepository;
    private final UsuariosRepository usuariosRepository;
    private final LivrosRepository livrosRepository;

    public ReservaService(ReservasRepository reservasRepository, UsuariosRepository usuariosRepository, LivrosRepository livrosRepository) {
        this.reservasRepository = reservasRepository;
        this.usuariosRepository = usuariosRepository;
        this.livrosRepository = livrosRepository;
    }

    public void salvarReserva(Reserva reserva) {
        this.reservasRepository.save(reserva);
    }

    public List<ReservaDTO> listarReservas() {
        return this.reservasRepository.findAllReservasDTO();
    }

    public ReservaDTO getReservaUUID(UUID uuid) {
        return this.reservasRepository.findReservaDTOByUuid(uuid);
    }

    @Transactional
    public void excluirReservaUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.reservasRepository.deleteReservasByUuid(uuidformatado);
    }

    public void atualizarReservaUuid(ReservaDTO reservaDTO) {
        // Buscar a reserva existente pelo UUID
        Reserva reservaExistente = this.reservasRepository.findReservasByUuid(reservaDTO.getUuid());
        if (reservaExistente == null) {
            throw new EntityNotFoundException("Reserva não encontrada com o UUID fornecido.");
        }

        // Buscar o usuário e o livro pelos IDs fornecidos no DTO
        Usuario usuario = this.usuariosRepository.findById(reservaDTO.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com o ID fornecido."));
        Livro livro = this.livrosRepository.findById(reservaDTO.getLivroId())
                .orElseThrow(() -> new EntityNotFoundException("Livro não encontrado com o ID fornecido."));

        // Atualizar os atributos da reserva existente
        reservaExistente.setUsuario(usuario);
        reservaExistente.setLivro(livro);
        reservaExistente.setData_emprestimo(reservaDTO.getDataEmprestimo());
        reservaExistente.setData_devolucao(reservaDTO.getDataDevolucao());
        reservaExistente.setData_devolucao_real(reservaDTO.getDataDevolucaoReal());
        reservaExistente.setStatus(reservaDTO.getStatus());

        // Salvar a reserva atualizada
        this.reservasRepository.save(reservaExistente);
    }
}