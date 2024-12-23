package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.dto.AutorDTO;
import br.csi.sistema_biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
    public Autor findAutoresByUuid(UUID uuid);
    public void deleteAutoresByUuid(UUID uuid);
}