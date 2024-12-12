package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.dto.LivroDTO;
import br.csi.sistema_biblioteca.model.Autor;
import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.repository.LivrosRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LivroService {
    private final LivrosRepository livroRepository;

    public LivroService(LivrosRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void salvarLivro(Livro livro) {
        this.livroRepository.save(livro);
    }

    public List<LivroDTO> listarLivros() {
        return this.livroRepository.findAllLivrosDTO();
    }

    public String atribuirAutor(Long livro_id, Autor autor) {
        Livro livro = this.livroRepository.getReferenceById(livro_id);

        // Inicializa a lista de autores, caso esteja nula
        if (livro.getAutores() == null) {
            livro.setAutores(new ArrayList<>());
        }

        // Adiciona o autor à lista se ele ainda não estiver nela
        if (!livro.getAutores().contains(autor)) {
            livro.getAutores().add(autor);
            this.livroRepository.save(livro);
            return "Autor atribuído com sucesso";
        } else {
            return "O autor já está associado ao livro";
        }
    }

    public LivroDTO getLivroUUID(Long id) {
        return this.livroRepository.findLivroDTOByUuid(id);
    }

    @Transactional
    public void excluirLivroUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.livroRepository.deleteLivrosByUuid(uuidformatado);
    }

    public void atualizarLivroUuid(LivroDTO livroDTO) {
        Livro livroExistente = this.livroRepository.findLivrosByUuid(livroDTO.getUuid());

        if (livroExistente == null) {
            throw new RuntimeException("Livro não encontrado");
        }

        // Atualiza apenas os campos definidos no DTO
        livroExistente.setTitulo(livroDTO.getTitulo());
        livroExistente.setEditora(livroDTO.getEditora());
        livroExistente.setAno_publicacao(livroDTO.getAnoPublicacao());
        livroExistente.setIsbn(livroDTO.getIsbn());
        livroExistente.setCategoria(livroDTO.getCategoria());
        livroExistente.setQuantidade_disponivel(livroDTO.getQuantidadeDisponivel());
        livroExistente.setDescricao(livroDTO.getDescricao());

        this.livroRepository.save(livroExistente);
    }
}