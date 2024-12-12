package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.dto.AutorDTO;
import br.csi.sistema_biblioteca.model.Autor;
import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/autor")
@Tag(name = "Autores", description = "Path relacionado a operações com autores")
public class AutorController {
    private AutorService autorService;

    public AutorController(AutorService autorService) {this.autorService = autorService;}

    @PostMapping
    @Operation(summary = "Adicionar um novo autor", description = "Adiciona um novo autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor adicionado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public ResponseEntity salvar(@RequestBody @Valid Autor autor, UriComponentsBuilder uriBuilder){
        this.autorService.salvarAutor(autor);
        URI uri = uriBuilder.path("/autor/uuid/{uuid}").buildAndExpand(autor.getUuid()).toUri();
        return ResponseEntity.created(uri).body(autor);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar autores", description = "Lista todos os autores do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Autor> listar(){return autorService.listarAutores();}

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Listar autor específico", description = "Retorna um autor com base no uuid buscado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Autor buscarPorUUID(@PathVariable String uuid) {
        return this.autorService.getAutorUUID(uuid);
    }

    @GetMapping("/{autorId}/livros")
    @Operation(summary = "Listar livros por autor", description = "Retorna todos os livros associados a um autor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado", content = @Content)
    })
    public ResponseEntity<List<Livro>> listarLivrosPorAutor(@PathVariable Long autorId) {
        List<Livro> livros = autorService.listarLivrosPorAutorId(autorId);

        if (livros.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(livros);
        }
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar um autor", description = "Método para atualizar um autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity atualizar(@RequestBody Autor autor){
        this.autorService.atualizarAutorUuid(autor);
        return ResponseEntity.ok(autor);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um autor", description = "Método para deletar um autor da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Autor.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "409", description = "Erro de conflito - autor está associado " +
                    "a outros registros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity excluir(@PathVariable String uuid){
        try{
            this.autorService.excluirAutorUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e){
            //retorna erro 409 (conflito) informando que o autor está vinculado a outros registros
            return ResponseEntity.status(409).body("Não foi possível excluir o autor pois ele está" +
                    "associado a um ou mais livros");
        } catch (Exception e){
            //retorna um erro 500 para qualquer outro erro inesperado
            return ResponseEntity.status(500).body("Erro interno ao tentar excluir o autor");
        }
    }
}