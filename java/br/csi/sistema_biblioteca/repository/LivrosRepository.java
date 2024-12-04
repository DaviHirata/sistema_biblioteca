package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LivrosRepository extends JpaRepository<Livro, Long> {
    public Livro findLivrosByUuid(UUID uuid);
    public void deleteLivrosByUuid(UUID uuid);

    @Query("SELECT l FROM Livro l JOIN l.autores a WHERE a.autor_id = :autor_id")
    List<Livro> findLivrosByAutorId(Long autor_id);
}
