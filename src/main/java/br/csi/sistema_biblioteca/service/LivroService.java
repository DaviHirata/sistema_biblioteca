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

    public List<Livro> listarLivros() {
        return this.livroRepository.findAll();
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

    public Livro getLivroUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.livroRepository.findLivrosByUuid(uuidformatado);
    }

    @Transactional
    public void excluirLivroUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.livroRepository.deleteLivrosByUuid(uuidformatado);
    }

    public void atualizarLivroUuid(Livro livro) {
        Livro livroExistente = this.livroRepository.findLivrosByUuid(livro.getUuid());

        if (livroExistente == null) {
            throw new RuntimeException("Livro não encontrado");
        }

        livro.setTitulo(livro.getTitulo());
        livro.setEditora(livro.getEditora());
        livro.setAno_publicacao(livro.getAno_publicacao());
        livro.setIsbn(livro.getIsbn());
        livro.setCategoria(livro.getCategoria());
        livro.setQuantidade_disponivel(livro.getQuantidade_disponivel());
        livro.setDescricao(livro.getDescricao());
        this.livroRepository.save(livro);
    }
}