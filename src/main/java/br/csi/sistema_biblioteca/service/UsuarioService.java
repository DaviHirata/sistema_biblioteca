package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.dto.UsuarioDTO;
import br.csi.sistema_biblioteca.model.DadosUsuarios;
import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.repository.UsuariosRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public List<UsuarioDTO> listUsuarios(){
        return this.usuariosRepository.findAllUsuariosDTO();
    }

    public DadosUsuarios findUsuario(Long id) {
        Usuario usuario = this.usuariosRepository.getReferenceById(id);
        return new DadosUsuarios(usuario);
    }

    public UsuarioDTO getUsuarioUUID(UUID uuid) {
        return this.usuariosRepository.findUsuarioDTOByUuid(uuid);
    }

    @Transactional
    public void excluirUsuarioUuid(String uuid) {
        UUID uuidformatado = UUID.fromString(uuid);
        this.usuariosRepository.deleteUsuariosByUuid(uuidformatado);
    }

    public void atualizarUsuarioUuid(UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuariosRepository.findUsuariosByUuid(usuarioDTO.getUuid());
        if (usuarioExistente == null) {
            throw new EntityNotFoundException("Usuário não encontrado com o UUID fornecido.");
        }

        // Atualizar os atributos do Usuario com base no DTO
        usuarioExistente.setNome(usuarioDTO.getNome());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTipo_usuario(usuarioDTO.getTipo_Usuario());
        usuarioExistente.setTelefone(usuarioDTO.getTelefone());
        usuarioExistente.setData_nasc(usuarioDTO.getData_Nasc());

        // Salvar o objeto atualizado
        usuariosRepository.save(usuarioExistente);
    }
}