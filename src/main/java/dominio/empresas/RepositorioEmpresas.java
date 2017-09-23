package dominio.empresas;

import java.util.List;
import java.util.stream.Stream;

import dominio.AbstractRepository;

public class RepositorioEmpresas extends AbstractRepository<Empresa>{
	
	public boolean hayEmpresas(){
		return obtenerTodos().size() > 0;
	}

	public void agregarMultiplesEmpresas(List<Empresa> empresas) {
		Stream<Empresa> empresasNuevas = empresas.stream().filter(emp -> !existe(emp));//Sólo persisto las empresas que no están ya en la BD
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
}
