package dominio.metodologias;

import dominio.AbstractRepository;

public class RepositorioMetodologias extends AbstractRepository<Metodologia>{

	@Override
	protected Class<Metodologia> tipoEntidad() {
		return Metodologia.class;
	}

	@Override
	protected String mensajeEntidadExistenteError(Metodologia elemento) {
		return "Ya existe una metodología con el nombre " + elemento.getNombre();
	}

}