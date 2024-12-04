package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.DadosUsuarios;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuariosRepository usuariosRepository;

    public UsuarioService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    public void salvarUsuario(Usuario usuario) {
        //Criptografar a senha antes de ser salvo
        usuario.setSenha(new BCryptPasswordEncoder().encode(usuario.getSenha()));
        this.usuariosRepository.save(usuario);
    }

    public List<DadosUsuarios> listAllUsuarios() {
        return this.usuariosRepository.findAll().stream().map(DadosUsuarios::new).toList();
    }

    public List<Usuario> listUsuarios(){
        return this.usuariosRepository.findAll();
    }

    public DadosUsuarios findUsuario(Long id) {
        Usuario usuario = this.usuariosRepository.getReferenceById(id);
        return new DadosUsuarios(usuario);
    }

    public Usuario getUsuarioUUID(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        return this.usuariosRepository.findUsuariosByUuid(uuidformatado);
    }

    @Transactional
    public void excluirUsuarioUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.usuariosRepository.deleteUsuariosByUuid(uuidformatado);
    }

    public void atualizarUsuarioUuid(Usuario usuario) {
        Usuario u = this.usuariosRepository.findUsuariosByUuid(usuario.getUuid());
        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        u.setTipo_usuario(usuario.getTipo_usuario());
        u.setTelefone(usuario.getTelefone());
        u.setData_nasc(usuario.getData_nasc());
        this.usuariosRepository.save(u);
    }
}
