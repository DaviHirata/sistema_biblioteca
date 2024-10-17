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

    @PutMapping
    public void atualizar(@RequestBody Usuario usuario) {
        this.usuarioService.atualizarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        this.usuarioService.excluirUsuario(id);
    }
}
