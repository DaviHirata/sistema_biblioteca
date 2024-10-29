package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Livros_Autores;
import br.csi.sistema_biblioteca.service.LivrosAutoresService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros_autores")
public class LivrosAutoresController {
    private LivrosAutoresService livrosAutoresService;

    public LivrosAutoresController(LivrosAutoresService livrosAutoresService) {
        this.livrosAutoresService = livrosAutoresService;
    }

    @PostMapping
    public void salvar(@RequestBody Livros_Autores livros_autores) {
        this.livrosAutoresService.salvarLA(livros_autores);
    }

    @GetMapping("/listar")
    public List<Livros_Autores> listar() {
        return livrosAutoresService.buscarLA();
    }

    @GetMapping("/id/{id}")
    public Livros_Autores buscar(@PathVariable Long id) {
        return livrosAutoresService.findLivrosAutoresById(id);
    }

    @PutMapping("/id")
    public void atualizar(@RequestBody Livros_Autores livros_autores) {
        this.livrosAutoresService.atualizarLA(livros_autores);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.livrosAutoresService.excluirLA(id);
    }
}
