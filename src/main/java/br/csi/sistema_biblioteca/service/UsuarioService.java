package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.model.UsuariosRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuariosRepository usuariosRepository;

    public UsuarioService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public void salvarUsuario(Usuario usuario) {
        this.usuariosRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return this.usuariosRepository.findAll();
    }

    public Usuario findById(Long id) {
        return this.usuariosRepository.findById(id).get();
    }

    public void excluirUsuario(Long id) {
        this.usuariosRepository.deleteById(id);
    }

    public void atualizarUsuario(Usuario usuario) {
        Usuario u = this.usuariosRepository.getReferenceById(usuario.getId());
        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        u.setTipo_usuario(usuario.getTipo_usuario());
        u.setTelefone(usuario.getTelefone());
        u.setData_nasc(usuario.getData_nasc());
        this.usuariosRepository.save(u);
    }
}
