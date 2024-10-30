package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivrosRepository extends JpaRepository<Livro, Long> {
    public Livro findLivrosByUuid(UUID uuid);
    public void deleteLivrosByUuid(UUID uuid);
}
