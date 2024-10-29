package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.model.Livros_Autores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivrosAutoresRepository extends JpaRepository<Livros_Autores, Long> {
}