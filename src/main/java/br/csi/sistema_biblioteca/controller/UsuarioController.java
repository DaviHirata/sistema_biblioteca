package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

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
    public void salvar(@RequestBody Usuario usuario) {
        this.usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/uuid/{uuid}")
    @Operation(summary = "Listar usuário específico", description = "Retorna um usuário com base no uuid buscado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
    })
    public Usuario usuario(@PathVariable String uuid) {
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
    public void atualizar(@RequestBody Usuario usuario) {
        this.usuarioService.atualizarUsuarioUuid(usuario);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Deletar um usuário", description = "Método para deletar um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exclusão feita com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "Erro de servidor", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno de servidor - Operação não efetuada", content = @Content),
    })
    public void deletar(@PathVariable String uuid) {
        //para deletar usuários com reserva, precisará deletar a reserva
        this.usuarioService.excluirUsuarioUuid(uuid);
    }
}
