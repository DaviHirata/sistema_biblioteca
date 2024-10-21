package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.livro.Livro;
import br.csi.sistema_biblioteca.model.livro.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LivroService {
    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public void salvarLivro(Livro livro) {
        this.livroRepository.save(livro);
    }

    public List<Livro> listarLivros() {
        return this.livroRepository.findAll();
    }

    public Livro getLivroUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.livroRepository.findLivrosByUuid(uuidformatado);
    }

    public void excluirLivroUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.livroRepository.deleteLivrosByUuid(uuidformatado);
    }

    public void atualizarLivroUuid(Livro livro) {
        Livro l = this.livroRepository.findLivrosByUuid(livro.getUuid());
        l.setTitulo(livro.getTitulo());
        l.setEditora(livro.getEditora());
        l.setAno_publicacao(livro.getAno_publicacao());
        l.setIsbn(livro.getIsbn());
        l.setCategoria(livro.getCategoria());
        l.setQuantidade_disponivel(livro.getQuantidade_disponivel());
        l.setDescricao(livro.getDescricao());
        this.livroRepository.save(l);
    }
}
