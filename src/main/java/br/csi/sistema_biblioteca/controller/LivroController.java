package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livro")
public class LivroController {
    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public void salvar(@RequestBody Livro livro) {
        this.livroService.salvarLivro(livro);
    }

    @GetMapping("/listar")
    public List<Livro> listar() {
        return livroService.listarLivros();
    }

    @GetMapping("/uuid/{uuid}")
    public Livro livro(@PathVariable String uuid) {
        return this.livroService.getLivroUUID(uuid);
    }

    @PutMapping("/uuid")
    public void atualizar(@RequestBody Livro livro) {
        this.livroService.atualizarLivroUuid(livro);
    }

    @DeleteMapping("/{uuid}")
    public void deletar(@PathVariable String uuid) {
        this.livroService.excluirLivroUuid(uuid);
    }
}