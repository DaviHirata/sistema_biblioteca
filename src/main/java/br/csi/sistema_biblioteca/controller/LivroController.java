package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Autor;
import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livro")
@Tag(name = "Livros", description = "Path relacionado a operações com livros")
public class LivroController {
    private LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    @Operation(summary = "Adicionar um novo livro", description = "Adiciona um livro à base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro adicionado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public ResponseEntity salvar(@RequestBody @Valid Livro livro, UriComponentsBuilder uriBuilder) {
        this.livroService.salvarLivro(livro);
        URI uri = uriBuilder.path("/livro/uuid/{uuid}").buildAndExpand(livro.getUuid()).toUri();
        return ResponseEntity.created(uri).body(livro);
    }

    @PutMapping("{id}/atribuir-autor")
    @Transactional
    @Operation(summary = "Vincular Autor a Livro", description = "Associa um autor a um livro específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor vinculado ao livro com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Livro ou autor não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity vincularAutor(
            @PathVariable Long id,
            @RequestBody Autor autor
    ) {
        return ResponseEntity.ok(this.livroService.atribuirAutor(id, autor));
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar livros", description = "Lista todos os livros do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Livro> listar() {
        return livroService.listarLivros();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Listar livro específico", description = "Retorna um livro com base no uuid buscado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Livro buscarPorUUID(@PathVariable String uuid) {
        return this.livroService.getLivroUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar um livro", description = "Método para atualizar um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity atualizar(@RequestBody Livro livro) {
        this.livroService.atualizarLivroUuid(livro);
        return ResponseEntity.ok(livro);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um livro", description = "Método para deletar um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "409", description = "Erro de conflito - livro está associado " +
                    "a outros registros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity deletar(@PathVariable String uuid) {
        try{
            this.livroService.excluirLivroUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e){
            //retorna erro 409 (conflito) informando que o livro está vinculado a outros registros
            return ResponseEntity.status(409).body("Não foi possível excluir o livro pois ele está" +
                    "associado a um ou mais autores ou reservas");
        } catch (Exception e){
            //retorna um erro 500 para qualquer outro erro inesperado
            return ResponseEntity.status(500).body("Erro interno ao tentar excluir o livro");
        }
    }
}