package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Livro;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
    public void salvar(@RequestBody Livro livro) {
        this.livroService.salvarLivro(livro);
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
    public Livro livro(@PathVariable String uuid) {
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
    public void atualizar(@RequestBody Livro livro) {
        this.livroService.atualizarLivroUuid(livro);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um livro", description = "Método para deletar um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Livro.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public void deletar(@PathVariable String uuid) {
        //para deletar livros com reservas registradas, precisará deletar a reserva
        //também será necessário deletar a conexão entre o livro e o autor na tabela livros_autores
        this.livroService.excluirLivroUuid(uuid);
    }
}