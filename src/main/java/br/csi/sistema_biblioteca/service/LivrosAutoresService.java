package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.Livros_Autores;
import br.csi.sistema_biblioteca.repository.LivrosAutoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivrosAutoresService {
    private final LivrosAutoresRepository livrosAutoresRepository;

    public LivrosAutoresService(LivrosAutoresRepository livrosAutoresRepository) {this.livrosAutoresRepository = livrosAutoresRepository;}

    public void salvarLA(Livros_Autores la) {
       this.livrosAutoresRepository.save(la);
    }

    public List<Livros_Autores> buscarLA() {
        return this.livrosAutoresRepository.findAll();
    }

    public Livros_Autores findLivrosAutoresById(Long id) {
        return this.livrosAutoresRepository.findById(id).get();
    }

    public void excluirLA(Long id) {
        this.livrosAutoresRepository.deleteById(id);
    }

    public void atualizarLA(Livros_Autores livros_autores) {
        Livros_Autores la = this.livrosAutoresRepository.getReferenceById(livros_autores.getId());
        la.setAutor_id(livros_autores.getAutor_id());
        la.setLivro_id(livros_autores.getLivro_id());
        this.livrosAutoresRepository.save(la);
    }
}
