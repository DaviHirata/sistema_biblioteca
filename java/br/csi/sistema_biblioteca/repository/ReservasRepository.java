package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservasRepository extends JpaRepository<Reserva, Long> {
    public Reserva findReservasByUuid(UUID uuid);
    public void deleteReservasByUuid(UUID uuid);
}
