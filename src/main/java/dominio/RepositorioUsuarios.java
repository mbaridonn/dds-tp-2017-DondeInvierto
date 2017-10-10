package dominio;

import javax.persistence.EntityTransaction;

import excepciones.NoExisteUsuarioError;

public class RepositorioUsuarios extends AbstractRepository<Usuario>{

	public Long obtenerId(String email, String password){
		Usuario usuarioBuscado = obtenerTodos().stream().filter(usuario -> usuario.validar(email, password)).findFirst()
				.orElseThrow(() -> new NoExisteUsuarioError("No se pudo encontrar el usuario " + email + " o la password no es la correcta."));
		return usuarioBuscado.getId();
	}
	
	public void actualizarUsuario(Usuario usuario){
		EntityTransaction tx = entityManager().getTransaction();
		tx.begin();
		entityManager().merge(usuario);
		tx.commit();
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