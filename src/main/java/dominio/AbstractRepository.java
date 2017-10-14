package dominio;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import excepciones.EntidadExistenteError;

public abstract class AbstractRepository<T> implements WithGlobalEntityManager{
	
	public List<T> obtenerTodos() {
		return entityManager().createQuery("FROM " + nombreTipoEntidad(), tipoEntidad()).getResultList();
	}
	
	public T obtenerPorId(Long id){
		return entityManager().find(tipoEntidad(), id);
	}
	
	public void agregar(T elemento){
		System.out.println("Hasta aca llegue");
		if (existe(elemento)){
			throw new EntidadExistenteError(mensajeEntidadExistenteError(elemento));
		}
		System.out.println("Aca no");
		entityManager().persist(elemento);//La transacción se tiene que agregar donde se envíe el mensaje
		System.out.println("Aca si!!");
	}
	
	protected boolean existe(T elemento){
		return this.obtenerTodos().contains(elemento);//Contains chequea con equals
	}
	
	private String nombreTipoEntidad(){
		return tipoEntidad().getSimpleName();
	}
	
	protected abstract Class<T> tipoEntidad();
	
	protected abstract String mensajeEntidadExistenteError(T elemento);
	
}