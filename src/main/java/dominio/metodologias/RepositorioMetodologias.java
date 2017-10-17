package dominio.metodologias;

import javax.persistence.Entity;

import dominio.AbstractLocalRepository;

@Entity
public class RepositorioMetodologias extends AbstractLocalRepository<Metodologia>{

	@Override
	protected String mensajeEntidadExistenteError(Metodologia elemento) {
		return "Ya existe una metodología con el nombre " + elemento.getNombre();
	}

}