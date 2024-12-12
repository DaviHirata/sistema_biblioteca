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
}