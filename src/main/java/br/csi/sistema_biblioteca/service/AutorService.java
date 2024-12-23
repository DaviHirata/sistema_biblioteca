package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.dto.AutorDTO;
import br.csi.sistema_biblioteca.model.Autor;
import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.repository.AutoresRepository;
import br.csi.sistema_biblioteca.repository.LivrosRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AutorService {
    private final AutoresRepository autoresRepository;
    private final LivrosRepository livrosRepository;

    public AutorService(AutoresRepository autoresRepository, LivrosRepository livrosRepository) {this.autoresRepository = autoresRepository;
        this.livrosRepository = livrosRepository;
    }

    public void salvarAutor(Autor autor) {this.autoresRepository.save(autor);}

    public List<Autor> listarAutores() {
        return autoresRepository.findAll();
    }

    public Autor getAutorUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.autoresRepository.findAutoresByUuid(uuidformatado);
    }

    public List<Livro> listarLivrosPorAutorId(Long autor_id){
        return livrosRepository.findLivrosByAutorId(autor_id);
    }

    @Transactional
    public void excluirAutorUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.autoresRepository.deleteAutoresByUuid(uuidformatado);
    }

    public void atualizarAutorUuid(Autor autor) {
        Autor autorExistente = this.autoresRepository.findAutoresByUuid(autor.getUuid());
        if (autorExistente == null) {
            throw new EntityNotFoundException("Autor não encontrado com o UUID fornecido.");
        }
        autorExistente.setNome(autor.getNome());
        this.autoresRepository.save(autorExistente);
    }

}