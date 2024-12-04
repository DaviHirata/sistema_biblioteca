package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.DadosUsuarios;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.UsuarioService;
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

@RestController
@RequestMapping("/usuario")
@Tag(name = "Usuarios", description = "Path relacionado a operações com usuários")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @Operation(summary = "Criar um novo usuário", description = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public ResponseEntity salvar(@RequestBody @Valid Usuario usuario, UriComponentsBuilder uriBuilder) {
        this.usuarioService.salvarUsuario(usuario);
        URI uri = uriBuilder.path("/usuario/uuid/{uuid}").buildAndExpand(usuario.getUuid()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Listar um usuário pelo id", description = "Lista o usuário procurado pelo id no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public DadosUsuarios findById(@PathVariable Long id) {
        return this.usuarioService.findUsuario(id);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Usuario> listar() {
        return usuarioService.listUsuarios();
    }

    @GetMapping()
    @Operation(summary = "Listar dados de todos usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<DadosUsuarios> listarTodosUsuarios() {
        return usuarioService.listAllUsuarios();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Listar usuário específico", description = "Retorna um usuário com base no uuid buscado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Usuario buscarPorUUID(@PathVariable String uuid) {
        return this.usuarioService.getUsuarioUUID(uuid);
    }

    @PutMapping("/uuid")
    @Operation(summary = "Atualizar um usuário", description = "Método para atualizar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity atualizar(@RequestBody Usuario usuario) {
        this.usuarioService.atualizarUsuarioUuid(usuario);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um usuário", description = "Método para deletar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "409", description = "Erro de conflito - usuário está associado " +
                    "a outros registros", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public ResponseEntity deletar(@PathVariable String uuid) {
        try{
            this.usuarioService.excluirUsuarioUuid(uuid);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e){
            //retorna erro 409 (conflito) informando que o usuário está vinculado a outros registros
            return ResponseEntity.status(409).body("Não foi possível excluir o usuário pois ele está" +
                    "associado a uma ou mais reservas");
        } catch (Exception e){
            //retorna um erro 500 para qualquer outro erro inesperado
            return ResponseEntity.status(500).body("Erro interno ao tentar excluir o usuário");
        }
    }
}
