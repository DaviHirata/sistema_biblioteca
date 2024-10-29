package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Autor;
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

    @GetMapping("/uuid/{uuid}")
    public Autor autor(@PathVariable String uuid) {
        return this.autorService.getAutorUUID(uuid);
    }

    @PutMapping("/uuid")
    public void atualizar(@RequestBody Autor autor){
        this.autorService.atualizarAutorUuid(autor);
    }

    @DeleteMapping("/{uuid}")
    public void excluir(@PathVariable String uuid){
        this.autorService.excluirAutorUuid(uuid);
    }
}