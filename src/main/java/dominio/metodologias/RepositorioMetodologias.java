package dominio.metodologias;

import javax.persistence.Entity;

import dominio.AbstractLocalRepository;

@Entity
public class RepositorioMetodologias extends AbstractLocalRepository<Metodologia> {

	@Override
	protected String mensajeEntidadExistenteError(Metodologia elemento) {
		return "Ya existe una metodología con el nombre " + elemento.getNombre();
	}

	public Metodologia obtenerPorId(Long id) {
		return obtenerTodos().stream().filter(metodologia -> metodologia.getId().equals(id)).findFirst()
				.orElseThrow(null);//Estoy seguro que la metodologia va a existir
	}

}