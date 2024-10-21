package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.autor.Autor;
import br.csi.sistema_biblioteca.model.autor.AutoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {
    private final AutoresRepository autoresRepository;

    public AutorService(AutoresRepository autoresRepository) {this.autoresRepository = autoresRepository;}

    public void salvarAutor(Autor autor) {this.autoresRepository.save(autor);}

    public List<Autor> listarAutores() {return this.autoresRepository.findAll();}

    public Autor findUsuarioById(Long id) {return this.autoresRepository.findById(id).get();}

    public void excluirAutor(Long id) {this.autoresRepository.deleteById(id);}

    public void atualizar(Autor autor) {
        Autor a = this.autoresRepository.getReferenceById(autor.getId());
        a.setNome(autor.getNome());
        this.autoresRepository.save(autor);
    }
}
