package br.csi.sistema_biblioteca.controller;

import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
//@Tag(name = "Usuarios", description = "Path relacionado a operações com usuários")
public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public void salvar(@RequestBody Usuario usuario) {
        this.usuarioService.salvarUsuario(usuario);
    }

    @GetMapping("/listar")
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/uuid/{uuid}")
    public Usuario usuario(@PathVariable String uuid) {
        return this.usuarioService.getUsuarioUUID(uuid);
    }

    @PutMapping("/uuid")
    public void atualizar(@RequestBody Usuario usuario) {
        this.usuarioService.atualizarUsuarioUuid(usuario);
    }

    @DeleteMapping("/{uuid}")
    public void deletar(@PathVariable String uuid) {
        this.usuarioService.excluirUsuarioUuid(uuid);
    }
}
