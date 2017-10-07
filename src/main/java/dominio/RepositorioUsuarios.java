package dominio;

import excepciones.NoExisteUsuarioError;

public class RepositorioUsuarios extends AbstractRepository<Usuario>{

	public Long obtenerId(String email, String password){
		Usuario usuarioBuscado = obtenerTodos().stream().filter(usuario -> usuario.validar(email, password)).findFirst()
				.orElseThrow(() -> new NoExisteUsuarioError("No se pudo encontrar el usuario " + email + " o la password no es la correcta."));
		return usuarioBuscado.getId();
	}
	
	@Override
	protected Class<Usuario> tipoEntidad() {
		return Usuario.class;
	}

	@Override
	protected String mensajeEntidadExistenteError(Usuario elemento) {
		return "Ya existe un usuario con el email " + elemento.getEmail();
	}

}