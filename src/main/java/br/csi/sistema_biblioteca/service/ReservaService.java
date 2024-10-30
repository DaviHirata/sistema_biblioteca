package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.Reserva;
import br.csi.sistema_biblioteca.repository.ReservasRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservaService {
    private final ReservasRepository reservasRepository;

    public ReservaService(ReservasRepository reservasRepository) {
        this.reservasRepository = reservasRepository;
    }

    public void salvarReserva(Reserva reserva) {
        this.reservasRepository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return this.reservasRepository.findAll();
    }

    public Reserva getReservaUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.reservasRepository.findReservasByUuid(uuidformatado);
    }

    @Transactional
    public void excluirReservaUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.reservasRepository.deleteReservasByUuid(uuidformatado);
    }

    public void atualizarReservaUuid(Reserva reserva) {
        Reserva r = this.reservasRepository.findReservasByUuid(reserva.getUuid());
        r.setId_usuario(reserva.getId_usuario());
        r.setId_livro(reserva.getId_livro());
        r.setData_emprestimo(reserva.getData_emprestimo());
        r.setData_devolucao(reserva.getData_devolucao());
        r.setData_devolucao_real(reserva.getData_devolucao_real());
        r.setStatus(reserva.getStatus());
        this.reservasRepository.save(r);
    }
}
