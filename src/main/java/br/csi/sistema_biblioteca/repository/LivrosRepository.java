package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.dto.LivroDTO;
import br.csi.sistema_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LivrosRepository extends JpaRepository<Livro, Long> {
    public Livro findLivrosByUuid(UUID uuid);
    public void deleteLivrosByUuid(UUID uuid);

    @Query(value = "SELECT l FROM livros l JOIN l.autores a WHERE a.autor_id =: autor_id",
            nativeQuery = true)
    List<Livro> findLivrosByAutorId(Long autor_id);

    @Query(value = "SELECT l.uuid, l.titulo, l.editora, l.ano_publicacao, l.isbn, " +
            "l.categoria, l.quantidade_disponivel, l.descricao FROM livros l WHERE l.livro_id =: id",
            nativeQuery = true)
    LivroDTO findLivroDTOByUuid(@Param("id") Long id);

    @Query(value = "SELECT l.uuid, l.titulo, l.editora, l.ano_publicacao, l.isbn, " +
            "l.categoria, l.quantidade_disponivel, l.descricao FROM livros as l", nativeQuery = true)
    List<LivroDTO> findAllLivrosDTO();
}