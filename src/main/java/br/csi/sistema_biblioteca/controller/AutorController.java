package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Autor;
import br.csi.sistema_biblioteca.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity excluir(@PathVariable String uuid){
        // para deletar um autor, será necessário deletar a conexão entre o livro e o autor na tabela livros_autores
        this.autorService.excluirAutorUuid(uuid);
        return ResponseEntity.noContent().build();
    }
}