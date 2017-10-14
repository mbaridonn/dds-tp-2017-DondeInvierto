package dominio.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.AbstractRepository;
import dominio.indicadores.Indicador;
import dominio.parser.ParserIndicadores;
import excepciones.NoExisteUsuarioError;

public class RepositorioUsuarios extends AbstractRepository<Usuario> implements TransactionalOps{

	public Long obtenerId(String email, String password){
		Usuario usuarioBuscado = obtenerTodos().stream().filter(usuario -> usuario.validar(email, password)).findFirst()
				.orElseThrow(() -> new NoExisteUsuarioError("No se pudo encontrar el usuario " + email + " o la password no es la correcta."));
		return usuarioBuscado.getId();
	}
	
	public void actualizar(Usuario usuario){
		withTransaction(() -> entityManager().merge(usuario));
	}
	
	@Override
	public Usuario obtenerPorId(Long id){
		Usuario usuario = super.obtenerPorId(id);
		this.inicializarIndicadoresPara(usuario);
		return usuario;
	}
	
	private void inicializarIndicadoresPara(Usuario usuario){
		System.out.println("Obtuve los indicadores");
		List<Indicador> indicadores = usuario.getIndicadoresCreados();
		System.out.println("Obtuve los indicadores");
		Stream<Indicador> indicsInicializados =  indicadores.stream().map(protoInd -> ParserIndicadores.parse(protoInd.getEquivalencia()));
		usuario.setIndicadoresCreados(indicsInicializados.collect(Collectors.toList()));
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