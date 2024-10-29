package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    public Autor findAutoresByUuid(UUID uuid);
    public void deleteAutoresByUuid(UUID uuid);
}