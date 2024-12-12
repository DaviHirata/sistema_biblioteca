package br.csi.sistema_biblioteca.repository;

import br.csi.sistema_biblioteca.dto.UsuarioDTO;
import br.csi.sistema_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
    public Usuario findUsuariosByUuid(UUID uuid);
    public void deleteUsuariosByUuid(UUID uuid);

    @Query(value = "SELECT u.uuid, u.nome, u.email, u.senha, u.telefone, u.tipo_usuario, u.data_nasc " +
            "FROM usuarios u WHERE u.uuid =: uuid", nativeQuery = true)
    UsuarioDTO findUsuarioDTOByUuid(@Param("uuid") UUID uuid);

    @Query(value = "SELECT u.uuid, u.nome, u.email, u.senha, u.telefone, u.tipo_usuario, u.data_nasc " +
            "FROM usuarios u", nativeQuery = true)
    List<UsuarioDTO> findAllUsuariosDTO();

    public Usuario findUsuarioByEmail(String email);
}