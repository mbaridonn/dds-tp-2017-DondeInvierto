package dominio.empresas;

import java.util.List;
import java.util.stream.Stream;

import dominio.AbstractRepository;

public class RepositorioEmpresas extends AbstractRepository<Empresa> {

	public static RepositorioEmpresas instancia = new RepositorioEmpresas();

	public boolean hayEmpresas() {
		return obtenerTodos().size() > 0;
	}

	public void agregarMultiplesEmpresas(List<Empresa> empresas) {
		Stream<Empresa> empresasNuevas = empresas.stream().filter(emp -> !existe(emp));
		empresasNuevas.forEach(empresa -> agregar(empresa));
	}

	@Override
	protected Class<Empresa> tipoEntidad() {
		return Empresa.class;
	}

	@Override
	protected String mensajeEntidadExistenteError(Empresa elemento) {
		return "Ya existe una empresa con el nombre " + elemento.getNombre();
	}

	public List<Empresa> listar() {
		return entityManager().createQuery("from Empresa", Empresa.class).getResultList();
	}

	public Empresa buscar(long id) {
		return entityManager().find(Empresa.class, id);
	}
}
