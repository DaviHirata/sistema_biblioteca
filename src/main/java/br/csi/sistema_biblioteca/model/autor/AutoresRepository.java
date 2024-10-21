package br.csi.sistema_biblioteca.model.autor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AutoresRepository extends JpaRepository<Autor, Long> {
}