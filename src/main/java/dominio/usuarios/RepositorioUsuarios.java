package dominio.usuarios;

import java.util.List;
import java.util.stream.Collectors;

import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.AbstractRepository;
import excepciones.NoExisteUsuarioError;

public class RepositorioUsuarios extends AbstractRepository<Usuario> implements TransactionalOps{

	public Long obtenerId(String email, String password){
		Usuario usuarioBuscado = obtenerTodos().stream().filter(usuario -> usuario.validar(email, password)).findFirst()
				.orElseThrow(() -> new NoExisteUsuarioError("No se pudo encontrar el usuario " + email + " o la password no es la correcta."));
		return usuarioBuscado.getId();
	}
	
	public void actualizar(Usuario usuario){ // SE DEBERIA USAR PERSIST, NO MERGE (!!!)
		withTransaction(() -> entityManager().merge(usuario));
	}
	
	@Override
	public Usuario obtenerPorId(Long id){ // VER DE VOLAR (!!!)
		Usuario usuario = super.obtenerPorId(id);
		this.inicializarIndicadoresPara(usuario);
		return usuario;
	}
	
	private void inicializarIndicadoresPara(Usuario usuario){ // VER DE VOLAR (!!!)
		List<String> equivalenciaIndicadores = usuario.getIndicadores().stream().map(protoInd -> protoInd.getEquivalencia()).collect(Collectors.toList());
		usuario.eliminarIndicadores();
		usuario.agregarIndicadores(equivalenciaIndicadores);
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