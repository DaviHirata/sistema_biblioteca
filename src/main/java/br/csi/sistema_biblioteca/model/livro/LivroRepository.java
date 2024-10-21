package br.csi.sistema_biblioteca.model.livro;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    public Livro findLivrosByUuid(UUID uuid);
    public void deleteLivrosByUuid(UUID uuid);
}
