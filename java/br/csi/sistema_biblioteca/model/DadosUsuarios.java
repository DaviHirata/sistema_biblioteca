package br.csi.sistema_biblioteca.model;

public record DadosUsuarios(Long id, String email, String tipo_usuario) {
    public DadosUsuarios(Usuario usuario){
        this(usuario.getUsuario_id(), usuario.getEmail(),
                usuario.getTipo_usuario());
    }
}
