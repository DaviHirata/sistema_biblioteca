package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.model.Reserva;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.repository.LivrosRepository;
import br.csi.sistema_biblioteca.repository.ReservasRepository;
import br.csi.sistema_biblioteca.repository.UsuariosRepository;
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
        // Busca a reserva existente pelo UUID
        Reserva r = this.reservasRepository.findReservasByUuid(reserva.getUuid());
        if (r == null) {
            throw new IllegalArgumentException("Reserva não encontrada com o UUID fornecido.");
        }

        // Busca o usuário e o livro pelos IDs fornecidos
        Usuario usuario = this.usuariosRepository.findById(reserva.getUsuario().getUsuario_id())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o ID fornecido."));
        Livro livro = this.livrosRepository.findById(reserva.getLivro().getLivro_id())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o ID fornecido."));

        // Define os dados atualizados na reserva existente
        r.setUsuario(usuario);
        r.setLivro(livro);
        r.setData_emprestimo(reserva.getData_emprestimo());
        r.setData_devolucao(reserva.getData_devolucao());
        r.setData_devolucao_real(reserva.getData_devolucao_real());
        r.setStatus(reserva.getStatus());

        // Salva a reserva atualizada
        this.reservasRepository.save(r);
    }
}
