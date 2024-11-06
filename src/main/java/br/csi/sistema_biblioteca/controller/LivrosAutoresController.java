package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Livros_Autores;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.LivrosAutoresService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros_autores")
@Tag(name = "Livros_Autores", description = "Path relacionado a operações de conexão de registros entre livros e autores")
public class LivrosAutoresController {
    private LivrosAutoresService livrosAutoresService;

    public LivrosAutoresController(LivrosAutoresService livrosAutoresService) {
        this.livrosAutoresService = livrosAutoresService;
    }

    @PostMapping
    @Operation(summary = "Fazer uma nova conexão Livro-Autor", description = "Cria um novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conexão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livros_Autores.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public ResponseEntity salvar(@RequestBody @Valid Livros_Autores livros_autores, UriComponentsBuilder uriBuilder) {
        this.livrosAutoresService.salvarLA(livros_autores);
        URI uri = uriBuilder.path("/livros_autores/id/{id}").buildAndExpand(livros_autores.getId()).toUri();
        return ResponseEntity.created(uri).body(livros_autores);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar Livros_Autores", description = "Retorna todos os registros que conectam um livro ao seu respectivo auto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livros_Autores.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Livros_Autores> listar() {
        return livrosAutoresService.buscarLA();
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar um registro específico", description = "Cria um novo registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conexão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livros_Autores.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Livros_Autores buscarPorID(@PathVariable Long id) {
        return livrosAutoresService.findLivrosAutoresById(id);
    }

    @PutMapping("/id")
    @Operation(summary = "Atualizar registro", description = "Atualiza uma conexão entre livro e autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atualização feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livros_Autores.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno - Operação não efetuada", content = @Content),
    })
    public ResponseEntity atualizar(@RequestBody Livros_Autores livros_autores) {
        this.livrosAutoresService.atualizarLA(livros_autores);
        return ResponseEntity.ok(livros_autores);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um registro", description = "Método para excluir uma conexão entre um livro e um autor, baseado no id do registro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livros_Autores.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity deletar(@PathVariable Long id) {
        this.livrosAutoresService.excluirLA(id);
        return ResponseEntity.noContent().build();
    }
}
