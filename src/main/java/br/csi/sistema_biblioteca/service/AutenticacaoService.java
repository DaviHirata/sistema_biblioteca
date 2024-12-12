package br.csi.sistema_biblioteca.service;

import br.csi.sistema_biblioteca.model.Usuario;
import br.csi.sistema_biblioteca.repository.UsuariosRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuariosRepository usuariosRepository;

    public AutenticacaoService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = this.usuariosRepository.findUsuarioByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário ou senha incorretos");
        } else{
            UserDetails user = User.withUsername(usuario.getEmail()).
                    password(usuario.getSenha()).authorities(usuario.getTipo_usuario()).build();
            return user;
        }
    }
}
