package dominio.metodologias;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import excepciones.MetodologiaExistenteError;

public class RepositorioMetodologias implements WithGlobalEntityManager{
	
	public List<Metodologia> getMetodologias() {
		return (List<Metodologia>) this.entityManager().createQuery("FROM Metodologia").getResultList();
	}
	
	public void agregarMetodologia(Metodologia metodologia){
		if (existeMetodologia(metodologia)){
			throw new MetodologiaExistenteError("Ya existe una metodología con el nombre " + metodologia.getNombre());
		}
		this.entityManager().persist(metodologia);//La transacción se tiene que agregar donde se envíe el mensaje (!)
	}
	
	private boolean existeMetodologia(Metodologia metodologia){
		List<Metodologia> metodologiasConMismoNombre = this.entityManager().createQuery("FROM Metodologia where nombre = '" + metodologia.getNombre() + "'").getResultList();
		return !metodologiasConMismoNombre.isEmpty();
	}
	
}