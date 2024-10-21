package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.autor.Autor;
import br.csi.sistema_biblioteca.service.AutorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {
    private AutorService autorService;

    public AutorController(AutorService autorService) {this.autorService = autorService;}

    @PostMapping
    public void salvar(@RequestBody Autor autor){this.autorService.salvarAutor(autor);}

    @GetMapping("/listar")
    public List<Autor> listar(){return autorService.listarAutores();}

    @PutMapping
    public void atualizar(@RequestBody Autor autor){
        this.autorService.atualizar(autor);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id){
        this.autorService.excluirAutor(id);
    }
}
